package com.lepu.co2.enums;

/**
 * 计算周期
 */
public enum TimePeriodEnum {
    TimePeriod1B("1b", 1),
    TimePeriod10S("10S", 10),
    TimePeriod20S("20S", 20);

    String str;
    int value;

    TimePeriodEnum(String str, int value) {
        this.str = str;
        this.value = value;
    }

    public String getStr() {
        return str;
    }


    public int getValue() {
        return value;
    }

}
