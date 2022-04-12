package com.lepu.co2.listener;


import com.lepu.co2.constant.Co2Constant;

/**
 * co2串口命令监听
 */
public interface Co2CmdListener {
    //请求成功
    void onSuccess();
    //请求失败
    void onFail();
    //请求超时
    void onTimeOut();
}
