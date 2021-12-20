package com.lepu.co2.obj;

/**
 * 硬件状态  byte 2
 * 在dpi 7的时候有值
 */
public class HardwareStatus {
    /**
     * 检测到软件错误或故障。
     */
    boolean SoftwareFault;
    /**
     * 加热器热敏电阻检测到错误。
     */
    boolean HeaterThermistorError;
    /**
     * 计算的5伏特电压超出了范围。
     */
    boolean VoltVoltageRangeError5;
    /**
     * 计算的偏置电压超出范围。
     */
    boolean BiasVoltageRangerror;
    /**
     * 计算的源电压超出范围。
     */
    boolean SourceVoltageRangeError;
    /**
     * 计算出的脉冲宽度超出了范围。
     */
    boolean PulseWidthRangeError;
    /**
     * 一个问题已被检测在脉冲宽度看门狗
     */
    boolean PulseWidthWatchdogError;
    /**
     * 总是为零
     */
    int byte1Sync;
    /**
     * O2 预热期超过
     */
    boolean O2WarmUpPeriodExceeded;
    /**
     * CO2 预热期超过
     */
    boolean CO2WarmUpPeriodExceeded;
    /**
     * 主Flash校验和错误
     */
    boolean MainFlashChecksumError;
    /**
     * 程序RAM校验和错误
     */
    boolean ProgramRAMChecksumError;
    /**
     * 总是为零
     */
    int byte2Sync;


    public HardwareStatus(byte byte1, byte byte2) {
        SoftwareFault = (byte1 >> 0 & 0x1) == 1;
        HeaterThermistorError = (byte1 >> 1 & 0x1) == 1;
        VoltVoltageRangeError5 = (byte1 >> 2 & 0x1) == 1;
        BiasVoltageRangerror = (byte1 >> 3 & 0x1) == 1;
        SourceVoltageRangeError = (byte1 >> 4 & 0x1) == 1;
        PulseWidthRangeError = (byte1 >> 5 & 0x1) == 1;
        PulseWidthWatchdogError = (byte1 >> 6 & 0x1) == 1;
        byte1Sync = byte1 >> 7 & 0x1;
        //byte2
        O2WarmUpPeriodExceeded = (byte2 >> 3 & 0x1) == 1;
        CO2WarmUpPeriodExceeded = (byte2 >> 4 & 0x1) == 1;
        MainFlashChecksumError = (byte2 >> 5 & 0x1) == 1;
        ProgramRAMChecksumError = (byte2 >> 6 & 0x1) == 1;
        byte2Sync = byte2 >> 7 & 0x1;
    }


    public boolean isSoftwareFault() {
        return SoftwareFault;
    }

    public void setSoftwareFault(boolean softwareFault) {
        SoftwareFault = softwareFault;
    }

    public boolean isHeaterThermistorError() {
        return HeaterThermistorError;
    }

    public void setHeaterThermistorError(boolean heaterThermistorError) {
        HeaterThermistorError = heaterThermistorError;
    }

    public boolean isVoltVoltageRangeError5() {
        return VoltVoltageRangeError5;
    }

    public void setVoltVoltageRangeError5(boolean voltVoltageRangeError5) {
        VoltVoltageRangeError5 = voltVoltageRangeError5;
    }

    public boolean isBiasVoltageRangerror() {
        return BiasVoltageRangerror;
    }

    public void setBiasVoltageRangerror(boolean biasVoltageRangerror) {
        BiasVoltageRangerror = biasVoltageRangerror;
    }

    public boolean isSourceVoltageRangeError() {
        return SourceVoltageRangeError;
    }

    public void setSourceVoltageRangeError(boolean sourceVoltageRangeError) {
        SourceVoltageRangeError = sourceVoltageRangeError;
    }

    public boolean isPulseWidthRangeError() {
        return PulseWidthRangeError;
    }

    public void setPulseWidthRangeError(boolean pulseWidthRangeError) {
        PulseWidthRangeError = pulseWidthRangeError;
    }

    public boolean isPulseWidthWatchdogError() {
        return PulseWidthWatchdogError;
    }

    public void setPulseWidthWatchdogError(boolean pulseWidthWatchdogError) {
        PulseWidthWatchdogError = pulseWidthWatchdogError;
    }

    public int getByte1Sync() {
        return byte1Sync;
    }

    public void setByte1Sync(int byte1Sync) {
        this.byte1Sync = byte1Sync;
    }

    public boolean isO2WarmUpPeriodExceeded() {
        return O2WarmUpPeriodExceeded;
    }

    public void setO2WarmUpPeriodExceeded(boolean o2WarmUpPeriodExceeded) {
        O2WarmUpPeriodExceeded = o2WarmUpPeriodExceeded;
    }

    public boolean isCO2WarmUpPeriodExceeded() {
        return CO2WarmUpPeriodExceeded;
    }

    public void setCO2WarmUpPeriodExceeded(boolean CO2WarmUpPeriodExceeded) {
        this.CO2WarmUpPeriodExceeded = CO2WarmUpPeriodExceeded;
    }

    public boolean isMainFlashChecksumError() {
        return MainFlashChecksumError;
    }

    public void setMainFlashChecksumError(boolean mainFlashChecksumError) {
        MainFlashChecksumError = mainFlashChecksumError;
    }

    public boolean isProgramRAMChecksumError() {
        return ProgramRAMChecksumError;
    }

    public void setProgramRAMChecksumError(boolean programRAMChecksumError) {
        ProgramRAMChecksumError = programRAMChecksumError;
    }

    public int getByte2Sync() {
        return byte2Sync;
    }

    public void setByte2Sync(int byte2Sync) {
        this.byte2Sync = byte2Sync;
    }
}
