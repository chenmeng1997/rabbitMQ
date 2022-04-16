package com.cm.rabbitmq.enums;

/**
 * @author 陈萌
 * @date 2022/4/8 16:22
 */
public interface NameValueEnum<T> {

    String getName();

    T getValue();

}
