package com.example.gongzhu.server;

import android.content.Context;
import android.text.TextUtils;

import com.syh.dalilystudio.DeviceUtil;

public class CommonUtil {
    /**
     * 热点的密码
     */
    public static final String AP_PWD="gongzhu123456";

    public static final String AP_PREFIX="gongzhu_";
    /**
     * 为当前手机生成一个唯一的ssid
     * @param context
     * @return 如果为空，表示无法生成唯一ID
     */
    public static String getDeviceApSSID(Context context){
        String imei=DeviceUtil.getIMEI(context);
        if(TextUtils.isEmpty(imei)){
            imei=DeviceUtil.getWlanMacAddress(context);
        }
        if(imei!=null){
            return AP_PREFIX+DeviceUtil.getMenufacture()+"_"+imei;
        }
        return null;
    }
}
