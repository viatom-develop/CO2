package com.lepu.co2_data;

import static java.lang.Thread.sleep;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lepu.co2.constant.Co2Cmd;
import com.lepu.co2.constant.Co2EventMsgConst;
import com.lepu.co2.enums.BalanceGasEnum;
import com.lepu.co2.enums.CO2UnitEnum;
import com.lepu.co2.enums.ModelEnum;
import com.lepu.co2.enums.SleepModeEnum;
import com.lepu.co2.enums.TimePeriodEnum;
import com.lepu.co2.listener.Co2CmdListener;
import com.lepu.co2.listener.Co2ConnectListener;
import com.lepu.co2.manager.Co2Manager;
import com.lepu.co2.obj.Co2Data;
import com.lepu.co2.obj.NACK;
import com.lepu.co2.uitl.FileUtils;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    File file = FileUtils.createFile(Environment.getExternalStorageDirectory().getAbsolutePath(),"co2data.DAT");
    boolean getDataToFile=false;

    int co2Index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




       // 数据以19200波特率传输，字节大小为8个数据位，1个停止位，无奇偶校验
        Co2Manager.getInstance().init(this, "/dev/ttyS0", new Co2ConnectListener() {
            @Override
            public void onSuccess() {
                 Log.e("lzd","onSuccess");


            }

            @Override
            public void onFail() {
                Log.e("init","onFail");
            }
        });
      //  Co2Manager.getInstance().setConnectType(0);
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
                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdSetGasCompensations(16,BalanceGasEnum.AIR , (short) 0), cmdReplyListener);
            }


        });
        //设置计算周期
        findViewById(R.id.btn_set_time_period).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdSetTimePeriod(TimePeriodEnum.TimePeriod1B), cmdReplyListener);
            }


        });

        //设置呼吸窒息
        findViewById(R.id.btn_apnea_delay_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdSetApneaDelayTime(5), cmdReplyListener);
            }
        });

        //设置重置呼吸窒息
        findViewById(R.id.btn_reset_apnea_delay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdResetApneaDelay(), cmdReplyListener);
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

                Co2Manager.getInstance().serialSendData(Co2Cmd.cmdSetCo2Unit(CO2UnitEnum.mmHg), cmdReplyListener);
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

        //采集数据到文件
        findViewById(R.id.btn_get_data_to_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataToFile=true;
            }
        });


        // 测试模式
        findViewById(R.id. btn_test_mode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Co2Manager.getInstance().setModel(ModelEnum.MODEL_TEST);
            }
        });
        //正式模式
        findViewById(R.id.btn_formal_mode ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Co2Manager.getInstance().setModel(ModelEnum.MODEL_NORMAL);
            }
        });

        //STOP模式
        findViewById(R.id.btn_stop_mode ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Co2Manager.getInstance().setModel(ModelEnum.MODEL_STOP);
            }
        });

        LiveEventBus.get(Co2EventMsgConst.MsgCo2NICK).observeForever(new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                NACK n = (NACK) o;
                Log.e("NACK",n.getNackcebEnum().name());
            }
        });



        LiveEventBus.get(Co2EventMsgConst.MsgCo2Data).observeForever(new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                Co2Data n = (Co2Data) o;
           //     Log.e("Co2Data",n.getCo2Wave()+"");
                if (n.getDPI()==2){
                    Log.e("Co2Data==",n.getETCO2()+"");
                }
                if (n.getDPI()==3){
                //    Log.e("Co2Data RR==","RespRate=="+n.getRespRate());
                }
                if (n.getDPI()==1){
                  Log.e("lzd",n.getCo2Status().getTemperatureStatus()+"<--gzzhuangtai");
                  /*  if (n.getCo2Status().getBreathsFlag() == 1) {
                        Log.e("lzd", "zhixibaoj");
                    } else if (n.getCo2Status().getBreathsFlag() == 0) {
                        Log.e("lzd", "zhixibaoj quxiao");
                    }*/
                }


              /*  co2Index++;
                if (co2Index % 100 == 0) {
                    Log.e("收到1秒数据","-------------------------");
                }

                if (getDataToFile){
                    FileUtils.write2File(file,n.getOriginalData());
                }*/

            }
        });


    }

    Co2CmdListener cmdReplyListener = new Co2CmdListener() {
        @Override
        public void onSuccess() {
            Log.e("lzd", "onSuccess");
        }

        @Override
        public void onFail() {
            Log.e("lzd", "onFail");
        }

        @Override
        public void onTimeOut() {
            Log.e("lzd", "onTimeOut");
        }
    };


}