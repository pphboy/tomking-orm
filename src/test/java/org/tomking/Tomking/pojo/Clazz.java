package org.tomking.Tomking.pojo;

/**
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/2 20:15
 */
public class Clazz {
    private Integer id;
    private String cname;

    @Override
    public String toString() {
        return "Class{" +
                "id=" + id +
                ", cname='" + cname + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
