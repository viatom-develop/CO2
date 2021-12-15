package com.lepu.co2.constant;

public class Co2Constant {

    /**
     * 是否是测试数据
     */
    public static boolean IS_TEST_DATA = false;
    /**
     * CO2 波形模式命令以 100 Hz 的速率发送。 该命令用于
     * 传输 CO2 波形和数据。 每当无法计算 CO2 波形时，
     * 最小 CO2 值 (-10.00) 作为波形“penlift”发送（参见第 6.4.2 节）。 这
     * 对应于 CO2WB1 和 CO2WB2 都为零。 此外，所有 0 的值
     * 单位（mmHg、kPa 和 %）是 CO2WB1 和 CO2WB2 的相同绝对字节值。
     */
    public static final byte TYPE_Waveform_Data_Mode  = (byte)0x80; //8.1 CO2 波形/数据模式（命令 80h）
    /**
     * 这个命令用于启动Capnostat 0。零用于校正气道适配器类型的差异。
     * Capnostat zero必须在没有CO的情况下进行
     * 2．有关详细描述，请参阅第4.2节，Capnostat Zeroing。
     */
    public static final byte TYPE_Capnostat_Zero_Command  = (byte)0x82; //8.2 Capnostat Zero命令(82h命令)
    /**
     *此命令用于获取和设置 Capnostat 中的各种传感器设置
     * 模块。 当命令在没有可选 DB1 的情况下传输到 Capnostat 时……
     * DBN 数据字节，指定传感器设置的当前值在
     * 命令的响应字符串。 这对应于“获取”当前值
     * 传感器设置。 如果 DB1 ... DBN 数据字节从主机传输，则传感器
     * 设置设置为该值。 这对应于“设置”指定的传感器设置，并且
     * 这个新值在命令的响应字符串中传输。
     * 主机必须请求有效的设置字节标识符 - ISB。 如果请求无效的 ISB，则
     * 命令的响应字符串将返回 ISB = 0 且不返回数据字节。
     */
    public static final byte TYPE_Get_Set_Sensor_Settings   = (byte)0x84; //获取/设置传感器设置 (Command 84h)
    /**
     * CO2 / O2 波形模式命令以 100 Hz 的速率发送。 当 CO2 或 O2
     * 无法计算波形，最小 CO2 或 O2 值 (-10.00) 作为波形发送
     * “penlift”（见第 6.4.2 节）。 这对应于 CO2WB1 & CO2WB2（或 OWB1 & OWB2）
     * 为零。 此外，所有单位（mmHg、kPa 和 %）中的 0 值是相同的绝对值
     * 两个波形数据字节的字节值。
     */
    public static final byte TYPE_CO2_O2_Waveform_Mode   = (byte)0x90; //CO2 / O2 波形模式（命令 90h）
    /**
     * 通信协议具有内置的命令错误检查。
     */
    public static final byte TYPE_NACK_Error  = (byte)0xC8; //NACK 错误（命令 C8h）
    /**
     *该命令用于停止连续响应命令的数据传输。
     * 当前进程停止后立即发送响应。 当前任何数据包
     * 正在发送将在当前连续响应停止之前完整发送。 如果
     * 波形模式命令未激活，停止连续模式命令将
     * 发送适当的响应，但该命令无效。
     */
    public static final byte TYPE_Stop_Continuous_Mode  = (byte)0xC9; //停止连续模式(命令C9h)
    /**
     *此命令返回当前软件修订级别。 修订级别是一串 
     * 最多 35 个非 NULL 终止的 ASCII 字符； 它的长度是 NBF - 2。
     * 字节 RF 描述了请求的修订字符串。 下表描述了
     * 可用的修订字符串请求。
     */
    public static final byte TYPE_GET_SOFTWARE_REVISION = (byte)0xCA; //获取软件版本(命令CAh)
    /**
     *此命令返回传感器功能（包括是否支持 O2 或 LoFlo）
     * 并且还允许启用或禁用这些功能。当主流
     * Capnostat 已开机，仅启用主流 CO2。所有其他传感器
     * 功能在启动时默认关闭。如果 Capnostat 和主机系统都
     * 支持额外的 O2 或 LoFlo 功能，然后可以使用此功能启用它们
     * 命令。此命令应首先由主机系统（SCI = 0）使用
     * 确定 Capnostat 是否支持这些附加功能。
     * 要检索传感器中可用的传感器功能，请使用 SCI = 发送此命令
     * 0. 要查询 Capnostat 以确定当前启用了哪些功能，请发送
     * 此命令的 SCI = 1。在 SCI 设置为 2 的情况下发送此命令将启用和
     * 禁用在 SCB 字节中发送的相关功能。唯一的两个乐器
     * 可以同时启用的功能是 CO2 Mainstream 和 O2
     * 主流选项。
     * 注意：SCB 仅在启用传感器功能时发送到 Capnostat。它是
     * 总是在 Capnostat 的响应中返回以指示可用的
     * 能力（对于 SCI = 0）或启用能力的当前状态（SCI = 1 或 SCI =
     * 2）。
     */
    public static final byte TYPE_Sensor_Capabilities = (byte)0xC8; //传感器功能（命令 CBh）
    /**
     *此命令用于强制系统清除未检测到呼吸标志。 什么时候
     * 发出此命令，未检测到呼吸的状态位被清除，系统
     * 进入类似于初始启动的状态。 请注意，所有 DPI 参数也会重置。 这
     * 即使未设置未检测到呼吸的状态标志，也可以发送命令。
     */
    public static final byte TYPE_Reset_No_Breaths_Detected_Flag = (byte)0xCC; //  重置未检测到呼吸标志（命令 CCh）

    /**
     *该命令用于在传感器中引起系统看门狗复位。 当这
     * 命令发出，系统进入死循环，看门狗定时器复位
     * 一秒后系统。
     */
    public static final byte TYPE_Reset_Capnostat = (byte)0xF8; //重置 Capnostat（命令 F8h）




}
