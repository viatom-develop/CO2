package com.lepu.co2.task;


import com.lepu.co2.listener.CmdReplyListener;
import com.lepu.co2.obj.CmdReply;

/**
 * 命令超时监听线程
 */
public class CmdReplyTimeOutTask extends Thread{
    CmdReplyListener cmdReplyListener;
     CmdReply cmdReply;
    long timeOut;


    public CmdReplyTimeOutTask(CmdReplyListener cmdReplyListener, CmdReply cmdReply, long timeOut) {
        this.cmdReplyListener = cmdReplyListener;
        this.cmdReply = cmdReply;
        this.timeOut=timeOut;
    }


    @Override
    public void run() {
        try {
            sleep(timeOut);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (cmdReplyListener!=null){
            cmdReplyListener.onTimeOut(cmdReply);
        }


    }

    public CmdReplyListener getCmdReplyListener() {
        return cmdReplyListener;
    }

    public void setCmdReplyListener(CmdReplyListener cmdReplyListener) {
        this.cmdReplyListener = cmdReplyListener;
    }

    public CmdReply getCmdReply() {
        return cmdReply;
    }

    public void setCmdReply(CmdReply cmdReply) {
        this.cmdReply = cmdReply;
    }
    public void cencel(){
        cmdReplyListener=null;

    }
}
