package com.lepu.co2.enums;
/**
 * 优先 CO2 状态/错误字节
 */
public enum PrioritizedCO2Enum {
    /**
     *正常
     */
    Normal((byte) 0x00, 0),
    /**
     * 传感器温度过高
     * 确保传感器没有暴露在极热（热
     * 灯等）。 如果错误仍然存在，请将传感器返回工厂
     * 服务
     */
    SensorOverTemp((byte) 0x01, 1),
    /**
     * 传感器故障
     * 检查传感器是否正确插入。重新插入或
     * 必要时重置传感器。 如果错误仍然存在，返回
     * 传感器到工厂进行维修
     */
    SensorFaulty((byte) 0x02, 2),
    /**
     * 主机必须设置气压和
     */
    NoMessage((byte) 0x03, 3),
    /**
     * 睡眠模式下的 Capnostat
     */
    CapnostatInSleepMode((byte) 0x04, 4),
    /**
     * 零进度  Capnostat Zero 目前正在进行中。
     */
    ZeroInProgress((byte) 0x05, 5),
    /**
     * 传感器预热
     * 存在以下条件之一：
     * 温度传感器
     * 温度不稳定
     * 源电流不稳定
     */
    SensorWarmUp((byte) 0x06, 6),
    /**
     * “检查采样线”
     * 检查采样管是否被堵塞或扭结。
     */
    CheckSamplingLine((byte) 0x0A, 7),
    /**
     * "零要求”
     * 要清除，请检查气道适配器并在必要时进行清洁。
     * 如果这不能纠正错误，请执行适配器
     * 零。
     * 如果您必须多次适配器零，则可能
     * 可能存在硬件错误。
     */
    ZeroRequired((byte) 0x07, 8),
    /**
     *“二氧化碳超出范围”
     * 如果错误仍然存在，则执行归零。
     */
    CO2OutOfRange((byte) 0x08, 9),
    /**
     *“检查气道适配器”
     * 如果有粘液或湿气，则清除、清洁气道适配器
     * 看到了。 如果适配器干净，请执行 Capnostat 调零。
     */
    CheckAirwayAdapter((byte) 0x09, 10);


    byte key;
    int priority;

    PrioritizedCO2Enum(byte key, int priority) {
        this.key = key;
        this.priority = priority;
    }

    public byte getKey() {
        return key;
    }

    public int getPriority() {
        return priority;
    }


    public static PrioritizedCO2Enum getPrioritizedCO2Status(byte key) {
        switch (key) {
            case (byte) 0x01: {
                return SensorOverTemp;
            }
            case (byte) 0x02: {
                return SensorFaulty;
            }
            case (byte) 0x03: {
                return NoMessage;
            }
            case (byte) 0x04: {
                return CapnostatInSleepMode;
            }
            case (byte) 0x05: {
                return ZeroInProgress;
            }
            case (byte) 0x06: {
                return SensorWarmUp;
            }
            case (byte) 0x0A: {
                return CheckSamplingLine;
            }
            case (byte) 0x07: {
                return ZeroRequired;
            }
            case (byte) 0x08: {
                return CO2OutOfRange;
            }
            case (byte) 0x09: {
                return CheckAirwayAdapter;
            }


            default:
                return Normal;

        }
    }
}
