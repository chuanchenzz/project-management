package com.outsource.util;

/**
 * @author chuanchen
 */
public class KeyUtil {
    private KeyUtil() {
    }

    /**
     * 生成key
     * @param prefix
     * @param suffix
     * @param <T>
     * @return String
     */
    public static <T> String generateKey(String prefix, T suffix) {
        return (prefix + suffix).intern();
    }
}
