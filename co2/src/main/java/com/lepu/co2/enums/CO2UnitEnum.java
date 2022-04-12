package com.lepu.co2.enums;

/**
 * 单位
 */
public enum CO2UnitEnum {
    mmHg("mmHg",(byte)0),
    KPa("KPa",(byte)1),
    percent("%",(byte)2);


    String key;
    byte value;

    CO2UnitEnum(String key,byte value) {
        this.key=key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public byte getValue() {
        return value;
    }

    public static CO2UnitEnum getCO2UnitEnum(String key) {
        if (key.equals(mmHg.key)) {
            return mmHg;
        } else if (key.equals(KPa.key)) {
            return KPa;
        } else {
            return percent;
        }
    }
}
