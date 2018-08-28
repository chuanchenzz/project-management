package com.outsource.util;

import java.util.UUID;

/**
 * @author chuanchen
 */
public class UUIDUtils {
    private UUIDUtils(){}

    public static String generateUUID(){
        UUID id = UUID.randomUUID();
        return id.toString().replace("-","");
    }
}
