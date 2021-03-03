package test.tomking.factory;

/**
 * 用于生成 接口的实例
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/3 16:53
 */
public class TomkingSessionFactory {

    /**
     * 生成接口对象
     * @param clazz
     * @return
     */
    public static Object getSession(Class<?> clazz) throws IllegalAccessException, InstantiationException {
        if(clazz.isInterface()){
        }
        return null;
    }
}
