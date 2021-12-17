package com.lepu.co2.constant;

import com.lepu.co2.obj.SerialMsg;
import com.lepu.co2.uitl.ChecksumUtil;


/**
 * 串口命令合成
 */
public class SerialCmd {


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
     * 8.3 设置气压
     * Default: 760 mmHg.
     * Resolution: 1 mmHg (400-850 mmHg)
     * Conversion: Barometric Pressure = (128 * DB1) + DB2
     * DB1 = ( Barometric Pressure / 128 ) & 7Fh
     * DB2 = ( Barometric Pressure) & 7Fh
     */
    public static byte[] cmdSetBarometricPressure(int barometricPressure) {
        byte[] data = new byte[0];
        data[0]=1;
        data[1]= (byte) (( barometricPressure / 128 ) & 0x7F);
        data[2]= (byte) (( barometricPressure) & 0x7F);
        SerialMsg msg = new SerialMsg(Co2Constant.TYPE_Get_Set_Sensor_Settings, data);
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
        byte a = (byte) 0xCA;
        System.out.println("");
    }

}
