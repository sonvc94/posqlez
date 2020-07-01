package me.geardao.posqlez.json;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ObjectMapper<T> {

    private Class clazz;
    private Map<String, Field> fields = new HashMap<>();
    Map<String, String> errors = new HashMap<>();


    public ObjectMapper(Class clazz) {
        this.clazz = clazz;
        List<Field> fieldList = Arrays.asList(clazz.getDeclaredFields());
        for (Field field : fieldList) {
            Column col = field.getAnnotation(Column.class);
            if (col != null) {
                field.setAccessible(true);
                fields.put(col.name(), field);
            }
        }
    }

    /**
     * @param row tuong ung 1 row trong database tra ve
     */
    public T map(Map<String, Object> row) throws SQLException {
        try {
            T dto = (T) clazz.getConstructor().newInstance();
            for (Map.Entry<String, Object> entity : row.entrySet()) {
                if (entity.getValue() == null) {
                    continue;  // Don't set DBNULL
                }
                String column = entity.getKey();
                Field field = fields.get(column);
                if (field != null) {
                    field.set(dto, convertInstanceOfObject(entity.getValue()));
                }
            }
            return dto;
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            throw new SQLException("Problem with data Mapping. See logs.");
        }
    }

    public List<T> map(List<Map<String, Object>> rows) throws SQLException {
        List<T> list = new LinkedList<>();
        for (Map<String, Object> row : rows) {
            list.add(map(row));
        }
        return list;
    }

    public List<T> map(ResultSet resultSet) throws SQLException {
        List<T> list = new LinkedList<>();
        while (resultSet.next()) {
            try {
                T dto = (T) clazz.getConstructor().newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field f : fields) {
                    Column column = f.getAnnotation(Column.class);
                    f.setAccessible(true);
                    System.out.println(resultSet.getInt("salary"));
                    if (f.getType().isAssignableFrom(String.class)) {
                        f.set(dto, convertInstanceOfObject(resultSet.getString(column.name())));
                    } else if (f.getType().isAssignableFrom(int.class)){
                        f.set(dto, convertInstanceOfObject(resultSet.getInt(column.name())));
                    } else if (f.getType().isAssignableFrom(Integer.class)){
                        f.set(dto, convertInstanceOfObject(resultSet.getInt(column.name())));
                    }
                }
                list.add(dto);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private T convertInstanceOfObject(Object o) {
        try {
            return (T) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

}
