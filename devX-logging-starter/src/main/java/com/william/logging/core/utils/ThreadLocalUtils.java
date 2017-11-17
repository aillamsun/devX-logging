package com.william.logging.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * ThreadLocal 工具类,通过在ThreadLocal存储map信息,来实现在ThreadLocal中维护多个信息
 *
 * Created by sungang on 2017/11/17.
 */
public class ThreadLocalUtils {

    private static final ThreadLocal<Map<String, Object>> local = ThreadLocal.withInitial(HashMap::new);

    /**
     * @return threadLocal中的全部值
     */
    public static Map<String, Object> getAll(){
        return local.get();
    }

    /**
     * 设置一个值到ThreadLocal
     *
     * @param key   键
     * @param value 值
     * @param <T>   值的类型
     * @return 被放入的值
     * @see Map#put(Object, Object)
     */
    public static <T> T put(String key, T value) {
        local.get().put(key, value);
        return value;
    }

    /**
     * 删除参数对应的值
     *
     * @param key
     * @see Map#remove(Object)
     */
    public static void remove(String key) {
        local.get().remove(key);
    }

    /**
     * 清空ThreadLocal
     *
     * @see Map#clear()
     */
    public static void clear() {
        local.remove();
    }

    /**
     * 从ThreadLocal中获取值
     *
     * @param key 键
     * @param <T> 值泛型
     * @return 值, 不存在则返回null, 如果类型与泛型不一致, 可能抛出{@link ClassCastException}
     * @see Map#get(Object)
     * @see ClassCastException
     */
    public static <T> T get(String key) {
        return ((T) local.get().get(key));
    }

    /**
     * 从ThreadLocal中获取值,并指定一个当值不存在的提供者
     *
     * @see Supplier
     * @since 3.0
     */
    public static <T> T get(String key, Supplier<T> supplierOnNull) {
        return ((T) local.get().computeIfAbsent(key, k -> supplierOnNull.get()));
    }

    /**
     * 获取一个值后然后删除掉
     *
     * @param key 键
     * @param <T> 值类型
     * @return 值, 不存在则返回null
     * @see this#get(String)
     * @see this#remove(String)
     */
    public static <T> T getAndRemove(String key) {
        try {
            return get(key);
        } finally {
            remove(key);
        }
    }
}
