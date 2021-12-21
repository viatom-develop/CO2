package com.lepu.co2.enums;

/**
 * 补偿气体/平衡气体 0 (room air)   room air= 0  , N2O = 1 , Helium= 2
 */
public enum BalanceGasEnum {
    AIR("Air", 0),
    N20("N20", 1),
    HE("He", 2);

    String key;
    int value;

    BalanceGasEnum(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }


    public static BalanceGasEnum getBalanceGasEnum(String key) {
        if (key.equals(AIR.key)) {
            return AIR;
        } else if (key.equals(N20.key)) {
            return N20;
        } else {
            return HE;
        }
    }
}
