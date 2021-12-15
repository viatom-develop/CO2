package com.lepu.co2.listener;


import com.lepu.co2.obj.CmdReply;

/**
 * 串口命令监听 除血压命令
 */
public interface CmdReplyListener {
    //请求成功
    void onSuccess(CmdReply cmdReply);
    //请求失败
    void onFail(CmdReply cmdReply);
    //请求超时
    void onTimeOut(CmdReply cmdReply);
}
