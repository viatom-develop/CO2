package com.lepu.co2.listener;

/**
 * 串口连接监听
 */
public interface SerialConnectListener {
    //连接成功
    void onSuccess();

    //连接失败
    void onFail();
}
