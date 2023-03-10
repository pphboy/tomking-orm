package org.tomking.Tomking.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ObjectUtils;
import org.tomking.Tomking.exception.TomkingFirstLoadException;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

/**
 * Tomking的配置类<br>
 * 直接在这个类初始化配置文件 ，且为单例模式
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/2 17:21
 */
public class TomkingConfig {

    private static TomkingConfig tomkingConfig= null;

    /**
     * 无文件初始化
     * @return
     */
    public static TomkingConfig instance(){
    	return instance("tomking.yml");
    }

    /**
     * 有文件初始化
     * @param configPath
     * @return
     */
    public static TomkingConfig instance(String configPath){
        if(ObjectUtils.isEmpty(tomkingConfig)){
            try {
                ClassLoader classLoader = TomkingConfig.class.getClassLoader();
                InputStream inputStream = classLoader.getResourceAsStream(configPath);
                if(inputStream == null) {
                    throw new TomkingFirstLoadException(configPath+" file not found. It is a main config of Tomking Frame");
                }

                Yaml yaml = new Yaml();
                URL url =TomkingConfig.class.getClassLoader().getResource(configPath);
                
                if(url != null){
                    Object obj = null;
                    obj = yaml.load(new FileInputStream(url.getFile()));
//                    System.out.println(obj);
                    /*obj转成tomking*/
                    tomkingConfig = JSON.parseObject(JSON.toJSONString(obj), TomkingConfig.class);
//                    System.out.println(tomkingConfig);
                }else{
                    throw new TomkingFirstLoadException(configPath+" file not found. It is a main config of Tomking Frame");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return tomkingConfig;
        }else{
            return tomkingConfig;
        }
    }

    private DruidDataSource database;

    public DruidDataSource getDatabase() {
        return database;
    }

    public void setDatabase(DruidDataSource database) {
    	this.database = database;
    }
    
    
}
