package com.lepu.co2.manager;

import android.content.Context;
import android.os.AsyncTask;
import android.serialport.SerialPort;
import android.util.Log;

import androidx.annotation.NonNull;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lepu.co2.constant.Co2Constant;
import com.lepu.co2.constant.EventMsgConst;
import com.lepu.co2.listener.CmdReplyListener;
import com.lepu.co2.listener.SerialConnectListener;
import com.lepu.co2.obj.Co2Data;
import com.lepu.co2.obj.Co2O2Data;
import com.lepu.co2.obj.SerialMsg;
import com.lepu.co2.uitl.ByteUtils;
import com.lepu.co2.uitl.ChecksumUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Co2Manager {
    //定时获取串口数据任务
    ScheduledThreadPoolExecutor mScheduledThreadPoolExecutor;
    SerialPort mSerialPort;
    InputStream mInputStream;
    OutputStream mOutputStream;
    //单例
    private static Co2Manager instance = null;
    //
    Context mContext;
    //请求命令回调
    CmdReplyListener mCmdReplyListener;

    public static Co2Manager getInstance() {
        if (instance == null) {
            instance = new Co2Manager();
        }
        return instance;
    }

    /**
     * 串口初始化
     * 数据以19200波特率传输，字节大小为8个数据位，1个停止位，无奇偶校验
     * @param devicePath 串口名 /dev/ttyS1
     */
    public void init(Context context, String devicePath, SerialConnectListener serialConnentListener) {
        AsyncTask.execute(() -> {
            try {
                //     Log.d("SerialPortManager", "初始化串口");
                //打开串口
                SerialPort serialPort = SerialPort //
                        .newBuilder(devicePath, 19200) // 串口地址地址，波特率
                        .parity(0) // 校验位；0:无校验位(NONE，默认)；1:奇校验位(ODD);2:偶校验位(EVEN)
                        .dataBits(8) // 数据位,默认8；可选值为5~8
                        .stopBits(1) // 停止位，默认1；1:1位停止位；2:2位停止位
                        .build();
                mSerialPort = serialPort;
                mInputStream = mSerialPort.getInputStream();
                mOutputStream = mSerialPort.getOutputStream();
                serialConnentListener.onSuccess();

            } catch (Exception e) {
                e.printStackTrace();
                serialConnentListener.onFail();
            }
        });
        //开始定时获取心电图数据
        startGetEcgData();
        //设置任务监听
     /*   mSerialPortDataTask = SerialPortDataTask.getInstance();
        mSerialPortDataTask.setOnTaskListener(onTaskListener);*/
        mContext = context;
    }


    /**
     * 开始读取串口数据
     */
    public void startGetEcgData() {

        if (mScheduledThreadPoolExecutor == null) {
            mScheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
            mScheduledThreadPoolExecutor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
            //        Log.e("buffer",  "读取数据");
                    try {
                        if (Co2Constant.IS_TEST_DATA) {//测试模式
                            //测试数据
                            //    sendTestEcgData();
                            //昨天采集的数据
                      //      sendTestEcgDataFile();
                     //       Log.e("buffer",  "测试数据");
                        } else {//正式数据
                            if (mInputStream == null) return;
                            byte[] buffer = ByteUtils.readStream(mInputStream);


                   //         Log.e("buffer",  "接收完成");
                            //处理数据
                            dataProcess(buffer);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, 10, 100, TimeUnit.MILLISECONDS);//每100毫秒获取一次数据
        }
    }

    /**
     * 向串口写入数据
     */
    public void serialSendData(byte[] bytes, CmdReplyListener cmdReplyListener) {
        try {
        //    Log.e("tag","写入数据");
            mCmdReplyListener = cmdReplyListener;
            writeBytes (bytes);

        } catch (Exception ex) {
            ex.printStackTrace();
            if (mCmdReplyListener != null) {
                mCmdReplyListener.onFail(bytes[0]);
            }
        }
    }
    private void writeBytes (byte[] bytes) throws IOException {
        OutputStream mOutputStream;
        mOutputStream = mSerialPort.getOutputStream();
        for (int i = 0; i < bytes.length; i++) {
            mOutputStream.write(bytes[i]);
        }
        mOutputStream.flush();
    }



    byte[] surplusData;//用于记录任务剩余的数据 放入下一个任务继续遍历
    int taskindex = 0;
    long time = 0;
    public void dataProcess(byte[] dataArr) {
        time = System.currentTimeMillis();
        //    Log.e("接收到数据时间", time + "");
        byte[] data;
        //如果有剩余的数据，需要把剩余的剩余的数据重新处理
        if (surplusData != null) {
            data = new byte[surplusData.length + dataArr.length];
            System.arraycopy(surplusData, 0, data, 0, surplusData.length);
            System.arraycopy(dataArr, 0, data, surplusData.length, dataArr.length);
            surplusData = null;
        } else {
            data = dataArr;
        }
        //用于记录一段完整的报文
        byte[] completeData = null;
        //遍历数据
        for (int i = 0; i < data.length; i++) {
            //第2个是长度 尾随的长度
            if (i + 1 >= data.length) {
                //把最后的数据 放在下一个任务中
                surplusData = new byte[data.length - i];
                System.arraycopy(data, i, surplusData, 0, data.length - i);
                break;
            }
            //判断开头
            if (isSerialCmd(data[i])) {
                completeData = new byte[(0x00ff & data[i + 1])+2];
                if (i + completeData.length > data.length) {
                    //把最后的数据 放在下一个任务中
                    surplusData = new byte[data.length - i];
                    System.arraycopy(data, i, surplusData, 0, data.length - i);
                    break;
                } else {
                    taskindex++;
                    System.arraycopy(data, i, completeData, 0, completeData.length);
                    //校验数据 校验和
                    if (ChecksumUtil.AddChecksum(completeData.length - 1, completeData) == completeData[completeData.length - 1]) {
                        //越过已处理数据
                        i = i + completeData.length - 1;
                        //分发数据
                        distributeMsg(completeData);

                    } else {
             //           Log.d("taskindex", taskindex + "");
                    }
                }
            }

        }
        time = System.currentTimeMillis() - time;
        //  Log.e("接收到处理完数据时间", time + "----"+taskindex);

    }


    public boolean isSerialCmd(byte buf) {
        switch (buf) {
            case Co2Constant.TYPE_Waveform_Data_Mode:
            case Co2Constant.TYPE_Capnostat_Zero_Command:
            case Co2Constant.TYPE_Get_Set_Sensor_Settings:
            case Co2Constant.TYPE_CO2_O2_Waveform_Mode:
            case Co2Constant.TYPE_NACK_Error:
            case Co2Constant.TYPE_Stop_Continuous_Mode:
            case Co2Constant.TYPE_GET_SOFTWARE_REVISION:
            case Co2Constant.TYPE_Sensor_Capabilities:
            case Co2Constant.TYPE_Reset_No_Breaths_Detected_Flag:
            case Co2Constant.TYPE_Reset_Capnostat:
                return true;
        }
        return false;
    }

    public void distributeMsg(@NonNull byte[] buf) {
        SerialMsg serialMsg= new SerialMsg(buf);


        switch (serialMsg.getType()) {
            case Co2Constant.TYPE_Waveform_Data_Mode: {
                Co2Data co2Data=new Co2Data(serialMsg.getContent());
                LiveEventBus.get(EventMsgConst.MsgCo2Data).post(co2Data);
                }

            break;
            case Co2Constant.TYPE_Capnostat_Zero_Command: {
                mCmdReplyListener.onSuccess(Co2Constant.TYPE_Capnostat_Zero_Command);

            }
            break;
            case Co2Constant.TYPE_Get_Set_Sensor_Settings: {
                mCmdReplyListener.onSuccess(Co2Constant.TYPE_Get_Set_Sensor_Settings);
            }
            break;
            case Co2Constant.TYPE_CO2_O2_Waveform_Mode: {
                Co2O2Data co2Data=new Co2O2Data(serialMsg.getContent());

            }
            break;
            case Co2Constant.TYPE_NACK_Error: {

            //    mCmdReplyListener.onSuccess(Co2Constant.TYPE_NACK_Error);
            }
            break;
            case Co2Constant.TYPE_Stop_Continuous_Mode: {
                mCmdReplyListener.onSuccess(Co2Constant.TYPE_Stop_Continuous_Mode);
            }
            break;
            case Co2Constant.TYPE_GET_SOFTWARE_REVISION: {
                mCmdReplyListener.onSuccess(Co2Constant.TYPE_GET_SOFTWARE_REVISION);
            }
            break;
            case Co2Constant.TYPE_Sensor_Capabilities: {
                mCmdReplyListener.onSuccess(Co2Constant.TYPE_Sensor_Capabilities);
            }
            break;
            case Co2Constant.TYPE_Reset_No_Breaths_Detected_Flag: {
                mCmdReplyListener.onSuccess(Co2Constant.TYPE_Reset_No_Breaths_Detected_Flag);
            }
            break;
            case Co2Constant.TYPE_Reset_Capnostat: {
                mCmdReplyListener.onSuccess(Co2Constant.TYPE_Reset_Capnostat);
            }
            break;
            default:
        }
    }


    public static void main(String[] args) {
        byte b = (byte) 80;


    }
}
