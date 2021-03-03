package test.tomking.proxy;

import org.junit.Test;
import org.tomking.Tomking.Annotation.exec.Select;
import org.tomking.Tomking.Annotation.mapper.Param;
import com.pipihao.tomking.mapper.ClazzMapper;
import org.tomking.Tomking.Utils.ExecuteSql;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 学习代理
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/3 17:33
 */
public class LearnProxy {

    @Test
    public void learnProxy(){
    }

    @Test
    public void testFs() throws IllegalAccessException, InstantiationException {

        System.out.println(ClazzMapper.class.getInterfaces());
        Object obj = Proxy.newProxyInstance(ClazzMapper.class.getClassLoader(),new Class[]{ClazzMapper.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                for(Object obj : args){
                    System.out.println(obj);
                }
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                /*将以Param命名的变量的值 于Param的值对应起来*/
                Map<String,Object> map = new HashMap<>(); // map的创建与否取决于该类是不是一个对象，或者是有对象的
                for (int i = 0; i < method.getParameters().length; i++) {
                    for(Annotation annotation: method.getParameters()[i].getAnnotations()){
                        System.out.println(annotation);
                        if(annotation instanceof Param){
                            Param param = (Param) annotation;
                            /*将参数的值添加以Key:Value形式添加到内*/
                            map.put(param.value(),args[i]);
                        }
                    }
                }
                /*获取查询语句的值*/
                Select select = (Select)method.getAnnotation(Select.class);
                System.out.println(select.value());
                /*返回的类型*/
                Type genericReturnType = method.getGenericReturnType();
                Class<?> generic = null;
                /*获取List泛型的clasws*/
                if(genericReturnType instanceof  ParameterizedType){
                    ParameterizedType pt = (ParameterizedType) genericReturnType;
                    generic =  Class.forName(pt.getActualTypeArguments()[0].getTypeName());
                    System.out.println(generic);
                }
                System.out.println(map);
                return ExecuteSql.select(select.value(),map,generic);
            }
        });

        ClazzMapper clazzMapper = (ClazzMapper)obj;
        System.out.println(clazzMapper.getClazz(2,"计应2班"));;

    }
}
