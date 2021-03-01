package org.tomking.Tomking.Utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.tomking.Tomking.Annotation.pojo.Column;
import org.tomking.Tomking.Annotation.pojo.Table;
import org.tomking.Tomking.enums.FieldBind;

/**
 * 
 * @author pipihao@qq.com
 *
 */
public class SqlMakerUtils {
	
	/**
	 * 通过 实例类 生成建表代码
	 * @param clazz
	 * @return
	 */
	public static String getMakePojoToTableSql(Class<?> clazz) {
		Long begin = System.currentTimeMillis();
		StringBuilder sb = null ;
		System.out.println("class name: "+clazz.getName());
		if(clazz.isAnnotationPresent(Table.class)) {
			sb = new StringBuilder();
			Table table = clazz.getAnnotation(Table.class);
			System.out.println(table.name());
			sb.append(" CREATE TABLE IF NOT EXISTS `"+table.name()+"` (");
			System.out.println(clazz.getDeclaredFields());
			Field[] fields = clazz.getDeclaredFields();
			for(Field f:fields) {
				if(f.isAnnotationPresent(Column.class)) {
					Column c = f.getAnnotation(Column.class);
					sb.append(c.name()+' '+typeToSqlString(f.getType()));
					//如果没有设置长度 给 varchar 设置长度
					if(String.class.equals(f.getType()) && c.length() == 0) {
						sb.append("(255) ");
					}
					//添加约束
					for(FieldBind fb: c.bind()) {
						sb.append(fb.getName());
					}
					System.out.println("COLUMN :"+f.getName()+" type: " +f.getType());
					System.out.println("name : "+c.name()+" length : "+c.length());
					System.out.println(typeToSqlString(f.getType()));
					if(f.equals(fields[fields.length -1])) break;
					sb.append(',');
				}
			}
			sb.append(")ENGINE="+table.engine()+" DEFAULT CHARSET=utf8;");
		}
		System.out.println(sb.toString());
		System.out.println("生成建表语句使用"+(System.currentTimeMillis() - begin)+"毫秒");
		return sb.toString();
	}
	
	public static String typeToSqlString(Class<?> clazz) {
		if(String.class.equals(clazz)) return " VARCHAR ";
		if(Integer.class.equals(clazz)) return " INT ";
		if(Double.class.equals(clazz)) return " DOUBLE ";
		if(Float.class.equals(clazz)) return " FLOAT ";
		if(Long.class.equals(clazz)) return " BIGINT ";
		if(Boolean.class.equals(clazz)) return " TINYINT(1) ";
//		if(Boolean.class.equals(clazz)) return " varchar ";
		throw new RuntimeException("字段无属性名 ERROR");
	}
	
}
