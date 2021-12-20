package com.lepu.co2.obj;

import android.util.Log;

import com.lepu.co2.enums.PrioritizedCO2Status;
import com.lepu.co2.uitl.ByteUtils;

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
    PrioritizedCO2Status CO2Status;

    /**
     *ETCO2 x101  当DPI等于2的时候有效 ETCO2 = (DB1 * 2^7) + DB
     */
    int ETCO2;

    /**
     *  呼吸频率 当DPI等于3的时候有效 RespRate = (DB1 * 2^7) + DB2
     */
    int respirationRate;

    /**
     *  吸入气二氧化碳浓度 CO2 x10^1 当DPI等于4的时候有效 Insp CO2 = (DB1 * 2^7) + DB2
     */
    int InspiredCO2;

    /**
     *  呼吸检测标志 当DPI等于5的时候有效
     */
    boolean BreathDetectedFlag;

    /**
     *  硬件状态 仅在非零时发送。 见附录  当DPI等于7的时候有效
     */
    HardwareStatus HardwareStatus;

    byte[] buf;


    public Co2Data(byte[] buf){
        this.buf=buf;
        SYNC=buf[0];

            co2Wave= (short) ((( 128 * buf[1]) + buf[2])-1000);
          //  Log.e("co2Wave===",(co2Wave*0.01)+"");
        if (buf.length>3){
            DPI=buf[3];
            if (DPI!=0){
                switch (DPI){
                    case 1:{
                        CO2Status=PrioritizedCO2Status.getPrioritizedCO2Status(buf[8]);
                    }
                    break;
                    case 2:{
                        ETCO2=  ( ( 128 * buf[4] ) + buf[5] );
                    }
                    break;
                    case 3:{
                        respirationRate = (buf[4] * 2^7) + buf[5];
                    }
                    break;
                    case 4:{
                        InspiredCO2  = ( ( 128 * buf[4] ) + buf[5] );
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



    @Override
    public String toString() {
        return "Co2Data{" +
                "SYNC=" + SYNC +
                ", co2Wave=" + co2Wave +
                ", DPI=" + DPI +
                ", CO2Status=" + CO2Status +
                ", ETCO2=" + ETCO2 +
                ", respirationRate=" + respirationRate +
                ", InspiredCO2=" + InspiredCO2 +
                ", BreathDetectedFlag=" + BreathDetectedFlag +
                ", HardwareStatus=" + HardwareStatus +
                ", buf=" + Arrays.toString(buf) +
                '}';
    }
}
