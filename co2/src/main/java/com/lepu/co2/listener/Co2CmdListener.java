package com.lepu.co2.listener;


import com.lepu.co2.constant.Co2Constant;

/**
 * co2串口命令监听
 */
public interface Co2CmdListener {
    //请求成功
    void onSuccess(byte cmd);
    //请求失败
    void onFail(byte cmd);
    //请求超时
    void onTimeOut(byte cmd);
}
