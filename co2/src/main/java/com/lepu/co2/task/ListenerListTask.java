package com.lepu.co2.task;


import com.lepu.co2.manager.Co2Manager;

/**
 *命令回调监听
 */
public class ListenerListTask extends Thread {


    private CmdTask cmdTask;
    private byte cmd;
    private boolean isCancel;

    public ListenerListTask(CmdTask cmdTask) {
        this.cmdTask = cmdTask;
        cmdTask.start();
    }

    public ListenerListTask(byte cmd,boolean isCancel) {
        this.cmd = cmd;
        this.isCancel=isCancel;
    }


    @Override
    public void run() {
        if (cmdTask != null) {
            Co2Manager.getInstance().mCmdTaskList.add(cmdTask);
        } else {
            for (int i = 0; i < Co2Manager.getInstance().mCmdTaskList.size(); i++) {
                if (Co2Manager.getInstance().mCmdTaskList.get(i).getCmd() == cmd) {
                    if (!isCancel){
                        Co2Manager.getInstance().mCmdTaskList.get(i).onSuccess();
                    }
                    Co2Manager.getInstance().mCmdTaskList.remove(i);
                    i--;
                }
            }
        }

    }

}
