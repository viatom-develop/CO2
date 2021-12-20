package com.lepu.co2_data;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lepu.co2.enums.BalanceGasEnum;
import com.lepu.co2.enums.CO2UnitEnum;
import com.lepu.co2.constant.Co2Cmd;
import com.lepu.co2.enums.SleepModeEnum;
import com.lepu.co2.enums.TimePeriodEnum;
import com.lepu.co2.listener.CmdReplyListener;
import com.lepu.co2.listener.SerialConnectListener;
import com.lepu.co2.manager.Co2Manager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // 数据以19200波特率传输，字节大小为8个数据位，1个停止位，无奇偶校验
        Co2Manager.getInstance().init(this, "/dev/ttyS0", new SerialConnectListener() {
            @Override
            public void onSuccess() {
                Log.e("init","onSuccess");
            }

            @Override
            public void onFail() {
                Log.e("init","onFail");
            }
        });

        //停止传输命令
        findViewById(R.id.btn_stop_continuous_mode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdStopContinuousMode(), cmdReplyListener);
            }
        });


        //设置气压
        findViewById(R.id.btn_set_barometric_pressure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdSetBarometricPressure((short) 760), cmdReplyListener);
            }


        });

        //设置补偿气体
        findViewById(R.id.btn_set_gas_compensations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdSetGasCompensations(16, BalanceGasEnum.AIR, (short) 0), cmdReplyListener);
            }


        });
        //设置计算周期
        findViewById(R.id.btn_set_time_period).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdSetTimePeriod(TimePeriodEnum.TimePeriod1B), cmdReplyListener);
            }


        });



        //设置工作温度
        findViewById(R.id.btn_set_gas_temperature).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdSetGasTemperature((short) 350), cmdReplyListener);
            }


        });
        //设置工作单位
        findViewById(R.id.btn_set_co2_unit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdSetCo2Unit((CO2UnitEnum.mmHg)), cmdReplyListener);
            }


        });



        //CO2 波形模式命令
        findViewById(R.id.btn_waveform_data_mode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdWaveformDataMode(), cmdReplyListener);
            }


        });
        //CO2 02 波形模式命令
        findViewById(R.id.btn_co2_o2_waveform_data_mode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdWaveformDataModeC2CO2(), cmdReplyListener);
            }


        });



        //CO2  偏移校准/零校准命令
        findViewById(R.id.btn_capnostat_zero_command).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdCapnostatZeroCommand(), cmdReplyListener);
            }


        });


        //获取版本号
        findViewById(R.id.btn_get_software_revision).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdGetSoftwareRevision(), cmdReplyListener);

            }
        });

        //测试命令
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdTest(), cmdReplyListener);

            }
        });


        //睡眠模式
        findViewById(R.id.btn_sleep_mode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdSleepMode(SleepModeEnum.NormalOperatingMode), cmdReplyListener);

            }
        });
    }

    CmdReplyListener cmdReplyListener = new CmdReplyListener() {
        @Override
        public void onSuccess(byte cmd) {
            Log.e("CmdReply", "onSuccess");
        }

        @Override
        public void onFail(byte cmd) {
            Log.e("CmdReply", "onFail");
        }

        @Override
        public void onTimeOut(byte cmd) {
            Log.e("CmdReply", "onTimeOut");
        }
    };
}