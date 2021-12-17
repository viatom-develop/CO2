package com.lepu.co2.obj;

import com.lepu.co2.constant.Co2Constant;
import com.lepu.co2.uitl.ChecksumUtil;

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
        content = new byte[nbf-1];
        System.arraycopy(buf, 2, content, 0, nbf-1);
        cks=buf[buf.length-1];

    }

    /**
     * 拼接命令的时候使用
     * @param type
     * @param content
     */
    public SerialMsg(byte type,byte[] content) {
        this.type = type;
        nbf = (byte) (content.length+1);
        this.content = content;
        byte[] buf=new byte[content.length+3];
        buf[0]=type;
        buf[1]=nbf;
        System.arraycopy(buf, 2, content, 0, nbf-1);
        cks= ChecksumUtil.AddChecksum(buf.length-1, buf);
     }

    public byte[] toBytes() {
        byte[] buf=new byte[content.length+3];
        buf[0]=type;
        buf[1]=nbf;
        System.arraycopy(buf, 2, content, 0, nbf-1);
        cks= ChecksumUtil.AddChecksum(buf.length-1, buf);
        return buf;
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


    public static void main(String[] args) {
        SerialMsg msg = new SerialMsg(Co2Constant.TYPE_Capnostat_Zero_Command, new byte[0]);
        byte[] bytes=msg.toBytes();
        System.out.println(msg.content.length+"");


    }
}
