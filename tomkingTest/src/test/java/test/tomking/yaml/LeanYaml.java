package test.tomking.yaml;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.tomking.Tomking.config.TomkingConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;

/**
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/2 17:16
 */
public class LeanYaml {

    /**
     * 解析yml学习
     */
    @Test
    public void firstYmlRead() throws FileNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        long start = System.currentTimeMillis();
        Yaml yaml = new Yaml();
        URL url = Test.class.getClassLoader().getResource("tomking.yml");
        if(url != null){
            Map obj = (Map)yaml.load(new FileInputStream(url.getFile()));
            System.out.println(obj);
            /*obj转成tomking*/
            TomkingConfig tomkingConfig = JSON.parseObject(JSON.toJSONString(obj), TomkingConfig.class);
            System.out.println(tomkingConfig);
        }
        System.out.println("花了"+(System.currentTimeMillis()-start)+"毫秒");
    }

    @Test
    public void testTomkingConfig(){
        long start = System.currentTimeMillis();
        System.out.println(TomkingConfig.instance());
        System.out.println("第一次：花了"+(System.currentTimeMillis() - start)+"毫秒");
        start = System.currentTimeMillis();
        System.out.println(TomkingConfig.instance());
        System.out.println("第二次：花了"+(System.currentTimeMillis()-start)+"毫秒");
    }
}
