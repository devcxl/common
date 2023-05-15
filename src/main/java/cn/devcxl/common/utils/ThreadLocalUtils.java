package cn.devcxl.common.utils;

/**
 * @author Devcxl
 */
public class ThreadLocalUtils<T> {

    public static final ThreadLocal<String> STRING_THREAD_LOCAL = new ThreadLocal<>();

    public static String get() {
        return STRING_THREAD_LOCAL.get();
    }

    public static void set(String name) {
        ThreadLocalUtils.STRING_THREAD_LOCAL.set(name);
    }

    public static void remove() {
        STRING_THREAD_LOCAL.remove();
    }
}
