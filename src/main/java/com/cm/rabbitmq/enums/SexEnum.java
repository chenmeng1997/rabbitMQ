package com.cm.rabbitmq.enums;

/**
 * @author 陈萌
 * @date 2022/4/8 16:24
 */
public enum SexEnum implements NameValueEnum<Short> {

    MALE((short) 1, "男"),
    FEMALE((short) 2, "女"),
    ;

    SexEnum(Short value, String name) {
        this.name = name;
        this.value = value;
    }

    private Short value;
    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Short getValue() {
        return this.value;
    }

}
