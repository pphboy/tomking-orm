package test.tomking.factory;

import org.junit.Test;
import com.pipihao.tomking.pojo.Clazz;

/**
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/3 16:58
 */
public class TestFactory {

    @Test
    public void testFactory() throws InstantiationException, IllegalAccessException {
        TomkingSessionFactory.getSession(Clazz.class);
    }
}
