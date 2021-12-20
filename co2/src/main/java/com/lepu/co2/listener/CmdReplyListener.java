package com.lepu.co2.listener;


import com.lepu.co2.constant.Co2Constant;

/**
 * 串口命令监听 除血压命令
 */
public interface CmdReplyListener {
    //请求成功
    void onSuccess(byte cmd);
    //请求失败
    void onFail(byte cmd);
    //请求超时
    void onTimeOut(byte cmd);
}
