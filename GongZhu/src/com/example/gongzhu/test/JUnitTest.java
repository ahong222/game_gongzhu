package com.example.gongzhu.test;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.test.AndroidTestCase;
import android.util.Log;

import com.syh.dalilystudio.WifiUtil;

public class JUnitTest extends AndroidTestCase {

    //测试创建Wifi
    public void testCreateWifi(){
        Log.d("","testCreateWifi");
        WifiManager wifiManager=(WifiManager)getContext().getSystemService(Context.WIFI_SERVICE);
        String ssid="wep123456";
        String password="12345678";
        int type=2;
//        WifiConfiguration wifi=WifiUtil.createWifiInfo(wifiManager,"wp123456", "WIFI_AP_PASSWORD", 3, "ap");
//        try {
//            boolean result=WifiUtil.setWifiApEnabled(wifiManager,wifi,true);
//            Log.d("","testCreateWifi result:"+result);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
//        String ssid2="wpa123456";
//        int type2=2;
//        WifiConfiguration wifi2=WifiUtil.createWifiConfiguration(wifiManager, ssid2, password, type2);
//        try {
//            boolean result2=WifiUtil.setWifiApEnabled(wifiManager,wifi2,true);
//            Log.d("","testCreateWifi result2:"+result2);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
    }
    
    //测试修改该Wifi
    public void testModifyWifi(){
        
    }
}
