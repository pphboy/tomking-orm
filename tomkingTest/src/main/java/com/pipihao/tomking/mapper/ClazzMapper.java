package com.pipihao.tomking.mapper;

import com.pipihao.tomking.pojo.Clazz;
import org.tomking.Tomking.Annotation.exec.Select;
import org.tomking.Tomking.Annotation.mapper.Param;

import java.util.List;

/**
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/3 17:34
 */
public interface ClazzMapper {

    @Select("select * from class where id = #{id} and cname = #{cname}")
    List<Clazz> getClazz(@Param("id") Integer id, @Param("cname") String cname);
}
