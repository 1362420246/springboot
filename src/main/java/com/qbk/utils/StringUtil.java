package com.qbk.utils;

import java.util.UUID;

/**
 * @Author: quboka
 * @Date: 2018/9/17 12:56
 * @Description: 字符串
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmptyByArr(String... strArr ) {
        for (String str : strArr) {
            if (str == null || str.length() == 0) {
                return true;
            }
        }
        return false;
    }

    public static String getUUID()
    {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("\\-", "");
    }

}
