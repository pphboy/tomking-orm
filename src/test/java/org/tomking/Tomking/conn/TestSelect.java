package org.tomking.Tomking.conn;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSON;
import com.sun.javaws.IconUtil;
import org.apache.commons.collections.SetUtils;
import org.junit.Test;
import org.tomking.Tomking.Utils.ResultSetUtils;
import org.tomking.Tomking.config.TomkingConfig;
import org.tomking.Tomking.pojo.Clazz;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.function.DoubleToIntFunction;

/**
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/2 19:46
 */
public class TestSelect {

    /**
     * 这是查询，查询成功
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchFieldException
     */
    @Test
    public void testConn() throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        DruidPooledConnection connection = TomkingConfig.instance().getDb().getConnection();
        String sql= "select * from class";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List list = ResultSetUtils.populate(resultSet, Clazz.class);
        if(list.get(0) instanceof Clazz){
            System.out.println("是Clazz的实例");
        }
        System.out.println(list);
    }
}
