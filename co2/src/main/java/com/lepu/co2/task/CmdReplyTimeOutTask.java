package com.lepu.co2.task;


import com.lepu.co2.listener.CmdReplyListener;

/**
 * 命令超时监听线程
 */
public class CmdReplyTimeOutTask extends Thread{
    CmdReplyListener cmdReplyListener;
    byte cmd;
    long timeOut;


    public CmdReplyTimeOutTask(CmdReplyListener cmdReplyListener,byte cmd, long timeOut) {
        this.cmdReplyListener = cmdReplyListener;
        this.cmd = cmd;
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
            cmdReplyListener.onTimeOut(cmd);
        }


    }

    public CmdReplyListener getCmdReplyListener() {
        return cmdReplyListener;
    }

    public void setCmdReplyListener(CmdReplyListener cmdReplyListener) {
        this.cmdReplyListener = cmdReplyListener;
    }

    public byte getCmdReply() {
        return cmd;
    }

    public void setCmdReply(byte cmd) {
        this.cmd = cmd;
    }
    public void cencel(){
        cmdReplyListener=null;

    }
}
