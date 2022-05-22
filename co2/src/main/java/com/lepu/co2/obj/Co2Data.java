package com.lepu.co2.obj;

import android.util.Log;

import com.lepu.co2.enums.PrioritizedCO2Status;

import java.util.Arrays;

/**
 * 二氧化碳数据
 */
public class Co2Data {
    /**
     * 同步计数器随着每个数据包的发送而增加。
     * 计数器从 0 开始，当它达到 127 时返回到 0。这个字节
     * 可用于检测丢失的数据包。
     */
    int SYNC;
    /**
     * CO2 波形 x1001
     */
    short co2Wave;
    /**
     * 数据参数索引（仅在必要时发送。有效的 DPI 是
     * 定义见下表）
     */
    int DPI;

    /**
     * 二氧化碳状态 当DPI等于1的时候有效
     */
 //   PrioritizedCO2Status CO2Status;

    /**
     * ETCO2 x101  当DPI等于2的时候有效 ETCO2 = (DB1 * 2^7) + DB
     */
    int ETCO2;

    /**
     * 呼吸频率 当DPI等于3的时候有效 RespRate = (DB1 * 2^7) + DB2
     */
    int respRate;

    /**
     * 吸入气二氧化碳浓度 CO2 x10^1 当DPI等于4的时候有效 Insp CO2 = (DB1 * 2^7) + DB2
     */
    int InspiredCO2;

    /**
     * 呼吸检测标志 当DPI等于5的时候有效
     */
    boolean BreathDetectedFlag;

    /**
     * 硬件状态 仅在非零时发送。 见附录  当DPI等于7的时候有效
     */
    HardwareStatus HardwareStatus;
    /**
     * 原始数据用于保存
     */
    byte[] originalData;
    /**
     *二氧化碳状态 当DPI等于1的时候有效
     */
    CO2Status co2Status;

    public Co2Data() {
    }

    public Co2Data(byte[] originalData, byte[] buf) {
        this.originalData = originalData;
        SYNC = buf[0];

        co2Wave = (short) (((buf[2] & 0xFF) | (short) (buf[1] << 7))-1000);

        if (buf.length > 3) {
            DPI=buf[3];
            if (DPI!=0){
                switch (DPI){
                    case 1:{
                        co2Status=new CO2Status(buf);
             //           Log.e("LZD",co2Status.toString());
                    }
                    break;
                    case 2:{
                        ETCO2= ((buf[5] & 0xFF) | (short) (buf[4] << 7));
                        if (ETCO2<150){
                            ETCO2=0;
                        }
                    }
                    break;
                    case 3:{
                        respRate = ((buf[5] & 0xFF) | (short) (buf[4] << 7));
                        if(respRate>150){
                            respRate=0;
                        }
                    }
                    break;
                    case 4:{
                        InspiredCO2  = ((buf[5] & 0xFF) | (short) (buf[4] << 7));
                    }
                    break;
                    case 5:{
                        BreathDetectedFlag = true;
                    }
                    break;
                    case 7:{
                        HardwareStatus=new HardwareStatus(buf[4], buf[5]);
                    }
                    break;

                }

            }

        }


    }

    public int getRespRate() {
        return respRate;
    }

    public void setRespRate(int respRate) {
        this.respRate = respRate;
    }

    public int getSYNC() {
        return SYNC;
    }

    public void setSYNC(int SYNC) {
        this.SYNC = SYNC;
    }

    public short getCo2Wave() {
        return co2Wave;
    }

    public void setCo2Wave(short co2Wave) {
        this.co2Wave = co2Wave;
    }

    public int getDPI() {
        return DPI;
    }

    public void setDPI(int DPI) {
        this.DPI = DPI;
    }

    public CO2Status getCo2Status() {
        return co2Status;
    }

    public void setCo2Status(CO2Status co2Status) {
        this.co2Status = co2Status;
    }

    public int getETCO2() {
        return ETCO2;
    }

    public void setETCO2(int ETCO2) {
        this.ETCO2 = ETCO2;
    }

    public int getInspiredCO2() {
        return InspiredCO2;
    }

    public void setInspiredCO2(int inspiredCO2) {
        InspiredCO2 = inspiredCO2;
    }

    public boolean isBreathDetectedFlag() {
        return BreathDetectedFlag;
    }

    public void setBreathDetectedFlag(boolean breathDetectedFlag) {
        BreathDetectedFlag = breathDetectedFlag;
    }

    public com.lepu.co2.obj.HardwareStatus getHardwareStatus() {
        return HardwareStatus;
    }

    public void setHardwareStatus(com.lepu.co2.obj.HardwareStatus hardwareStatus) {
        HardwareStatus = hardwareStatus;
    }

    public byte[] getOriginalData() {
        return originalData;
    }

    public void setOriginalData(byte[] originalData) {
        this.originalData = originalData;
    }

    public static void main(String[] args) {
        int a = 2 ^ 7;
        System.out.println(a + "");
    }
}
