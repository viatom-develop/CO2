package com.lepu.co2.obj;

import com.lepu.co2.constant.Co2Constant;

import java.io.Serializable;

/**
 * 命令回复
 */
public class CmdReply implements Serializable, Cloneable {
    /**
     * 命令回复类型
     */
    CmdReplyType cmdReplyType;

    /**
     * 参数板版本
     */
    String version;

    public CmdReply(SerialMsg serialMsg) {
        getCmdReplyTypeByType(serialMsg.type);
        if (serialMsg.content != null && serialMsg.content.length > 0) {
            version = new String(serialMsg.content);
        }
    }

    public CmdReply(byte type) {
        getCmdReplyTypeByType(type);
    }


    public CmdReply() {
    }

    public CmdReplyType getCmdReplyType() {
        return cmdReplyType;
    }

    public void setCmdReplyType(CmdReplyType cmdReplyType) {
        this.cmdReplyType = cmdReplyType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public void getCmdReplyTypeByType(byte type) {
        switch (type) {
            case Co2Constant.TYPE_GET_SOFTWARE_REVISION: {

                cmdReplyType = CmdReplyType.CMD_TYPE_GET_SOFTWARE_REVISION;
            }
            default:
        }


    }

    public enum CmdReplyType {

        /**
         *
         */
        CMD_TYPE_GET_SOFTWARE_REVISION,


    }


}
