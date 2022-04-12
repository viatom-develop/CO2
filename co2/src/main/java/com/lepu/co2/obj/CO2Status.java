package com.lepu.co2.obj;

import com.lepu.co2.enums.CO2CalibrationStatus;
import com.lepu.co2.enums.CO2SourceCurrent;
import com.lepu.co2.enums.CO2TemperatureStatusEnum;

/**
 * CO2状态 在DPI等于1的时候 有值
 */
public class CO2Status {
    /**
     * Always zero
     */
    int Sync;

    /**
     * 每当“没有呼吸
     * 检测到”超时条件发生。
     */
    int breathsFlag;

    /**
     * 该位在 Capnostat 放置后置位
     * 在睡眠模式。
     */
    int inSleepMode;

    /**
     * 如果 CO2 传感器未准备好进行 Capnostat 调零，则设置该位。
     * 如果设置了需要零（字节 2，位 1），并且设置了该位，则可能会出现以下一种或多种情况
     * 存在：
     * • 检测到呼吸（字节 2，位 2）
     * • 温度不稳定（字节 2，位 1,0）
     * • 源电流不稳定（字节 2，位 5）
     * • 处于睡眠模式。 （字节 2，位 5）
     */
    int CO2SensorNOTReadytoZero;

    /**
     * The value being calculated is greater than the upper CO2 limit (150 mmHg, 20.0 kPa, or 19.7 %). The
     * maximum value output is the upper CO2 limit.
     * 计算的值大于 CO2 上限（150 mmHg、20.0 kPa 或 19.7 %）。 这
     * 最大值输出是 CO2 上限。
     */
    int CO2OutofRange;

    /**
     * Capnostat 在过去 20 秒内检测到呼吸，同时尝试 Capnostat 归零。
     */
    int  BreathsDetected;

    /**
     * 通常是当气道适配器从 Capnostat 上取下时或当气道适配器的窗口出现光学阻塞时引起的。 也可能是由于更改适配器类型时未能执行 Capnostat 归零造成的。
     */
    int CheckAdapter;

    /**
     * 当计算出的 CO2 在一段时间内小于零时，就会出现此错误。 这可能是由在气道中用 CO2 归零的 Capnostat 或气道适配器的光学阻塞引起的
     */
    int NegativeCO2Error;

    /**
     * 源电流
     * 00 ：正常
     * 01 ：等待源电流稳定。Capnostat 源电流需要稳定，然后才能执行归零。 此错误会在大约 20 秒内清除。
     * 10 ：源电流漂移。 通过源的电流偏离了工厂校准时读取的值。 传感器不能再归零到工厂规格。
     * 11 ：源电流限制误差。 通过源的电流超出操作规范。
     */
    CO2SourceCurrent sourceCurrent;

    /**
     * 补偿尚未确定
     */
    int CompensationNotYetSet;

    /**
     * 校准状态
     */
    CO2CalibrationStatus calibrationStatus;

    /**
     * 温度状态
     */
    CO2TemperatureStatusEnum temperatureStatus;

    /**
     * 校验和错误
     * Capnostat EEPROM 中的校准值未通过校验和测试。
     */
    int checksumFaulty;
    /**
     * 硬件错误
     * Capnostat 检测到硬件错误。
     */
    int hardwareError;

    /**
     * 该位在采样泵关闭时置位
     */
    int PumpOff;
    /**
     * 气动系统错误
     */
    int PneumaticSystemError;
    /**
     * 超出泵寿命
     */
    int pumpLifeExceeded;
    /**
     * 未检测到侧流适配器
     */
     int SidestreamAdapterNotDetected;

    /**
     * 默认构造函数 一切正常
     */
    public CO2Status() {
        Sync = 0;
        breathsFlag = 0;
        inSleepMode = 0;
        CO2SensorNOTReadytoZero = 0;
        CO2OutofRange = 0;
        BreathsDetected = 0;
        CheckAdapter =0;
        NegativeCO2Error =0;
        //源电流
        sourceCurrent = CO2SourceCurrent.UN_KNOW;
        CompensationNotYetSet =0;
        //校准状态
        calibrationStatus = CO2CalibrationStatus.UN_KNOW;
        //温度状态
        temperatureStatus = CO2TemperatureStatusEnum.UN_KNOW;
        //校验和错误
        checksumFaulty = 0;
        //硬件错误
        hardwareError = 0;
        //该位在采样泵关闭时置位
        PumpOff = 0;
        //气动系统错误
        PneumaticSystemError =0;
        //超出泵寿命
        pumpLifeExceeded = 0;
        //未检测到侧流适配器
        SidestreamAdapterNotDetected= 0;

    }

