package com.example.maintainer.product.util;

import java.lang.reflect.Field;

public class CustomReflectionUtil {

  public static void setFieldValues(Object targetObject, Object sourceObject) {
    Field[] fields = sourceObject.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      try {
        Object value = field.get(sourceObject);
        if (value == null) {
          continue;
        }
        Field targetField = targetObject.getClass().getDeclaredField(field.getName());
        targetField.setAccessible(true);
        targetField.set(targetObject, value);
      } catch (NoSuchFieldException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }
}
