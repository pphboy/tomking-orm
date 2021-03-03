package org.tomking.Tomking.Annotation.mapper;

import java.lang.annotation.*;

/**
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/3 19:13
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Param {

    /*名字*/
    String value();
}
