package com.lepu.co2.constant;

import com.lepu.co2.enums.BalanceGasEnum;
import com.lepu.co2.enums.CO2UnitEnum;
import com.lepu.co2.enums.SleepModeEnum;
import com.lepu.co2.enums.TimePeriodEnum;
import com.lepu.co2.obj.SerialMsg;
import com.lepu.co2.uitl.ByteUtils;
import com.lepu.co2.uitl.ChecksumUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * 串口命令合成
 */
public class Co2Cmd {


    /***************************************************参数板整体业务 start*************************************************************/

    /**
     * 8.1 CO2 波形/数据模式（命令 80h）
     */
    public static byte[] cmdWaveformDataMode() {
        byte[] data = new byte[1];
        data[0] = 0;
        SerialMsg msg = new SerialMsg(Co2Constant.TYPE_Waveform_Data_Mode, data);
        return msg.toBytes();
    }

    /**
     * 8.2零校准命令 这个命令用于启动Capnostat 0。零用于校正气道适配器类型的差异。
     */
    public static byte[] cmdCapnostatZeroCommand() {
        byte[] data = new byte[0];
        SerialMsg msg = new SerialMsg(Co2Constant.TYPE_Capnostat_Zero_Command, data);
        return msg.toBytes();
    }

    /**
     * 8.3 1设置气压
     * Default: 760 mmHg.
     * Resolution: 1 mmHg (400-850 mmHg)
     * Conversion: Barometric Pressure = (128 * DB1) + DB2
     * DB1 = ( Barometric Pressure / 128 ) & 7Fh
     * DB2 = ( Barometric Pressure) & 7Fh
     */
    public static byte[] cmdSetBarometricPressure(short barometricPressure) {
        byte[] data = new byte[3];
        data[0]=1;
        byte[] value= ByteUtils.short2byte(barometricPressure);
      /*  data[1]= value[1];
        data[2]= value[0];*/
        data[1]= (byte) ((barometricPressure >> 7) & 0x7f);
        data[2]= (byte) (barometricPressure& 0x7f);

        SerialMsg msg = new SerialMsg(Co2Constant.TYPE_Get_Set_Sensor_Settings, data);
        return msg.toBytes();
    }



    /**
     * 8.3 4设置气体温度 工作温度  单位
     */
    public static byte[] cmdSetGasTemperature(short gasTemperature) {
        byte[] data = new byte[3];
        data[0]=4;
        data[1]= (byte) ((gasTemperature >> 7) & 0x7f);
        data[2]= (byte) (gasTemperature& 0x7f);
        SerialMsg msg = new SerialMsg(Co2Constant.TYPE_Get_Set_Sensor_Settings, data);
        return msg.toBytes();
    }


    /**
     * 8.3 5设置计算周期
     */
    public static byte[] cmdSetTimePeriod(TimePeriodEnum timePeriodEnum) {
        byte[] data = new byte[2];
        data[0]=5;
        data[1]= (byte) timePeriodEnum.getValue();
        SerialMsg msg = new SerialMsg(Co2Constant.TYPE_Get_Set_Sensor_Settings, data);
        return msg.toBytes();
    }

    /**
     * 8.3 6设置呼吸窒息时间
     * 默认值:20秒。
     * 分辨率:1秒(10到60秒)
     */
    public static byte[] cmdSetApneaDelayTime(int timeOut) {
        byte[] data = new byte[2];
        data[0]=6;
        data[1]= (byte)timeOut;
        SerialMsg msg = new SerialMsg(Co2Constant.TYPE_Get_Set_Sensor_Settings, data);
        return msg.toBytes();
    }
    /**
     * 8.3 7设置二氧化碳单位
     */
    public static byte[] cmdSetCo2Unit(CO2UnitEnum  co2UnitEnum) {
        byte[] data = new byte[2];
        data[0]=7;
        data[1]= co2UnitEnum.getValue();

        SerialMsg msg = new SerialMsg(Co2Constant.TYPE_Get_Set_Sensor_Settings, data);
        return msg.toBytes();
    }


    /**
     * 8.3 11设置补偿气体
     *
     * @param O2Compensation  氧气补偿 1 % ( 0 – 100 % ) 默认16
     * @param balanceGasEnum  平衡气   Default: 0 (room air)   room air= 0  , N2O = 1 , Helium= 2
     * @param anestheticAgent 麻醉剂 默认 0  范围 0.1 % ( 0.0 – 20.0 % )
     * @return
     */
    public static byte[] cmdSetGasCompensations(int O2Compensation, BalanceGasEnum balanceGasEnum, double anestheticAgent) {
        byte[] data = new byte[5];
        data[0] = 11;
        data[1] = (byte) O2Compensation;
        data[2] = (byte) balanceGasEnum.getValue();
        short ag = (short) (anestheticAgent * 10);
        data[3] = (byte) ((ag >> 7) & 0x7f);
        ;
        data[4] = (byte) (ag & 0x7f);
        SerialMsg msg = new SerialMsg(Co2Constant.TYPE_Get_Set_Sensor_Settings, data);
        return msg.toBytes();
    }

/*
    public static void main(String[] args) {
        cmdSetGasCompensations(40, BalanceGasEnum.N20, 3.5);
    }
*/


