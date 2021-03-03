package test.tomking.pattern;

import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.commons.beanutils.BeanMap;
import org.junit.Test;
import org.tomking.Tomking.Utils.ResultSetUtils;
import org.tomking.Tomking.config.TomkingConfig;
import com.pipihao.tomking.pojo.Clazz;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/2 20:54
 */
public class LearnPattern {

    /**
     * 这里通过正则记录，SQL 中的 替代字符的位置，并记录位置
     */

    @Test
    public void learnMatches(){
        String sql = "select * from class where id = #{id} and cname = #{cname} ";
        String pattern = ".*#\\{.*\\}.*";
        System.out.println(Pattern.matches(pattern,sql));
    }

    @Test
    public void leanPattern() throws SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException {
        long start = System.currentTimeMillis();
        String sql = "select * from class where id = #{id} ";
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
        Clazz clazz = new Clazz(2,"计应2班");
        BeanMap beanMap = new BeanMap(clazz);
        /*连接器*/
        DruidPooledConnection connection = TomkingConfig.instance().getDb().getConnection();
        /*匹配sqwl*/
        PreparedStatement preparedStatement = connection.prepareStatement(rSql);
        for(int i = 0; i < fieldList.size(); i++){
            preparedStatement.setString(i+1,beanMap.get(fieldList.get(i)).toString());
        }
        System.out.println(preparedStatement.toString());;
        //执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        /*将数据转成对象列表*/
        List list = ResultSetUtils.populate(resultSet, Clazz.class);
        System.out.println("第一次查询花了: "+(System.currentTimeMillis()-start)+"毫秒");
        System.out.println(list);
    }


}
