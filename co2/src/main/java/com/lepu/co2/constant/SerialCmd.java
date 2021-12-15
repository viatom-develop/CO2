package com.lepu.co2.constant;

import android.util.Log;

import com.lepu.co2.uitl.ChecksumUtil;


/**
 * 串口命令合成
 */
public class SerialCmd {

    static byte index = 0;

    /***************************************************参数板整体业务 start*************************************************************/


    /**
     *获取版本号
     */
    public static byte[] cmdGetSoftwareRevision() {
       /* SerialContent content = new SerialContent(SerialContent.TOKEN_PARAM, SerialContent.TYPE_RESET, null);
        SerialMsg msg = new SerialMsg(index, SerialMsg.TYPE_CMD, content);*/
        byte[] data = new byte[4];

        data[0] = Co2Constant.TYPE_GET_SOFTWARE_REVISION;
        data[1] = (byte) (data.length - 2);//
        data[2] = (byte) 0;
  //        data[3] = (byte) ChecksumUtil.sumCheck(data,3);
     //   Log.e("sck",String.format("%x",data[3] ));
        data[3] =   ChecksumUtil.AddChecksum(3, data);
        Log.e("sck",String.format("%x",data[3] ));
        index++;
        return data;
    }


    /**
     *停止连续模式(命令C9h)
     */
    public static byte[] cmdStopContinuousMode() {
       /* SerialContent content = new SerialContent(SerialContent.TOKEN_PARAM, SerialContent.TYPE_RESET, null);
        SerialMsg msg = new SerialMsg(index, SerialMsg.TYPE_CMD, content);*/
        byte[] data = new byte[3];

        data[0] = Co2Constant.TYPE_Stop_Continuous_Mode;
        data[1] = (byte) (data.length - 2);//
        data[2] =   ChecksumUtil.AddChecksum(2, data);
        Log.e("sck",String.format("%x",data[2] ));
        index++;
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
