package org.attrest.boottest.api;

import org.attrest.core.codetype.AttDescription;

public enum TestEnum {
    @AttDescription("X1测试")
    JQuery(0x01,"jq") ,
    @AttDescription("哈哈哈哈")
    Vue(0x02,"vue"),
    React(0x04,"react"),
    Angular2(0x08,"ang"),
    Avalon(0x10,"ava");

    private final int value;
    private final String order;

    // 构造器默认也只能是private, 从而保证构造函数只能在内部使用
    TestEnum(int value,String order) {
        this.value = value;
        this.order = order;
    }

    public int getValue() {
        return value;
    }

    public String getOrder() {
        return order;
    }
}