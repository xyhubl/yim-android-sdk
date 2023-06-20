package xyz.haijin.conf;

/**
 * socket配置类
 * <p>
 * 2023/6/20.
 *
 * @author haijin
 * @version 1.0
 */
public class SocketConfig {

    /**
     * 连接地址
     */
    public static final String URL = "ws://120.27.141.27:8081/sub";

    /**
     * 读取超时时间（秒）
     */
    public static final Integer READ_TIME_OUT = 3;

    /**
     * 写的超时时间（秒）
     */
    public static final Integer WRITE_TIME_OUT = 3;

    /**
     * 连接超时时间（秒）
     */
    public static final Integer CONNECT_TIME_OUT = 10;

    /**
     * 心跳请求间隔时间
     */
    public static final Integer HEART_BEAT_TIME = 5;

    /**
     * 延迟请求时间
     */
    public static final Integer HEART_BEAT_DELAY_TIME = 0;

    /**
     * 是否打印日志
     */
    public static final Boolean IS_PRINT = false;

}
