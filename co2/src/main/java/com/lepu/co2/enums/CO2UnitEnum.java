package com.lepu.co2.enums;

/**
 * 单位
 */
public enum CO2UnitEnum {
    mmHg((byte)0),
    KPa((byte)1),
    percent((byte)2);


    byte value;

    CO2UnitEnum(byte value) {
        this.value=value;
    }

    public byte getValue() {
        return value;
    }
}
