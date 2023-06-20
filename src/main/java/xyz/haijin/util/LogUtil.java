package xyz.haijin.util;

import xyz.haijin.conf.SocketConfig;

/**
 * 日志打印工具类
 * <p>
 * 2023/6/20.
 *
 * @author haijin
 * @version 1.0
 */
public class LogUtil {

    /**
     * 日志打印工具类
     * @param object 需要打印的实体
     */
    public static void out(Object object) {
        if (SocketConfig.IS_PRINT) {
            System.out.println(object);
        }
    }

}
