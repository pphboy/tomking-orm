package org.tomking.Tomking.Utils;

import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.commons.beanutils.BeanMap;
import org.tomking.Tomking.config.TomkingConfig;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/3 20:11
 */
public class ExecuteSql {

    /**
     * 查询
     * @param sql
     * @param map
     * @param clazz
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     * @throws InstantiationException
     */
    public static Object select(String sql, Map<String,Object> map,Class<?> clazz) throws SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException {
        String pattern = "#\\{(\\w*)\\}";
        //生成正则对象
        Pattern r = Pattern.compile(pattern);
        /*匹配*/
        Matcher m = r.matcher(sql);
        /*存field的列表*/
        List<String> fieldList = new ArrayList<>();
        if(m.find()){
            /*记录每一个匹配对象的位置
             * fieldList的索引就是其位置
             * */
            do{
                String field = m.group().replaceAll("\\W","");
                System.out.println(field);
                fieldList.add(field);
            }while (m.find());
            System.out.println(fieldList);
        }

        /*将占位符换成问号*/
        String rSql = sql.replaceAll(pattern,"?");
        System.out.println("rSql: "+rSql);
        /*注入值的对象*/
        /*连接器*/
        DruidPooledConnection connection = TomkingConfig.instance().getDb().getConnection();
        /*匹配sqwl*/
        PreparedStatement preparedStatement = connection.prepareStatement(rSql);
        for(int i = 0; i < fieldList.size(); i++){
            preparedStatement.setString(i+1,map.get(fieldList.get(i)).toString());
        }
        System.out.println(preparedStatement.toString());;
        //执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        /*将数据转成对象列表*/
        return ResultSetUtils.populate(resultSet, clazz);
    }
}
