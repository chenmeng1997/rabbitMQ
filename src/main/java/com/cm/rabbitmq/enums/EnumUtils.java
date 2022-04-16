package com.cm.rabbitmq.enums;

import java.util.Objects;

/**
 * @author 陈萌
 * @date 2022/4/8 16:32
 */
public class EnumUtils {


    /**
     * 判断值是否存在
     *
     * @param value      值
     * @param enumsClass 枚举类Class
     * @param <E>        枚举类
     * @param <V>        Value 类型
     * @return 是否存在
     */
    public static <E extends Enum<? extends NameValueEnum<V>>, V> boolean isExistValue(V value, Class<E> enumsClass) {
        Objects.requireNonNull(enumsClass, "枚举类为空！");
        Objects.requireNonNull(value, "value为空！");
        for (Enum<? extends NameValueEnum<V>> e : enumsClass.getEnumConstants()) {
            V v = ((NameValueEnum<V>) e).getValue();
            if (Objects.equals(v, value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断名称是否存在
     *
     * @param name       名称/枚举值描述
     * @param enumsClass 枚举类Class
     * @param <E>        枚举类
     * @param <V>        Value 类型
     * @return 是否存在
     */
    public static <E extends Enum<? extends NameValueEnum<V>>, V> boolean isExistName(String name, Class<E> enumsClass) {
        Objects.requireNonNull(enumsClass, "枚举类为空！");
        Objects.requireNonNull(name, "name为空！");
        for (Enum<? extends NameValueEnum<V>> e : enumsClass.getEnumConstants()) {
            String enumName = ((NameValueEnum<V>) e).getName();
            if (Objects.equals(enumName, name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据名称获取值
     *
     * @param value      值
     * @param enumsClass 枚举类
     * @param <E>        枚举
     * @param <V>        值类型
     * @return 根据名称获取值
     */
    public static <E extends Enum<? extends NameValueEnum<V>>, V> String getNameByValue(V value, Class<E> enumsClass) {
        Objects.requireNonNull(enumsClass, "枚举类为空！");
        Objects.requireNonNull(value, "value为空！");
        for (Enum<? extends NameValueEnum<V>> e : enumsClass.getEnumConstants()) {
            NameValueEnum<V> nameValueEnum = (NameValueEnum<V>) e;
            V v = nameValueEnum.getValue();
            if (Objects.equals(v, value)) {
                return nameValueEnum.getName();
            }
        }
        return null;
    }

    /**
     * 根据名称获取值
     *
     * @param value 值
     * @param enums 枚举数组
     * @param <E>   枚举
     * @param <V>   值类型
     * @return 根据名称获取值
     */
    public static <E extends Enum<? extends NameValueEnum<V>>, V> String getNameByValue(V value, E[] enums) {
        Objects.requireNonNull(enums, "枚举数组为空！");
        Objects.requireNonNull(value, "value为空！");
        for (Enum<? extends NameValueEnum<V>> e : enums) {
            NameValueEnum<V> nameValueEnum = (NameValueEnum<V>) e;
            V v = nameValueEnum.getValue();
            if (Objects.equals(v, value)) {
                return nameValueEnum.getName();
            }
        }
        return null;
    }

    /**
     * 根据名称获取值/默认值
     *
     * @param value       值
     * @param enumClass   枚举类
     * @param defaultName 默认名称
     * @param <E>         枚举类型
     * @param <V>         值类型
     * @return 值/默认值
     */
    public static <E extends Enum<? extends NameValueEnum<V>>, V> String getNameByValueOrDefault(V value, Class<E> enumClass, String defaultName) {
        NameValueEnum<V>[] enums = (NameValueEnum<V>[]) enumClass.getEnumConstants();
        return EnumUtils.getNameByValueOrDefault(value, enums, defaultName);
    }

    /**
     * 根据名称获取值/默认值
     *
     * @param value       值
     * @param enums       枚举数组
     * @param defaultName 默认名称
     * @param <V>         值类型
     * @return 名称
     */
    public static <V> String getNameByValueOrDefault(V value, NameValueEnum<V>[] enums, String defaultName) {
        if (enums == null || enums.length == 0) {
            throw new RuntimeException("enums 为空！");
        }
        Objects.requireNonNull(value, "value为空！");
        for (NameValueEnum<V> valueEnum : enums) {
            V v = valueEnum.getValue();
            if (Objects.equals(v, value)) {
                return valueEnum.getName();
            }
        }
        return defaultName;
    }


    /**
     * 根据名称获取值
     *
     * @param name       名称
     * @param enumsClass 枚举类
     * @param <E>        枚举类型
     * @param <V>        值类型
     * @return 名称
     */
    public static <E extends Enum<? extends NameValueEnum<V>>, V> V getValueByName(String name, Class<E> enumsClass) {
        Objects.requireNonNull(enumsClass, "枚举类为空！");
        Objects.requireNonNull(name, "name 为空！");
        for (Enum<? extends NameValueEnum<V>> e : enumsClass.getEnumConstants()) {
            NameValueEnum<V> nameValueEnum = (NameValueEnum<V>) e;
            String enumName = nameValueEnum.getName();
            if (Objects.equals(enumName, name)) {
                return nameValueEnum.getValue();
            }
        }
        return null;
    }

    /**
     * 根据名称获取值
     *
     * @param name  名称
     * @param enums 枚举数组
     * @param <E>   枚举类型
     * @param <V>   值类型
     * @return 名称
     */
    public static <E extends Enum<? extends NameValueEnum<V>>, V> V getValueByName(String name, E[] enums) {
        Objects.requireNonNull(enums, "枚举数组为空！");
        Objects.requireNonNull(name, "name 为空！");
        for (Enum<? extends NameValueEnum<V>> e : enums) {
            NameValueEnum<V> nameValueEnum = (NameValueEnum<V>) e;
            String enumName = nameValueEnum.getName();
            if (Objects.equals(enumName, name)) {
                return nameValueEnum.getValue();
            }
        }
        return null;
    }

    /**
     * 根据名称获取值/默认值
     *
     * @param name         名称
     * @param enums        枚举数组
     * @param defaultValue 默认值
     * @param <V>          值类型
     * @return 名称
     */
    public static <V> V getValueByNameOrDefault(String name, NameValueEnum<V>[] enums, V defaultValue) {
        if (enums == null || enums.length == 0) {
            throw new RuntimeException("enums 为空！");
        }
        Objects.requireNonNull(name, "name 为空！");
        for (NameValueEnum<V> valueEnum : enums) {
            String enumName = valueEnum.getName();
            if (Objects.equals(enumName, name)) {
                return valueEnum.getValue();
            }
        }
        return defaultValue;
    }

    /**
     * 根据名称获取值/默认值
     *
     * @param name         名称
     * @param enumsClass   枚举数类
     * @param defaultValue 默认值
     * @param <V>          值类型
     * @return 名称
     */
    public static <E extends Enum<? extends NameValueEnum<V>>, V> V getValueByNameOrDefault(String name, Class<E> enumsClass, V defaultValue) {
        NameValueEnum<V>[] enums = (NameValueEnum<V>[]) enumsClass.getEnumConstants();
        return EnumUtils.getValueByNameOrDefault(name, enums, defaultValue);
    }

    /**
     * 根据名称获取枚举
     *
     * @param name       名称
     * @param enumsClass 枚举类
     * @param <E>        枚举类型
     * @param <V>        值
     * @return 枚举
     */
    public static <E extends Enum<? extends NameValueEnum<V>>, V> E getEnumByName(String name, Class<E> enumsClass) {
        return EnumUtils.getEnumByNameOrDefault(name, enumsClass, null);
    }

    /**
     * 根据名称获取枚举/默认枚举
     *
     * @param name        名称
     * @param enumsClass  枚举类
     * @param <E>         枚举类型
     * @param <V>         值
     * @param defaultEnum 默认值
     * @return 枚举
     */
    public static <E extends Enum<? extends NameValueEnum<V>>, V> E getEnumByNameOrDefault(String name, Class<E> enumsClass, E defaultEnum) {
        Objects.requireNonNull(enumsClass, "枚举类为空！");
        Objects.requireNonNull(name, "name 为空！");
        for (E e : enumsClass.getEnumConstants()) {
            NameValueEnum<V> nameValueEnum = (NameValueEnum<V>) e;
            String enumName = nameValueEnum.getName();
            if (Objects.equals(enumName, name)) {
                return e;
            }
        }
        return defaultEnum;
    }

    /**
     * 根据值获取枚举
     *
     * @param value      值
     * @param enumsClass 枚举类
     * @param <E>        枚举类型
     * @param <V>        值类型
     * @return 枚举
     */
    public static <E extends Enum<? extends NameValueEnum<V>>, V> E getEnumByValue(V value, Class<E> enumsClass) {
        return EnumUtils.getEnumByValueOrDefault(value, enumsClass, null);
    }

    /**
     * 根据值获取枚举/默认枚举
     *
     * @param value       值
     * @param enumsClass  枚举类
     * @param defaultEnum 返回默认值
     * @param <E>         枚举类型
     * @param <V>         值类型
     * @return 枚举
     */
    public static <E extends Enum<? extends NameValueEnum<V>>, V> E getEnumByValueOrDefault(V value, Class<E> enumsClass, E defaultEnum) {
        Objects.requireNonNull(enumsClass, "枚举类为空！");
        Objects.requireNonNull(value, "value 为空！");
        for (E e : enumsClass.getEnumConstants()) {
            NameValueEnum<V> nameValueEnum = (NameValueEnum<V>) e;
            V v = nameValueEnum.getValue();
            if (Objects.equals(v, value)) {
                return e;
            }
        }
        return defaultEnum;
    }


    public static void main(String[] args) {

        /*
        boolean valueExist = EnumUtils.isExistValue((short) 0, SexEnum.class);
        boolean valueExist1 = EnumUtils.isExistValue((short) 1, SexEnum.class);

        boolean existName = EnumUtils.isExistName("男", SexEnum.class);
        boolean existName1 = EnumUtils.isExistName("男1", SexEnum.class);

        String nameByValue = EnumUtils.getNameByValue((short) 0, SexEnum.class);
        String nameByValue1 = EnumUtils.getNameByValue((short) 0, SexEnum.values());

        String nameByValueOrDefault = EnumUtils.getNameByValueOrDefault((short) 1, SexEnum.class, "wocao");
        String nameByValueOrDefault1 = EnumUtils.getNameByValueOrDefault((short) 3, SexEnum.values(), "wocao");

        Short valueByName = EnumUtils.getValueByName("男", SexEnum.class);
        Short valueByName1 = EnumUtils.getValueByName("男1", SexEnum.values());

        Short valueByNameOrDefault = EnumUtils.getValueByNameOrDefault("男", SexEnum.class, (short) 1);
        Short valueByNameOrDefault1 = EnumUtils.getValueByNameOrDefault("男1", SexEnum.values(), (short) 1);

        SexEnum sexEnum = EnumUtils.getEnumByValueOrDefault((short) 1, SexEnum.class, SexEnum.MALE);
        SexEnum sexEnum1 = EnumUtils.getEnumByValueOrDefault((short) 0, SexEnum.class, SexEnum.MALE);
        */

    }


}
