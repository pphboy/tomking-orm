package org.tomking.Tomking.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.tomking.Tomking.config.chid.DB;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;

/**
 * Tomking的配置类<br>
 * 直接在这个类初始化配置文件 ，且为单例模式
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/2 17:21
 */
public class TomkingConfig {

    private static TomkingConfig tomkingConfig= null;

    public static TomkingConfig instance(){
        if(ObjectUtils.isEmpty(tomkingConfig)){
            try {
                Yaml yaml = new Yaml();
                URL url =TomkingConfig.class.getClassLoader().getResource("tomking.yml");
                if(url != null){
                    Object obj = null;
                    obj = yaml.load(new FileInputStream(url.getFile()));
//                    System.out.println(obj);
                    /*obj转成tomking*/
                    tomkingConfig = JSON.parseObject(JSON.toJSONString(obj), TomkingConfig.class);
//                    System.out.println(tomkingConfig);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return tomkingConfig;
        }else{
            return tomkingConfig;
        }
    }


    private DruidDataSource db;

    public DruidDataSource getDb() {
        return db;
    }

    public void setDb(DruidDataSource db) {
        this.db = db;
    }

    /*
    private DB db;
    public DB getDb() {
        return db;
    }
    public void setDb(DB db) {
        this.db = db;
    }
*/

    @Override
    public String toString() {
        return "TomkingConfig{" +
                "db=" + db +
                '}';
    }
}
