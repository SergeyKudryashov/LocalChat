package com.ss.localchat.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import android.widget.Toast;

import com.ss.localchat.R;
import com.ss.localchat.service.AdvertiseService;

public class SettingsActivity extends AppCompatActivity {

    public static final String START_ADVERTISING = "Start Advertising";
    public static final String STOP_ADVERTISING = "Stop Advertising";


    private ServiceConnection mAdvertiseServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mAdvertiseBinder = (AdvertiseService.AdvertiseBinder)service;

            isBoundAdvertising = true;
            startService(mIntent);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mAdvertiseBinder = null;
            isBoundAdvertising = false;
        }
    };

    private AdvertiseService.AdvertiseBinder mAdvertiseBinder;

    private Intent mIntent;

    private boolean isBoundAdvertising;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        init();
    }

    private void init(){
        mIntent = new Intent(SettingsActivity.this, AdvertiseService.class);

        Switch advertisingSwitch = findViewById(R.id.turn_on_off_advertising_switch);
        final TextView advertisingText = findViewById(R.id.advertising_text);

        advertisingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    bindService(mIntent, mAdvertiseServiceConnection, Context.BIND_AUTO_CREATE);

                    //startService(mIntent);
                    advertisingText.setText(STOP_ADVERTISING);
                } else {
                    if(isBoundAdvertising){
                        unbindService(mAdvertiseServiceConnection);
                        stopService(mIntent);

                        isBoundAdvertising = false;

                        advertisingText.setText(START_ADVERTISING);
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
