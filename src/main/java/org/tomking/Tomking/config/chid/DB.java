package org.tomking.Tomking.config.chid;

/**
 * DB数据
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/2 17:22
 */
public class DB {
    private String url;
    private String username;
    private String password;
    private String driver;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "DB{" +
                "url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ",driver='" + driver+ '\'' +
                '}';
    }
}
