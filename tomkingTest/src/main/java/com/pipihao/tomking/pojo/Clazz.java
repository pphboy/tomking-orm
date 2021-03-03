package com.pipihao.tomking.pojo;

/**
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/2 20:15
 */
public class Clazz {

    public Clazz() {
    }

    public Clazz(Integer id, String cname) {
        this.id = id;
        this.cname = cname;
    }

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
