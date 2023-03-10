package org.tomking.Tomking.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tomking.Tomking.Annotation.exec.Modify;
import org.tomking.Tomking.Annotation.exec.Select;
import org.tomking.Tomking.Annotation.mapper.Param;
import org.tomking.Tomking.Utils.ExecuteSql;
import org.tomking.Tomking.config.TomkingConfig;
import org.tomking.Tomking.exception.ExecAnnotationLoadException;


public class MapperFactory {
	
	/**
	 * <h1> 数据库操作注解 </h1>
	 * 存储着操作数据库的注解，Modify 和 Select 后面如果有的话会再加
	 * 因为这判断这两个，如果都没有的话，或者超过了，需要报错
	 */
	List<Class<? extends Annotation>> execAnnotationlist = Arrays.asList(Modify.class,Select.class);
	
	/**
	 * SQL执行器
	 */
	public final ExecuteSql executeSql = new ExecuteSql();
	
	public MapperFactory() {}
	
	/**
	 * @param tomkingConfigFilePath 指定tomking的配置文件
	 */
	public MapperFactory(String tomkingConfigFilePath) {
		TomkingConfig.instance(tomkingConfigFilePath);
	}
	

	/**
	 * 获取Mapper映射类的实例
	 * @param <V>
	 * @param instanceClass
	 * @return
	 */
	public <V> V getMapperInstance(Class instanceClass) {
		System.out.println(instanceClass.getInterfaces());
        Object obj = Proxy.newProxyInstance(instanceClass.getClassLoader(),new Class[]{instanceClass}, new InvocationHandler() {
        	/**
        	 * 这个方法是指在Mapper接口调用方法的时候，生成的一个代理。
        	 */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                
				/*将以Param命名的变量的值 于Param的值对应起来*/
            	// 每个方法都需要对应一个键值绑定
				Map<String,Object> map = new HashMap<>(); // map的创建与否取决于该类是不是一个对象，或者是有对象的
				

                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
				// 在这里直接生成一个KeyValue的数组，绑定好数据和占位符的值
				// 获取Param，并且将Param和 SQL 的参数对上，如果对不上则报错
                for (int i = 0; i < method.getParameters().length; i++) {
                    for(Annotation annotation: method.getParameters()[i].getAnnotations()){
                        if(annotation instanceof Param){
                            Param param = (Param) annotation;
                            /*将参数的值添加以Key:Value形式添加到内*/
                            // 这里的数据存进去，在用的时候判断 参数是不是带点就行
                            map.put(param.value(),args[i]);
                            System.out.println("value:"+param.value() +" arg: "+args[i]);
                        }
                    }
                }

                // 判断 Annotation的数量，Exec的数量必须等于1
                List<Annotation> annotationList = new ArrayList();

                // 把这个操作数据库的注解全部拿出来
                for(Class tmpAnno: execAnnotationlist) {
                	Annotation tmp = method.getAnnotation(tmpAnno);
                	if(tmp != null) {
						annotationList.add(tmp);
                	}
                }
                // 如果没有写注解，直接返回空即可
                if(annotationList.size() == 0) {
                	return null;
                }
                // exec包下的 注解不能超过一个，目前仅有 Select 和 Modify，如果超过一个则是无法理解
                if( annotationList.size() > 1) {
                	throw new ExecAnnotationLoadException(method.getDeclaringClass().getName()+"."+method.getName()+"注解不能过超过两个");
                } 
                
                // 因为Mapper的方法只会存在一个Exec的注解
                Annotation currentAnnotation = annotationList.get(0);
//				Class annotationClassType = currentAnnotation.annotationType();

				if(currentAnnotation instanceof  Modify) {
					return modify(method,args,map);
				}else if(currentAnnotation instanceof Select) {
					return select(method,args,map);
				}
				
				// 如果即不是修改也不是查询，则返回Null
				return null;

            }
        });
        
        return (V)obj;
	}
	/**
	 * 插入、删除、更新，都是归为此 modify方法
	 * 如果是传入对象，需要匹配其名称和对应的键，并把 值映射的位置做出来，然后插入到 列表内，再使用prepared
	 * @param method
	 * @param args
	 * @param paramArgMap
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws InstantiationException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 */
	public Object modify(Method method,Object[] args,Map<String,Object> paramArgMap) throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException, InstantiationException, SQLException {
		Modify modify = (Modify)method.getAnnotation(Modify.class);
		System.out.println("Modify Value: "+modify.value());
		/*返回的类型*/
		Type genericReturnType = method.getGenericReturnType();
		
		System.out.println("modify paramArgMap"+paramArgMap);
		return executeSql.modify(modify.value(), paramArgMap,method);
	}
	
	/**
	 * 查询操作
	 * @param method
	 * @param args
	 * @param paramArgMap
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws NoSuchFieldException
	 * @throws InstantiationException
	 * @throws SQLException
	 */
	public Object select(Method method,Object[] args,Map<String,Object> paramArgMap) throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException, InstantiationException, SQLException {
		/*获取查询语句的值*/
		Select select = (Select)method.getAnnotation(Select.class);
		System.out.println("select sqlStr: "+select.value());
		/*返回的类型*/
		Type genericReturnType = method.getGenericReturnType();
		Class<?> generic = null;
		/*获取List泛型的class*/
		if(genericReturnType instanceof  ParameterizedType){
			ParameterizedType pt = (ParameterizedType) genericReturnType;
			generic =  Class.forName(pt.getActualTypeArguments()[0].getTypeName());
			System.out.println("select generic: "+generic);
		}
		System.out.println("select paramArgMap: "+paramArgMap);

		return executeSql.select(select.value(),paramArgMap,generic,method);
	}
	
}