    /**
     * 8.4 CO2 / O2 Waveform Mode (Command 90h)
     */
    public static byte[] cmdWaveformDataModeC2CO2() {
        byte[] data = new byte[1];
        data[0] = 0;
        SerialMsg msg = new SerialMsg(Co2Constant.TYPE_CO2_O2_Waveform_Mode, data);
        return msg.toBytes();
    }

    /**
     * 停止连续模式(命令C9h)
     */
    public static byte[] cmdStopContinuousMode() {
        byte[] data = new byte[0];
        SerialMsg msg = new SerialMsg(Co2Constant.TYPE_Stop_Continuous_Mode, data);
        return msg.toBytes();
    }


    /**
     * 该命令用于强制系统清除“检测不到呼吸”标志
     */
    public static byte[] cmdResetApneaDelay() {
        byte[] data = new byte[0];
        SerialMsg msg = new SerialMsg(Co2Constant.TYPE_Reset_No_Breaths_Detected_Flag, data);
        return msg.toBytes();
    }


    /**
     * 获取版本号
     */
    public static byte[] cmdGetSoftwareRevision() {
        byte[] data = new byte[4];
        data[0] = Co2Constant.TYPE_GET_SOFTWARE_REVISION;
        data[1] = (byte) (data.length - 2);//
        data[2] = (byte) 0;
        data[3] = ChecksumUtil.AddChecksum(3, data);

        return data;
    }

    /**
     *睡眠模式
     * 0 正常工作模式
     * 1 模式 1 – 关闭电源（维护加热器）
     * 2 模式 2 - 最大节能
     */
    public static byte[] cmdSleepMode(SleepModeEnum sleepMode) {
        byte[] data = new byte[2];
        data[0]=(byte)8;
        data[1]= (byte) sleepMode.getValue();
        SerialMsg msg = new SerialMsg(Co2Constant.TYPE_Get_Set_Sensor_Settings, data);
        return msg.toBytes();
    }
    /**
     *测试用例
     */
    public static byte[] cmdTest() {
       // 84h - 02h - 05h – 75h
      /*  byte[] data = new byte[4];

        data[0]= (byte) 0x84;
        data[1]= 0x02;
        data[2]= 0x05;
        data[3]= 0x75;*/
        byte[] data = new byte[1];
        data[0]=(byte) 5;
        SerialMsg msg = new SerialMsg(Co2Constant.TYPE_Get_Set_Sensor_Settings, data);
        return msg.toBytes();
    }

    /**
     * Byte转Bit
     */
    public static String byteToBit(byte b) {
        return "" + (byte) ((b >> 7) & 0x1) +
                (byte) ((b >> 6) & 0x1) +
                (byte) ((b >> 5) & 0x1) +
                (byte) ((b >> 4) & 0x1) +
                (byte) ((b >> 3) & 0x1) +
                (byte) ((b >> 2) & 0x1) +
                (byte) ((b >> 1) & 0x1) +
                (byte) ((b >> 0) & 0x1);
    }

    /**
     * short转Bit
     */
    public static String shortToBit(short b) {
        return ""
                + (short) ((b >> 15) & 0x1)
                + (short) ((b >> 14) & 0x1)
                + (short) ((b >> 13) & 0x1)
                + (short) ((b >> 12) & 0x1)
                + (short) ((b >> 11) & 0x1)
                + (short) ((b >> 10) & 0x1)
                + (short) ((b >> 9) & 0x1)
                + (short) ((b >> 8) & 0x1)
                + (short) ((b >> 7) & 0x1) +
                (short) ((b >> 6) & 0x1) +
                (short) ((b >> 5) & 0x1) +
                (short) ((b >> 4) & 0x1) +
                (short) ((b >> 3) & 0x1) +
                (short) ((b >> 2) & 0x1) +
                (short) ((b >> 1) & 0x1) +
                (short) ((b >> 0) & 0x1);
    }


    public static void main(String[] args) {

   /*  short[] a=new short[]{10};
     short[] b=new short[0];
     System.arraycopy( );
     a[0]=20;
     System.out.println(b[0]+"");*/
     /*   float f=0.1f;
        while (f<10){
          //  f+=0.1;
            f=new BigDecimal(Float.toString(f)).add(new BigDecimal(Float.toString(0.1f))).floatValue();

            System.out.println(f+"");
        }
*/

        List<String> mList=new ArrayList<>();
        mList.add("1");
        mList.add("2");
        mList.add("3");
        mList.add(0,"4");
        List<String> b=new ArrayList<>();
        b.add("5");
        b.add("6");
        mList.addAll(0,b);
        for (int i=0;i<mList.size();i++){
            System.out.println(mList.get(i));
        } 

    }

}
