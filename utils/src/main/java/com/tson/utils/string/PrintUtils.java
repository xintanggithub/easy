package com.tson.utils.string;

import java.lang.reflect.Field;

/**
 * Date 2020-09-23 16:06
 *
 * @author Tson
 */
public class PrintUtils {

    public static <T> String modelToString(T t) {
        if (null == t) {
            return "params object is null, object to string error！";
        }
        StringBuilder result = new StringBuilder("\n");
        result.append(t.getClass().getName()).append("\n");
        for (Field declaredField : t.getClass().getDeclaredFields()) {
            try {
                result.append(declaredField.getName()).append(" = ").append(declaredField.get(t))
                        .append("\n");
            } catch (IllegalAccessException e) {
                declaredField.setAccessible(true);
                try {
                    result.append(declaredField.get(t)).append("\n");
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
                declaredField.setAccessible(false);
            }
        }
        return result.substring(0, result.length() - 1);
    }
}
