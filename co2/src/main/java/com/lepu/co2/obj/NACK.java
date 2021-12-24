package com.lepu.co2.obj;

import com.lepu.co2.enums.NACKCEBEnum;

/**
 * NACK 错误
 */
public class NACK {
    NACKCEBEnum nackcebEnum;

    public NACK(byte[] buf){
        nackcebEnum=NACKCEBEnum.NCAKCEBEnum(buf[0]);
    }

    public NACKCEBEnum getNackcebEnum() {
        return nackcebEnum;
    }

    public void setNackcebEnum(NACKCEBEnum nackcebEnum) {
        this.nackcebEnum = nackcebEnum;
    }
}
