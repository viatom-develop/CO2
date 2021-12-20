package com.lepu.co2.task;


import com.lepu.co2.listener.Co2CmdListener;

/**
 * 命令超时监听线程
 */
public class Co2CmdTimeOutTask extends Thread{
    Co2CmdListener cmdReplyListener;
    byte cmd;
    long timeOut;


    public Co2CmdTimeOutTask(Co2CmdListener cmdReplyListener, byte cmd, long timeOut) {
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

    public Co2CmdListener getCmdReplyListener() {
        return cmdReplyListener;
    }

    public void setCmdReplyListener(Co2CmdListener cmdReplyListener) {
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
