package org.tomking.Tomking.exception;

/**
 * Tomking配置类加载异常
 * @author pipihao
 * @email pphboy@qq.com
 * @date 2021/3/3 16:45
 */
public class TomkingFirstLoadException extends RuntimeException{

    public TomkingFirstLoadException() {
    }

    public TomkingFirstLoadException(String message) {
        super(message);
    }

    public TomkingFirstLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public TomkingFirstLoadException(Throwable cause) {
        super(cause);
    }

    public TomkingFirstLoadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
