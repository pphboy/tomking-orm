package org.tomking.Tomking.Utils;

import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.commons.beanutils.BeanMap;
import org.tomking.Tomking.config.TomkingConfig;
import org.tomking.Tomking.exception.MapperParamBindingException;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public final class ExecuteSql {

	// 匹配规则
	protected final String pattern = "#\\{(.*?)\\}";
	//生成正则对象
	protected final Pattern r = Pattern.compile(pattern);
	

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
    public Object select(String sql, Map<String,Object> valueMap,Class<?> clazz,Method method) throws SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException {
		/*注入值的对象*/
		/*连接器*/
		DruidPooledConnection connection = TomkingConfig.instance().getDatabase().getConnection() ;
        /*匹配*/
        Matcher m = r.matcher(sql);
        /*存field的列表*/
        List<String> fieldList=  new ArrayList<>();
        while(m.find()) {
        	System.out.println(m.group(1));
        	fieldList.add(m.group(1));
        }
        // 将字符串绑定的问号
		String solveSql = sql.replaceAll(pattern, "?");
//        System.out.println(solveSql);
		// 绑定参数的对象
		PreparedStatement preparedStatement = getPreparedStatement(fieldList,solveSql,valueMap,method,connection);

        System.out.println(preparedStatement.toString());;
        //执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        /*将数据转成对象列表*/
        return ResultSetUtils.populate(resultSet, clazz);
    }

    /**
     * 执行修改数据库的消息
     * @param sql
     * @param valueMap
     * @param clazz
     * @return 返回大于0的数是正常执行，返回-1 表示执行失败
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     * @throws InstantiationException
     */
	public Object modify(String sql, Map<String,Object> valueMap,Method method) throws SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException {
		/*注入值的对象*/
		/*连接器*/
		DruidPooledConnection connection = TomkingConfig.instance().getDatabase().getConnection() ;
        /*匹配*/
        Matcher m = r.matcher(sql);
        /*存field的列表*/
        List<String> fieldList=  new ArrayList<>();
        while(m.find()) {
        	System.out.println(m.group(1));
        	fieldList.add(m.group(1));
        }
        // 将字符串绑定的问号
		String solveSql = sql.replaceAll(pattern, "?");
//        System.out.println(solveSql);
		// 绑定参数的对象
		PreparedStatement preparedStatement = getPreparedStatement(fieldList,solveSql,valueMap,method,connection,true);
		System.out.println(preparedStatement.toString());;

		try {
			// 执行更新
			preparedStatement.executeUpdate();
			// 获取更新ID 
			if(sql.startsWith("insert")) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				if(resultSet.next()) {
					return resultSet.getInt(1);
				}
			// 如果是delete、update返回1
			}else if(sql.startsWith("delete") || sql.startsWith("update")) {
				// update,delete都返回1
				return 1;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		// 如果走到这一步说明执行失败
		return -1;
    }
    

    /**
     * 获取preparedStatement对象
     * @param fieldList 参数顺序表
     * @param solveSql 处理后的SQL，都是问号
     * @param valueMap 值的Map
     * @param connection
     * @return
     * @throws SQLException
     */
    public PreparedStatement getPreparedStatement(List<String>  fieldList
    		,String solveSql,Map<String,Object> valueMap,Method method,DruidPooledConnection connection) throws SQLException {
    	return getPreparedStatement(fieldList,solveSql,valueMap,method,connection,false);
    }

    /**
     * 获取preparedStatement，设置isModify，可以拿到键值
     * @param fieldList
     * @param solveSql
     * @param valueMap
     * @param method
     * @param isModify 默认值是false
     * @param connection
     * @return
     * @throws SQLException
     */
    public PreparedStatement getPreparedStatement(List<String>  fieldList
    		,String solveSql,Map<String,Object> valueMap,Method method,DruidPooledConnection connection,boolean isModify) throws SQLException {
        /*匹配sqwl*/
    	//如果isModify为true，则拿到return_generate_keys，为false，则返回默认值
        PreparedStatement preparedStatement =  isModify ?
        		connection.prepareStatement(solveSql,Statement.RETURN_GENERATED_KEYS) : connection.prepareStatement(solveSql);
		BeanMap objectMap;
		String key;
		Object valueObject ;
        for(int i = 0; i < fieldList.size(); i++){
        	// 在这里将obj设置成mapBean
        	String paramStr = fieldList.get(i);
//        	System.out.println("Exec Select value: "+fieldList.get(i));

        	String[] objectParamDecompose = paramStr.split("\\.");
//        	System.out.println("length = "+objectParamDecompose.length+" "+Arrays.toString(objectParamDecompose));

        	// 如果数据长度为0 ，则说明只有一个参数
        	if(objectParamDecompose.length == 1) {
				// 这需要做类型判断
        		// 可以做一个方法，传入什么类型的参数做什么类型的判断
				preparedStatement.setString(i+1,valueMap.get(objectParamDecompose[0]).toString());

        	}else if(objectParamDecompose.length == 2) {
        		key = objectParamDecompose[0];

        		// 拿到key对应的值
        		valueObject = valueMap.get(key);
        		if(valueObject == null) {
        			// TODO: 这里还是需要加强一下，把方法和类都补上
        			throw new MapperParamBindingException(method.getDeclaringClass().getName()+"."+method.getName()+": "+paramStr+"不存在");
        		}
        		// 如果没有转成BeanMap先转成BeanMap
        		if(!( valueObject instanceof BeanMap)) {
        			objectMap  = new BeanMap(valueObject);
        			valueMap.put(key, objectMap);
        		}else {
        			// 因为之前已经转，这里直接强转
        			objectMap = (BeanMap)valueMap.get(key);
        		}
        		// 判断拿 到的值是不是空
        		Object value = objectMap.get(objectParamDecompose[1]);

//        			throw new MapperParamBindingException(method.getDeclaringClass().getName()+"."+method.getName()+": "+paramStr+"不存在");
        		if(value == null) {
					preparedStatement.setString(i+1,"NULL");
        		}else {
					preparedStatement.setObject(i+1,objectMap.get(objectParamDecompose[1]).toString());
        		}
        	}
        }
        
        return preparedStatement;
    }
    
}
