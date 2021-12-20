package com.lepu.co2.enums;

/**
 * 单位
 */
public enum CO2UnitEnum {
    mmHg("mmHg",(byte)0),
    KPa("KPa",(byte)1),
    percent("percent",(byte)2);


    String key;
    byte value;

    CO2UnitEnum(String key,byte value) {
        this.key=key;
        this.value=value;
    }

    public byte getValue() {
        return value;
    }
}
