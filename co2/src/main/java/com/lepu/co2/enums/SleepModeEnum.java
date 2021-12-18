package com.lepu.co2.enums;

/**
 * 睡眠模式
 * * 0 正常工作模式
 * * 1 模式 1 – 关闭电源（维护加热器）
 * * 2 模式 2 - 最大节能
 */
public enum SleepModeEnum {
    NormalOperatingMode((byte) 0),//正常工作模式
    TurnOffSource((byte)1),//模式 1 – 关闭电源（维护加热器）
    MaximumPowerSavings((byte)2);//模式 2 - 最大节能

    byte value;

    SleepModeEnum(byte value) {
        this.value=value;
    }

    public byte getValue() {
        return value;
    }


}
