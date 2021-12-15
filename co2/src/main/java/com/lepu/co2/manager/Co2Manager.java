package com.lepu.co2.manager;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.FontRequest;
import android.serialport.SerialPort;
import android.util.Log;

import com.lepu.co2.constant.Co2Constant;
import com.lepu.co2.constant.ConfigConst;
import com.lepu.co2.listener.CmdReplyListener;
import com.lepu.co2.listener.SerialConnectListener;
import com.lepu.co2.obj.CmdReply;
import com.lepu.co2.task.CmdReplyTimeOutTask;
import com.lepu.co2.uitl.ByteUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
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
    //
    List<CmdReplyTimeOutTask> mCmdReplyTimeOutTaskList=new ArrayList<>();

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
                    Log.e("buffer",  "读取数据");
                    try {
                        if (Co2Constant.IS_TEST_DATA) {//测试模式
                            //测试数据
                            //    sendTestEcgData();
                            //昨天采集的数据
                      //      sendTestEcgDataFile();
                            Log.e("buffer",  "测试数据");
                        } else {//正式数据
                            if (mInputStream == null) return;
                            byte[] buffer = ByteUtils.readStream(mInputStream);
                            for (int i=0;i<buffer.length;i++){
                                Log.e("buffer", buffer[i]+"");
                            }
                            Log.e("buffer",  "接收完成");
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
            mCmdReplyListener = cmdReplyListener;
            writeBytes (bytes);
            CmdReplyTimeOutTask cmdReplyTimeOutTask=
                    new CmdReplyTimeOutTask(cmdReplyListener,new CmdReply(bytes[0]), ConfigConst.CMD_TIMEOUT);
            mCmdReplyTimeOutTaskList.add(cmdReplyTimeOutTask);
            cmdReplyTimeOutTask.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (mCmdReplyListener != null) {
                mCmdReplyListener.onFail(new CmdReply(bytes[0]));
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
            if (data[i] == SerialContent.SYNC_H && data[i + 1] == SerialContent.SYNC_L) {
                completeData = new byte[(0x00ff & data[i + 2])];
                if (i + completeData.length > data.length) {
                    //把最后的数据 放在下一个任务中
                    surplusData = new byte[data.length - i];
                    System.arraycopy(data, i, surplusData, 0, data.length - i);
                    break;
                } else {
                    taskindex++;
                    System.arraycopy(data, i, completeData, 0, completeData.length);
                    //校验数据
                    if (CRCUitl.CRC8(completeData)  ) {
                        //越过已处理数据
                        i = i + completeData.length - 1;
                        //分发数据
                        distributeMsg(completeData);

                    } else {
                        Log.d("taskindex", taskindex + "");
                    }
                }
            }

        }
        time = System.currentTimeMillis() - time;
        //  Log.e("接收到处理完数据时间", time + "----"+taskindex);

    }


    public boolean is


    public static void main(String[] args) {
        byte b=(byte) 80;


    }
}
