package com.lepu.co2_data;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lepu.co2.constant.SerialCmd;
import com.lepu.co2.listener.CmdReplyListener;
import com.lepu.co2.listener.SerialConnectListener;
import com.lepu.co2.manager.Co2Manager;
import com.lepu.co2.obj.CmdReply;

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

                Co2Manager.getInstance().serialSendData(SerialCmd.cmdStopContinuousMode(), cmdReplyListener);
                    }


        });
        //CO2 波形模式命令
        findViewById(R.id.btn_waveform_data_mode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Co2Manager.getInstance().serialSendData(SerialCmd.cmdWaveformDataMode(), cmdReplyListener);
            }


        });
        //CO2  偏移校准/零校准命令
        findViewById(R.id.btn_capnostat_zero_command).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Co2Manager.getInstance().serialSendData(SerialCmd.cmdCapnostatZeroCommand(), cmdReplyListener);
            }


        });



        //获取版本号
        findViewById(R.id.btn_get_software_revision).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Co2Manager.getInstance().serialSendData(SerialCmd.cmdGetSoftwareRevision(), cmdReplyListener);

            }
        });
    }

    CmdReplyListener cmdReplyListener = new CmdReplyListener() {
        @Override
        public void onSuccess(CmdReply cmdReply) {
            Log.e("CmdReply", "onSuccess");
        }

        @Override
        public void onFail(CmdReply cmdReply) {
            Log.e("CmdReply", "onFail");
        }

        @Override
        public void onTimeOut(CmdReply cmdReply) {
            Log.e("CmdReply", "onTimeOut");
        }
    };
}