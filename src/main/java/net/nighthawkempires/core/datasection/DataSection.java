package net.nighthawkempires.core.datasection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DataSection extends Map<String, Object> {

    default boolean isSet(String p_String) {
        return containsKey(p_String) && get(p_String) != null;
    }

    default Object get(String p_String, Object p_Object) {
        if (containsKey(p_String))
            return get(p_String);
        return p_Object;
    }

    DataSection getSectionNullable(String p_String);

    default boolean isSection(String p_String) {
        return get(p_String) instanceof Map;
    }

    default Object getNullable(String p_String) {
        if (containsKey(p_String))
            return get(p_String);
        return null;
    }

    default String getString(String p_String) {
        return get(p_String).toString();
    }

    default String getString(String p_String, String p_String2) {
        return get(p_String, p_String2).toString();
    }

    default String getStringNullable(String p_String) {
        return containsKey(p_String) ? getString(p_String) : null;
    }

    default boolean isString(String p_String) {
        return get(p_String) instanceof String;
    }

    default int getInt(String p_String) {
        return getDouble(p_String).intValue();
    }

    default int getInt(String p_String, int i) {
        return getDouble(p_String, i).intValue();
    }

    default Integer getIntNullable(String p_String) {
        return containsKey(p_String) ? getInt(p_String) : null;
    }

    default boolean isInt(String p_String) {
        return get(p_String) instanceof Integer;
    }

    default boolean getBoolean(String p_String) {
        return Boolean.parseBoolean(getString(p_String));
    }

    default boolean getBoolean(String p_String, boolean b) {
        return Boolean.parseBoolean(get(p_String, b).toString());
    }

    default Boolean getBooleanNullable(String p_String) {
        return containsKey(p_String) ? getBoolean(p_String) : null;
    }

    default boolean isBoolean(String p_String) {
        return get(p_String) instanceof Boolean;
    }

    default Double getDouble(String p_String) {
        return Double.parseDouble(get(p_String).toString());
    }

    default Double getDouble(String p_String, double v) {
        return Double.parseDouble(get(p_String, v).toString());
    }

    default Double getDoubleNullable(String p_String) {
        return containsKey(p_String) ? getDouble(p_String) : null;
    }

    default boolean isDouble(String p_String) {
        return get(p_String) instanceof Double;
    }

    default long getLong(String p_String) {
        return getDouble(p_String).longValue();
    }

    default long getLong(String p_String, long l) {
        return getDouble(p_String, l).longValue();
    }

    default Long getLongNullable(String p_String) {
        return containsKey(p_String) ? getLong(p_String) : null;
    }

    default boolean isLong(String p_String) {
        return get(p_String) instanceof Long;
    }

    default List<Object> getList(String p_String) {
        return (List) get(p_String);
    }

    default List<Object> getList(String p_String, List<Object> objects) {
        return (List) get(p_String, objects);
    }

    default List<Object> getListNullable(String p_String) {
        return containsKey(p_String) ? getList(p_String) : null;
    }

    default boolean isList(String p_String) {
        return get(p_String) instanceof List;
    }

    default List<String> getStringList(String p_String) {
        return (List) get(p_String);
    }

    default List<Double> getDoubleList(String p_String) {
        return (List) get(p_String);
    }

    default List<Double> getDoubleListNullable(String p_String) {
        return containsKey(p_String) ? getDoubleList(p_String) : null;
    }

    default List<Map<String, Object>> getMapList(String p_String) {
        return (List) get(p_String);
    }

    default List<Map<String, Object>> getMapListNullable(String p_String) {
        return containsKey(p_String) ? getMapList(p_String) : null;
    }

    default HashMap<String, Object> getHashMap(String p_String) {
        return (HashMap<String, Object>) get(p_String);
    }

    default HashMap<String, Double> getDoubleHashMap(String p_String) {
        return (HashMap<String, Double>) get(p_String);
    }

    DataSection createSection(String p_String);

    DataSection createSection(String p_String, Map<String, Object> map);

    FJsonSection toFJsonSection();

    MJsonSection toMJsonSection();
}
