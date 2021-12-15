package com.lepu.co2.obj;

import com.lepu.co2.constant.Co2Constant;

/**
 * 串口消息处理 生成数据包和数据包解析
 */
public class SerialMsg {
    byte type;  // 命令标识符
    byte nbf; // 跟随的字节数

    byte[] content;
    byte cks;


    public SerialMsg(byte[] buf) {
        type = buf[0];
        nbf = buf[1];
        content = new byte[nbf-3];
        System.arraycopy(buf, 2, content, 0, nbf-3);
        cks=buf[buf.length-1];

    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getNbf() {
        return nbf;
    }

    public void setNbf(byte nbf) {
        this.nbf = nbf;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte getCks() {
        return cks;
    }

    public void setCks(byte cks) {
        this.cks = cks;
    }
}
