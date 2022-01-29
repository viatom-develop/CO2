package com.lepu.co2.enums;

/**
 * Capnostat 校准状态
 */
public enum CO2CalibrationStatus {
    /**
     *00 没有正在归零
     */
    NO_ZEROING_IN_PROGRESS,

    /**
     *01 – 正在调零 - Capnostat Zero 目前正在进行中。
     */
    ZEROING_IN_PROGRESS,

    /**
     *10 需要归零 - 需要 Capnostat 零
     * 对于 Capnostat，出于以下原因之一：
     * • 检查适配器（字节 1，位 1）
     * • 负 CO2 错误（字节 1，位 1、0）
     */
    ZERO_REQUIRED,

    /**
     *11 – 需要归零：归零错误 - 在 Capnostat 归零期间发现错误。
     *  如果气道适配器堵塞或适配器中存在 CO2 气体，则可能会发生这种情况。
     *  对于 LoFlo，这可能表示在未插入样品池时尝试归零。
     */
    ZERO_REQUIRED_ZERO_ERROR,







}
