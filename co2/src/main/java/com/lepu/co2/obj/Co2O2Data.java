package com.lepu.co2.obj;

import com.lepu.co2.uitl.ByteUtils;

/**
 * co2 o2 波形数据
 */
public class Co2O2Data {
    /**
     * 同步计数器随着每个数据包的发送而增加。
     * 计数器从 0 开始，当它达到 127 时返回到 0。这个字节
     * 可用于检测丢失的数据包。
     */
    int SYNC;
    /**
     * CO2 波形 x1001
     */
    short Co2;
    /**
     * O2 波形 x1001
     */
    short O2;

    /**
     * 数据参数索引（仅在必要时发送。有效的 DPI 是
     * 定义见下表）
     */
    int DPI;
    /**
     * 当DIP为1的时候 这个值有效
     */
    int CO2Status;

    /**
     * ETCO2 x10¹
     * 当DIP为2的时候 这个值有效  ETCO2 x101 ETCO2 = (DPB1 * 2^7) + DPB2
     */
    int ETCO2;

    /**
     * 当DIP为3的时候 这个值有效 RespRate = (DPB1 * 2^7) + DPB2
     */
    int RespRate;

    /**
     * Insp CO2¹
     * 当DIP为4的时候 这个值有效 Insp CO2 = (DPB1 * 2^7) + DPB2
     */
    int InspCO2;

    /**
     * 当DIP为5的时候 这个值有效 发送此 DPI 时已检测到呼吸。
     * 呼吸检测标志
     */
    int BreathDetectedFlag;

    /**
     * 当DIP为6的时候 这个值有效
     */
    int O2Status;

    /**
     * 当DIP为7的时候 这个值有效  Only sent when nonzero. See Appendix A
     */
    int HardwareStatus;

    /**
     * 当DIP为10的时候 这个值有效  Fractional Inspired O2 = (DPB1 * 2^7) + DPB2
     */
    int FiO2;

    /**
     * 当DIP为11的时候 这个值有效  Fractional Expired O2 = (DPB1 * 2^7) + DPB2
     */
    int FeO2;


    public Co2O2Data(byte buf[]) {
        SYNC = buf[0];
        Co2 = (short) ByteUtils.bytes2Short(buf[1], buf[2]);
        O2 = (short) ByteUtils.bytes2Short(buf[3], buf[4]);
        if (buf.length > 3) {
            DPI = buf[3];
            if (DPI > 0) {
                switch (DPI) {
                    case 1: {
                        //
                        CO2Status = buf[10];
                    }
                    break;
                    case 2: {
                        // ETCO2 x101 ETCO2 = (DPB1 * 2^7) + DPB2
                        ETCO2 = (buf[6] * 2 ^ 7) + buf[7];
                    }
                    break;
                    case 3: {
                        //  RespRate = (DPB1 * 2^7) + DPB2
                        RespRate = (buf[6] * 2 ^ 7) + buf[7];
                    }
                    break;
                    case 4: {
                        //   Insp CO2 = (DPB1 * 2^7) + DPB2
                        InspCO2 = (buf[6] * 2 ^ 7) + buf[7];
                    }
                    break;
                    case 5: {
                        //           BreathDetectedFlag=??
                    }
                    break;
                    case 6: {
                        //          O2Status??
                    }
                    break;
                    case 7: {
                        //         HardwareStatus??
                    }
                    break;
                    case 10: {
                        // ractional Inspired O2 = (DPB1 * 2^7) + DPB2
                        FiO2= (buf[6] * 2 ^ 7) + buf[7];
                    }
                    break;
                    case 11: {
                        //  Fractional Expired O2 = (DPB1 * 2^7) + DPB2
                        FeO2= (buf[6] * 2 ^ 7) + buf[7];
                    }
                    break;


                }
            }


        }
        //6


    }


    public int getSYNC() {
        return SYNC;
    }

    public void setSYNC(int SYNC) {
        this.SYNC = SYNC;
    }

    public short getCo2() {
        return Co2;
    }

    public void setCo2(short co2) {
        Co2 = co2;
    }

    public short getO2() {
        return O2;
    }

    public void setO2(short o2) {
        O2 = o2;
    }

    public int getDPI() {
        return DPI;
    }

    public void setDPI(int DPI) {
        this.DPI = DPI;
    }

    public int getCO2Status() {
        return CO2Status;
    }

    public void setCO2Status(int CO2Status) {
        this.CO2Status = CO2Status;
    }

    public int getETCO2() {
        return ETCO2;
    }

    public void setETCO2(int ETCO2) {
        this.ETCO2 = ETCO2;
    }

    public int getRespRate() {
        return RespRate;
    }

    public void setRespRate(int respRate) {
        RespRate = respRate;
    }

    public int getInspCO2() {
        return InspCO2;
    }

    public void setInspCO2(int inspCO2) {
        InspCO2 = inspCO2;
    }

    public int getBreathDetectedFlag() {
        return BreathDetectedFlag;
    }

    public void setBreathDetectedFlag(int breathDetectedFlag) {
        BreathDetectedFlag = breathDetectedFlag;
    }

    public int getO2Status() {
        return O2Status;
    }

    public void setO2Status(int o2Status) {
        O2Status = o2Status;
    }

    public int getHardwareStatus() {
        return HardwareStatus;
    }

    public void setHardwareStatus(int hardwareStatus) {
        HardwareStatus = hardwareStatus;
    }

    public int getFiO2() {
        return FiO2;
    }

    public void setFiO2(int fiO2) {
        FiO2 = fiO2;
    }

    public int getFeO2() {
        return FeO2;
    }

    public void setFeO2(int feO2) {
        FeO2 = feO2;
    }
}
