package com.lepu.co2.task;

import com.lepu.co2.constant.Co2Constant;
import com.lepu.co2.listener.Co2CmdListener;
import com.lepu.co2.manager.Co2Manager;


/**
 * 命令超时监听线程
 */
public class CmdTask extends Thread {
    Co2CmdListener mCo2CmdListener;
    byte cmd;
    long timeOut = Co2Constant.CMD_TIMEOUT;


    public CmdTask(Co2CmdListener co2CmdListener, byte cmd) {
        this.mCo2CmdListener = co2CmdListener;
        this.cmd = cmd;

    }

    @Override
    public void run() {

        try {
            sleep(timeOut);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mCo2CmdListener != null ) {
            mCo2CmdListener.onTimeOut();
            Co2Manager.getInstance().mThreadCmdListener.execute(new ListenerListTask(cmd,true));
        }

    }


    public byte getCmd() {
        return cmd;
    }

    public void setCmd(byte cmd) {
        this.cmd = cmd;
    }

    public void onSuccess() {
        if (mCo2CmdListener != null) {
            mCo2CmdListener.onSuccess();
        }
        mCo2CmdListener = null;
    }
}
