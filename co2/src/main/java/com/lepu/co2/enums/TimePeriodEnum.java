package com.lepu.co2.enums;

/**
 * 计算周期
 */
public enum TimePeriodEnum {
    TimePeriod1B("1b", 1),
    TimePeriod10S("10S", 10),
    TimePeriod20S("20S", 20);

    String key;
    int value;

    TimePeriodEnum(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }


    public int getValue() {
        return value;
    }

    public static TimePeriodEnum getTimePeriodEnum(String key){
        if (key.equals(TimePeriod1B.key)){
            return  TimePeriod1B;
        }else if (key.equals(TimePeriod10S.key)){
            return TimePeriod10S;
        }else {
            return TimePeriod20S;
        }
    }

}
