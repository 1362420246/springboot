package com.qbk.utils;

/**
 * @Author: quboka
 * @Date: 2018/9/18 12:50
 * @Description: integer
 */
public class IntegerUtil {

    public static boolean isEmpty(Integer integer) {
        if (integer == null) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Integer integer) {
        return !isEmpty(integer);
    }

    public static boolean isEmptyByArr(Integer... integerArr ) {
        for (Integer integer : integerArr) {
            if (integer == null ) {
                return true;
            }
        }
        return false;
    }
}