    /**
     * @param buf
     */
    public CO2Status(byte[] buf) {
        Sync = buf[4] >> 7 & 0x1;
        breathsFlag = buf[4] >> 6 & 0x1;
        inSleepMode = buf[4] >> 5 & 0x1;
        CO2SensorNOTReadytoZero = buf[4] >> 4 & 0x1;
        CO2OutofRange = buf[4] >> 3 & 0x1;
        BreathsDetected = buf[4] >> 2 & 0x1;
        CheckAdapter = buf[4] >> 1 & 0x1;
        NegativeCO2Error = buf[4] >> 0 & 0x1;
        //源电流
        int buf5_6 = buf[5] >> 6 & 0x1;
        int buf5_5 = buf[5] >> 5 & 0x1;
        if (buf5_6 == 0 && buf5_5 == 0) {
            sourceCurrent = CO2SourceCurrent.SOURCE_CURRENT_NORMAL;
        } else if (buf5_6 == 0 && buf5_5 == 1) {
            sourceCurrent = CO2SourceCurrent.WAITING_FOR_SOURCE_CURRENT_STABILIZATION;
        } else if (buf5_6 == 1 && buf5_5 == 0) {
            sourceCurrent = CO2SourceCurrent.SOURCE_CURRENT_DRIFT;
        } else if (buf5_6 == 1 && buf5_5 == 1) {
            sourceCurrent = CO2SourceCurrent.SOURCE_CURRENT_LIMIT_ERROR;
        }

        CompensationNotYetSet = buf[5] >> 4 & 0x1;
        //校准状态
        int buf5_3 = buf[5] >> 3 & 0x1;
        int buf5_2 = buf[5] >> 2 & 0x1;
        if (buf5_3 == 0 && buf5_2 == 0) {
            calibrationStatus = CO2CalibrationStatus.NO_ZEROING_IN_PROGRESS;
        } else if (buf5_3 == 0 && buf5_2 == 1) {
            calibrationStatus = CO2CalibrationStatus.ZEROING_IN_PROGRESS;
        } else if (buf5_3 == 1 && buf5_2 == 0) {
            calibrationStatus = CO2CalibrationStatus.ZERO_REQUIRED;
        } else if (buf5_3 == 1 && buf5_2 == 1) {
            calibrationStatus = CO2CalibrationStatus.ZERO_REQUIRED_ZERO_ERROR;
        }

        //温度状态
        int buf5_1 = buf[5] >> 1 & 0x1;
        int buf5_0 = buf[5] >> 0 & 0x1;
        if (buf5_1 == 0 && buf5_0 == 0) {
            temperatureStatus = CO2TemperatureStatusEnum.STABLE_AT_OPERATING_TEMPERATURE;
        } else if (buf5_1 == 0 && buf5_0 == 1) {
            temperatureStatus = CO2TemperatureStatusEnum.BELOW_OPERATING_TEMPERATURE;
        } else if (buf5_1 == 1 && buf5_0 == 0) {
            temperatureStatus = CO2TemperatureStatusEnum.ABOVE_OPERATING_TEMPERATURE;
        } else if (buf5_1 == 1 && buf5_0 == 1) {
            temperatureStatus = CO2TemperatureStatusEnum.TEMPERATURE_UNSTABLE;
        }
        //校验和错误
        checksumFaulty = buf[6] >> 6 & 0x1;
        //硬件错误
        hardwareError = buf[6] >> 5 & 0x1;
        //该位在采样泵关闭时置位
        PumpOff = buf[7] >> 3 & 0x1;
        //气动系统错误
        PneumaticSystemError = buf[7] >> 2 & 0x1;
        //超出泵寿命
        pumpLifeExceeded = buf[7] >> 1 & 0x1;
        //未检测到侧流适配器
        SidestreamAdapterNotDetected= buf[7] >> 0 & 0x1;

    }


    public int getSync() {
        return Sync;
    }

    public void setSync(int sync) {
        Sync = sync;
    }

    public int getBreathsFlag() {
        return breathsFlag;
    }

    public void setBreathsFlag(int breathsFlag) {
        this.breathsFlag = breathsFlag;
    }

    public int getInSleepMode() {
        return inSleepMode;
    }

    public void setInSleepMode(int inSleepMode) {
        this.inSleepMode = inSleepMode;
    }

