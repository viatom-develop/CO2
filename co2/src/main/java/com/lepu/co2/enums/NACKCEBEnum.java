package com.lepu.co2.enums;

/**
 * NACK 错误枚举
 */
public enum NACKCEBEnum {
    /**
     * 引导代码等待引导加载程序 - 仅启动
     */
    BootCode,
    /**
     * 无效命令 每当出现定义的命令之外的命令时，就会发生这种情况。
     */
    InvalidCommand,
    /**
     * 校验和错误 每当接收到不正确的校验和时就会发生这种情况。
     */
    ChecksumError,
    /**
     * 超时错误 超过 500 毫秒时会发生这种情况
     * 命令的第一个字节和最后一个字节之间。
     */
    TimeOutError,
    /**
     * 无效字节计数 当字节计数小于
     * 特定命令所需的字节数。
     */
    InvalidByteCount,
    /**
     * 无效数据字节 每当需要非命令字节时就会发生这种情况
     * 并且遇到命令字节（MSB=1 的字节）。
     */
    InvalidDataByte,
    /**
     * 系统故障 当系统处于非功能状态时会发生这种情况
     * 由于系统故障。所有命令都将被忽略。
     */
    SystemFaulty,
    /**
     * 预留字段
     */
    NotUsed,
    /**
     * 系统故障 当系统处于非功能状态时会发生这种情况
     * 由于系统故障。所有命令都将被忽略。
     */
    SystemFaulty2;


    public static NACKCEBEnum NCAKCEBEnum(int CBE) {
        switch (CBE) {
            case 0:
                return BootCode;
            case 1:
                return InvalidCommand;
            case 2:
                return ChecksumError;
            case 3:
                return TimeOutError;
            case 4:
                return InvalidByteCount;
            case 5:
                return InvalidDataByte;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                return SystemFaulty;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                return NotUsed;
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                return SystemFaulty2;

        }
        return NotUsed;
    }


}
