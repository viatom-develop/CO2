package com.lepu.co2.enums;

/**
 *温度状态
 */
public enum CO2TemperatureStatusEnem {
    /**
     *00  在工作温度下稳定 - 传感器温度稳定并准备好运行。
     */
    STABLE_AT_OPERATING_TEMPERATURE,
    /**
     * 01 低于工作温度 - 传感器未达到工作温度。 这种情况通常发生在通电时或将传感器插入主机系统之后。
     */
    BELOW_OPERATING_TEMPERATURE,
    /**
     *10 高于工作温度 - 传感器的内部温度高于工作温度。
     */
    ABOVE_OPERATING_TEMPERATURE,
    /**
     * 11– 温度不稳定
     */
    TEMPERATURE_UNSTABLE
}
