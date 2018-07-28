package com.outsource.util;

/**
 * @author chuanchen
 */
public class StringUtils {
    private StringUtils() {
    }

    /**
     * 密码加密
     *
     * @param str
     * @return
     */
    public static String encryption(String str) {
        return str;
    }

    public static boolean isEmpty(String... strArray) {
        if (strArray == null) {
            return true;
        }
        for (String str : strArray) {
            if (org.springframework.util.StringUtils.isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotEmpty(String... strArray) {
        return !isEmpty(strArray);
    }

    public static String generateJsonSession(String account) {
        return account + System.currentTimeMillis();
    }
}
