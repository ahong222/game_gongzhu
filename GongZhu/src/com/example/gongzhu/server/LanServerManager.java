package com.example.gongzhu.server;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.syh.dalilystudio.WifiUtil;

public class LanServerManager extends ServerManagerImpl {

    public LanServerManager(Context context, ServerCallback serverCallback) {
        super(context, serverCallback);
    }

    @Override
    public void connectServer() {

    }

    @Override
    public void enterHall() {
        // 本地的，请求socket端口号
        WifiManager wifiManager = WifiUtil.getWifiManager(context);
        wifiManager.setWifiEnabled(false);

        String ssid = CommonUtil.getDeviceApSSID(context);
        String passwor = CommonUtil.AP_PWD;

        WifiUtil.createAP(wifiManager, ssid, passwor, true);
    }

}