    public int getCO2SensorNOTReadytoZero() {
        return CO2SensorNOTReadytoZero;
    }

    public void setCO2SensorNOTReadytoZero(int CO2SensorNOTReadytoZero) {
        this.CO2SensorNOTReadytoZero = CO2SensorNOTReadytoZero;
    }

    public int getCO2OutofRange() {
        return CO2OutofRange;
    }

    public void setCO2OutofRange(int CO2OutofRange) {
        this.CO2OutofRange = CO2OutofRange;
    }

    public int getBreathsDetected() {
        return BreathsDetected;
    }

    public void setBreathsDetected(int breathsDetected) {
        BreathsDetected = breathsDetected;
    }

    public int getCheckAdapter() {
        return CheckAdapter;
    }

    public void setCheckAdapter(int checkAdapter) {
        CheckAdapter = checkAdapter;
    }

    public int getNegativeCO2Error() {
        return NegativeCO2Error;
    }

    public void setNegativeCO2Error(int negativeCO2Error) {
        NegativeCO2Error = negativeCO2Error;
    }

    public CO2SourceCurrent getSourceCurrent() {
        return sourceCurrent;
    }

    public void setSourceCurrent(CO2SourceCurrent sourceCurrent) {
        this.sourceCurrent = sourceCurrent;
    }

    public int getCompensationNotYetSet() {
        return CompensationNotYetSet;
    }

    public void setCompensationNotYetSet(int compensationNotYetSet) {
        CompensationNotYetSet = compensationNotYetSet;
    }

    public CO2CalibrationStatus getCalibrationStatus() {
        return calibrationStatus;
    }

    public void setCalibrationStatus(CO2CalibrationStatus calibrationStatus) {
        this.calibrationStatus = calibrationStatus;
    }

    public CO2TemperatureStatusEnum getTemperatureStatus() {
        return temperatureStatus;
    }

    public void setTemperatureStatus(CO2TemperatureStatusEnum temperatureStatus) {
        this.temperatureStatus = temperatureStatus;
    }

    public int getChecksumFaulty() {
        return checksumFaulty;
    }

    public void setChecksumFaulty(int checksumFaulty) {
        this.checksumFaulty = checksumFaulty;
    }

    public int getHardwareError() {
        return hardwareError;
    }

    public void setHardwareError(int hardwareError) {
        this.hardwareError = hardwareError;
    }

    public int getPumpOff() {
        return PumpOff;
    }

    public void setPumpOff(int pumpOff) {
        PumpOff = pumpOff;
    }

    public int getPneumaticSystemError() {
        return PneumaticSystemError;
    }

    public void setPneumaticSystemError(int pneumaticSystemError) {
        PneumaticSystemError = pneumaticSystemError;
    }

    public int getPumpLifeExceeded() {
        return pumpLifeExceeded;
    }

    public void setPumpLifeExceeded(int pumpLifeExceeded) {
        this.pumpLifeExceeded = pumpLifeExceeded;
    }

    public int getSidestreamAdapterNotDetected() {
        return SidestreamAdapterNotDetected;
    }

    public void setSidestreamAdapterNotDetected(int sidestreamAdapterNotDetected) {
        SidestreamAdapterNotDetected = sidestreamAdapterNotDetected;
    }

    @Override
    public String toString() {
        return "CO2Status{" +
                "Sync=" + Sync +
                ", breathsFlag=" + breathsFlag +
                ", inSleepMode=" + inSleepMode +
                ", CO2SensorNOTReadytoZero=" + CO2SensorNOTReadytoZero +
                ", CO2OutofRange=" + CO2OutofRange +
                ", BreathsDetected=" + BreathsDetected +
                ", CheckAdapter=" + CheckAdapter +
                ", NegativeCO2Error=" + NegativeCO2Error +
                ", sourceCurrent=" + sourceCurrent +
                ", CompensationNotYetSet=" + CompensationNotYetSet +
                ", calibrationStatus=" + calibrationStatus +
                ", temperatureStatus=" + temperatureStatus +
                ", checksumFaulty=" + checksumFaulty +
                ", hardwareError=" + hardwareError +
                ", PumpOff=" + PumpOff +
                ", PneumaticSystemError=" + PneumaticSystemError +
                ", pumpLifeExceeded=" + pumpLifeExceeded +
                ", SidestreamAdapterNotDetected=" + SidestreamAdapterNotDetected +
                '}';
    }
}
