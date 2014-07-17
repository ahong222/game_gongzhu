package com.example.gongzhu;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class RoomManager {
    private Context mContext;

    private static RoomManager roomManager;

    /**
     * 非线程安全单例
     * 
     * @return
     */
    public static RoomManager getInstance() {
        if (roomManager == null) {
            roomManager = new RoomManager();
        }
        return roomManager;
    }

    private RoomManager() {

    }

    public static void destroy() {
        if (roomManager != null) {
            roomManager = null;
        }
    }

    /**
     * 获取房间列表
     * 
     * @return
     */
    public List<RoomInfo> getRoomList() {
        return null;
    }

    public void addRoom(int roomKey) {

    }

    /**
     * 创建房间
     * 
     * @param name
     * @param roomKey
     */
    public void createRoom(String name, String roomKey) {
//        WifiManager wifiManager = (WifiManager) mContext
//                .getSystemService(Context.WIFI_SERVICE);
//        String ssid = "test123456";
//        String password = "pwd123456";
//        WifiConfiguration wifiConfiguration = createWifiConfiguration(
//                wifiManager, ssid, password, TYPE_WEP);
//        boolean wifiEnable=false;
//        try {
//            wifiEnable = setWifiApEnabled(wifiManager,wifiConfiguration, true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Toast.makeText(mContext, "wifiEnable: " + wifiEnable,
//                Toast.LENGTH_SHORT).show();
    }

    
}
