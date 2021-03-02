package org.tomking.Tomking.Utils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 将ResultSet转成List<形式>
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/2 20:13
 */
public class ResultSetUtils {


    /**
     * 将resultSet转成List<> 形式
     * @param resultSet
     * @param clazz
     * @return
     */
    public static List populate(ResultSet resultSet, Class<?> clazz) throws IllegalAccessException, InstantiationException, SQLException, NoSuchFieldException {
        Object obj = null;
        List list = new ArrayList();
        while (resultSet.next()){
            obj =clazz.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for(int i = 0;i < metaData.getColumnCount(); i++){
                String name = metaData.getColumnName(i+1);
                Field field= obj.getClass().getDeclaredField(name);
                field.setAccessible(true);
                field.set(obj,resultSet.getObject(name));
            }
            System.out.println(resultSet.getInt("id")+"-"+resultSet.getString("cname"));
            list.add(obj);
        }
        return list;
    }
}
