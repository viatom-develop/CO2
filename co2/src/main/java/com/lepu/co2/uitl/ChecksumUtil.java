package com.lepu.co2.uitl;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

/**
 * 校验和
 */
public class ChecksumUtil {
    /**
     * 通过字节数组计算校验和
     *
     * @param bytes
     * @return
     */
    public static long getCRC32Checksum(byte[] bytes) {
        Checksum crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length-1);
        return crc32.getValue();
    }

    /**
     *当处理较大数据集时，上述方法因加载所有数据至内存导致效率低下。
     * 如果可以获取InputStream，可以使用CheckedInputStream 类创建创建校验和。通过使用这种方法，我们可以定义一次处理多少字节
     * @param stream
     * @param bufferSize
     * @return
     * @throws IOException
     */
    public static long getChecksumCRC32(InputStream stream, int bufferSize)
            throws IOException {
        CheckedInputStream checkedInputStream = new CheckedInputStream(stream, new CRC32());
        byte[] buffer = new byte[bufferSize];
        while (checkedInputStream.read(buffer, 0, buffer.length) >= 0) {
        }
        return checkedInputStream.getChecksum().getValue();
    }

    /**
     * 求校验和的算法
     * @param
     * @return 校验和
     */
/*    public static byte sumCheck(byte[] b, int len){
        int sum = 0;
        for(int i = 0; i < len; i++){
            sum = sum + b[i];
        }
        if(sum > 0xff){ //超过了255，使用补码（补码 = 原码取反 + 1）
            sum = ~sum;
            sum = sum + 1;
        }
        return (byte) (sum & 0xff);
    }*/

   public static byte AddChecksum(int num, byte buf[])
    {
        byte checksum;
        int i;
        checksum = 0;
        for (i = 0; i < num; i++)
            checksum = (byte) (checksum + buf[i]);
        checksum = (byte) ((-checksum) & 0x7F);
        buf[num] = checksum;
        return checksum;
    }


    public static void main(String[] args) {
        byte[] b = new byte[7];
        b[0] = (byte) 0xfd;
        b[1] = (byte) 0xfc;
        b[2] = (byte) 0x08;
        b[3] = (byte) 0x80;
        b[4] = (byte) 0x02;
        b[5] = (byte) 0x00;
        b[6] = (byte) 0x0a;
     //   byte result = sumCheck(b, 7);
     //   System.out.printf("%x", result);//正确的结果应该是8d


   //     System.out.printf("%x", AddChecksum(6,b));//正确的结果应该是8d

       // System.out.printf("%x", getCRC32Checksum(b));//正确的结果应该是8d

    }
}
