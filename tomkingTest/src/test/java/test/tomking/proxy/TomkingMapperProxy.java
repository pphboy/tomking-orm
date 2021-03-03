package test.tomking.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/3 17:28
 */
public class TomkingMapperProxy  implements InvocationHandler {
    private Object object;

    public TomkingMapperProxy(Object object){
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(proxy);
        return proxy;
    }
}
