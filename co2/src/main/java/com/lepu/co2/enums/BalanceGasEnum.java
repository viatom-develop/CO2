package com.lepu.co2.enums;

/**
 * 补偿气体/平衡气体 0 (room air)   room air= 0  , N2O = 1 , Helium= 2
 */
public enum BalanceGasEnum {
    AIR("Air", 0),
    N20("N20", 1),
    HE("He", 2);

    String str;
    int value;

    BalanceGasEnum(String str, int value) {
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