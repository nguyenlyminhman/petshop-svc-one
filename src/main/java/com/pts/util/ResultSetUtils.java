package com.pts.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.sql.Clob;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class ResultSetUtils{
    private static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    // Step 1: Convert List<Object[]> + columns[] => List<Map<String, Object>>
    public static List<Map<String, Object>> toMapList(List<Object[]> rows, String[] columns) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new LinkedHashMap<>();
            for (int i = 0; i < columns.length; i++) {
                map.put(columns[i], row[i]);
            }
            result.add(map);
        }
        return result;
    }

    // Step 2: Convert a Map to a POJO with auto JSON string → object support
    public static <T> T mapToPojo(Map<String, Object> map, Class<T> clazz) {
        try {
            Map<String, Object> converted = new HashMap<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String fieldName = entry.getKey();
                Object value = entry.getValue();

                try {
                    Field field = clazz.getDeclaredField(fieldName);
                    Class<?> targetType = field.getType();

                    if (value instanceof Clob) {
                        value = clobToString((Clob) value);
                    }

                    // Nếu là chuỗi JSON và kiểu dữ liệu là object → parse
                    if (value instanceof String && !isPrimitiveOrWrapper(targetType)) {
                        String strVal = (String) value;
                        if (looksLikeJson(strVal)) {
                            value = objectMapper.readValue(strVal, targetType);
                        }
                    }

                    // Nếu là Timestamp → LocalDateTime
                    if (value instanceof Timestamp && targetType == LocalDateTime.class) {
                        value = ((Timestamp) value).toLocalDateTime();
                    }

                    converted.put(fieldName, value);
                } catch (NoSuchFieldException ignore) {
                    // Field không tồn tại trong class → bỏ qua
                }
            }

            return objectMapper.convertValue(converted, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Mapping failed", e);
        }
    }

    // Step 3: Convert full list
    public static <T> List<T> mapListToPojoList(List<Object[]> rows, String[] columns, Class<T> clazz) {
        List<Map<String, Object>> maps = toMapList(rows, columns);
        List<T> result = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            result.add(mapToPojo(map, clazz));
        }
        return result;
    }

    private static boolean isPrimitiveOrWrapper(Class<?> type) {
        return type.isPrimitive()
                || type == String.class
                || type == Integer.class
                || type == Long.class
                || type == Double.class
                || type == Boolean.class
                || type == Short.class
                || type == Byte.class
                || type == Float.class
                || type == Character.class
                || type == LocalDateTime.class;
    }

    private static String clobToString(Clob clob) {
        try (Scanner scanner = new Scanner(clob.getCharacterStream())) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (Exception e) {
            throw new RuntimeException("Failed to read CLOB", e);
        }
    }

    private static boolean looksLikeJson(String str) {
        str = str.trim();
        return (str.startsWith("{") && str.endsWith("}"))
                || (str.startsWith("[") && str.endsWith("]"));
    }

}
