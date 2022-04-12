package com.lepu.co2.enums;

/**
 * 电源状态
 */
public enum CO2SourceCurrent {
    /**
     * 正常
     */
    SOURCE_CURRENT_NORMAL,
    /**
     * 等待源电流稳定。Capnostat 源电流需要稳定，然后才能执行归零。 此错误会在大约 20 秒内清除。
     */
    WAITING_FOR_SOURCE_CURRENT_STABILIZATION,
    /**
     *源电流漂移。 通过源的电流偏离了工厂校准时读取的值。 传感器不能再归零到工厂规格。
     */
    SOURCE_CURRENT_DRIFT,
    /**
     *源电流限制误差。 通过源的电流超出操作规范。
     */
    SOURCE_CURRENT_LIMIT_ERROR,
    /**
     * 未知
     */
     UN_KNOW


    }
