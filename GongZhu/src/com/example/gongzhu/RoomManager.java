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
        WifiManager wifiManager = (WifiManager) mContext
                .getSystemService(Context.WIFI_SERVICE);
        String ssid = "test123456";
        String password = "pwd123456";
        WifiConfiguration wifiConfiguration = createWifiConfiguration(
                wifiManager, ssid, password, TYPE_WEP);
        boolean wifiEnable=false;
        try {
            wifiEnable = setWifiApEnabled(wifiManager,wifiConfiguration, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(mContext, "wifiEnable: " + wifiEnable,
                Toast.LENGTH_SHORT).show();
    }

    public static WifiConfiguration isWifiExsits(WifiManager wifiManager,
            String ssid) {
        Iterator localIterator = wifiManager.getConfiguredNetworks().iterator();
        WifiConfiguration localWifiConfiguration;
        do {
            if (!localIterator.hasNext()) {
                return null;
            }
            localWifiConfiguration = (WifiConfiguration) localIterator.next();
        } while (!localWifiConfiguration.SSID.equals("\"" + ssid + "\""));
        return localWifiConfiguration;
    }

    public static final int TYPE_NO_PASSWD = 1;
    public static final int TYPE_WEP = 2;
    public static final int TYPE_WPA = 3;

    // public static WifiConfiguration createWifiConfiguration(WifiManager
    // wifiManager,String ssid, String password,
    // int type) {
    public static WifiConfiguration createWifiConfiguration(
            WifiManager wifiManager, String ssid, String password, int type) {

        // Log.v(TAG, "SSID = " + SSID + "## Password = " + password +
        // "## Type = " + type);

        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + ssid + "\"";

        WifiConfiguration tempConfig = isWifiExsits(wifiManager, ssid);
        if (tempConfig != null) {
            wifiManager.removeNetwork(tempConfig.networkId);
        }

        // 分为三种情况：1没有密码2用wep加密3用wpa加密
        if (type == TYPE_NO_PASSWD) {// WIFICIPHER_NOPASS
            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;

        } else if (type == TYPE_WEP) { // WIFICIPHER_WEP
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + password + "\"";
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (type == TYPE_WPA) { // WIFICIPHER_WPA
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.TKIP);
            // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }

        return config;
    }

    public static boolean setWifiApEnabled(WifiManager wifiManager,
            WifiConfiguration paramWifiConfiguration, boolean enable)
            throws Exception {

        Class<? extends WifiManager> localClass = wifiManager.getClass();
        Class<?>[] arrayOfClass = new Class[2];
        arrayOfClass[0] = WifiConfiguration.class;
        arrayOfClass[1] = Boolean.TYPE;
        Method localMethod = localClass.getMethod("setWifiApEnabled",
                arrayOfClass);
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = paramWifiConfiguration;
        arrayOfObject[1] = Boolean.valueOf(enable);
        return (Boolean) localMethod.invoke(wifiManager, arrayOfObject);

    }
}
