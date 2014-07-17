package com.example.gongzhu;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gongzhu.server.CommonUtil;
import com.example.gongzhu.server.LanServerManager;
import com.example.gongzhu.server.ServerCallback;
import com.example.gongzhu.server.ServerManagerImpl;
import com.example.gongzhu.server.WanServerManager;
import com.syh.dalilystudio.NetworkChangedManager;
import com.syh.dalilystudio.NetworkChangedManager.CallbackKey;
import com.syh.dalilystudio.NetworkChangedManager.NetworkChangedCallback;
import com.syh.dalilystudio.ServerData;
import com.syh.dalilystudio.WifiUtil;

/**
 * 局域网对战可以创建游戏房间
 * 
 * @author shenyh
 * 
 */
public class HallActivity extends BaseActivity implements
        NetworkChangedCallback{

    private ServerManagerImpl mServer;
    private RoomAdapter mRoomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);
        initView();
        initData();
        handlerIntent(getIntent());
    }

    private View.OnClickListener mOnClickListener=new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
            switch(v.getId()){
            case R.id.create_room:{
                if(1==1){
                    int[] a=new int[]{1,2};
                    Bundle bundle=new Bundle();
                    bundle.putInt("key", 10);
                    bundle.putString("key_str", "ss");
                    bundle.putIntArray("key_intarray", a);
                    Parcel parcel=Parcel.obtain();
                    parcel.writeValue(bundle);
                    byte[] bytes=parcel.marshall();
                    Log.d("", "parcel key bytes:"+new String(bytes));
                    
                    Parcel newparcel=Parcel.obtain();
                    newparcel.unmarshall(bytes, 0, bytes.length);
                    newparcel.setDataPosition(0);
                    Bundle object=(Bundle) newparcel.readValue(Bundle.class.getClassLoader());
                    int key3=object.getInt("key",-1);
                    Log.d("", "parcel key:"+key3);
                    int[] key_intarray=object.getIntArray("key_intarray");
                    Log.d("", "parcel key_intarray:"+key_intarray[1]);  
                  
////                    newparcel.writeByteArray(bytes);
//                    byte[] newbytes=newparcel.marshall();
//                    Log.d("", "parcel key newbytes:"+new String(newbytes));
//                    if(bytes.equals(newbytes)){
//                        Log.d("", "parcel key equals");
//                    }
//                    
//                    try {
//                        Bundle newBundle=Bundle.CREATOR.createFromParcel(parcel);
////                        Bundle newBundle=new Bundle();
////                        newBundle.readFromParcel(newparcel);
////                        parcel.readBundle(getClassLoader());//readBundle();
//                        int key=newBundle.getInt("key",-1);
//                        Log.d("", "parcel key:"+key);
//                        
//                    } catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
                    return;
                }
                Log.d("","create_room");
//                mServer.enterHall();
                WifiManager wifiManager = WifiUtil.getWifiManager(HallActivity.this);
                wifiManager.setWifiEnabled(false);

                String ssid = CommonUtil.getDeviceApSSID(HallActivity.this);
                String passwor = CommonUtil.AP_PWD;

                boolean result=WifiUtil.createAP(wifiManager, ssid, passwor, false);//不删除
                Log.d("","create_room result:"+result);
                break;
            }
            }
            
        }
    };
    private void initView() {
        findViewById(R.id.create_room).setOnClickListener(mOnClickListener);
    }

    private void initData() {
        WifiManager wifiManager = WifiUtil.getWifiManager(HallActivity.this);
        if (wifiManager.isWifiEnabled()) {
            // wifi开启，直接loadData
            loadWifiList();
        } else {
//            wifiManager.setWifiEnabled(true);
//            // todo监听广播,开启wifi后scane
//            loadWifiList();
        }

    }

    private List<CallbackKey> registList;

    /**
     * 获取wifi列表
     * 
     */
    private void loadWifiList() {
        if (registList == null) {
            registList = new ArrayList<CallbackKey>();
        }
        WifiManager wifiManager = WifiUtil.getWifiManager(HallActivity.this);
        CallbackKey scanCallback = new CallbackKey(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        NetworkChangedManager.getInstance().registCallback(scanCallback, this);
        registList.add(scanCallback);

        CallbackKey connectCallback = new CallbackKey(
                ConnectivityManager.CONNECTIVITY_ACTION);
        NetworkChangedManager.getInstance().registCallback(connectCallback,
                this);
        registList.add(connectCallback);
        wifiManager.startScan();
    }

    private List<ScanResult> mNetworkList;

    private void setRoomData(List<ScanResult> networkList) {
        Log.d("", "test setRoomData size"
                + (networkList == null ? 0 : networkList.size()));
        if(networkList==null){
            return;
        }
        if (mNetworkList == null) {
            mNetworkList = new ArrayList<ScanResult>();
        } else {
            mNetworkList.clear();
        }
        for(ScanResult scanResult:networkList){
            if(scanResult.SSID.startsWith(CommonUtil.AP_PREFIX)){
                mNetworkList.add(scanResult);
            }
        }
        
        if (mRoomAdapter == null) {
            mRoomAdapter = new RoomAdapter();
            ListView listView = (ListView) findViewById(R.id.room_list_view);
            listView.setAdapter(mRoomAdapter);
        } else {
            mRoomAdapter.notifyDataSetChanged();
        }
    }

    private class RoomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mNetworkList != null ? mNetworkList.size() : 0;
        }

        @Override
        public ScanResult getItem(int position) {
            return mNetworkList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(HallActivity.this).inflate(
                        R.layout.room_item_view, null);
                holder.ssidTextView = (TextView) convertView
                        .findViewById(R.id.ssid);
                holder.joinButton = (Button) convertView
                        .findViewById(R.id.join);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            final ScanResult wifiInfo = getItem(position);
            holder.ssidTextView.setText(wifiInfo.SSID + " " + wifiInfo.level);
            holder.joinButton.setOnClickListener(null);
            return convertView;
        }

    }

    private static class Holder {
        private TextView ssidTextView;
        private Button joinButton;
    }

    private void handlerIntent(Intent intent) {
        int playType = intent.getIntExtra(Constants.EXTRA_PLAY_TYPE,
                Constants.UNKNOW_PLAY);
        switch (playType) {
        case Constants.LAN_PLAY: {
            mServer = new LanServerManager(this, mServerCallback);
            break;
        }
        case Constants.WAN_PLAY: {
            mServer = new WanServerManager(this, mServerCallback);
            break;
        }
        default: {
            finish();
            return;
        }
        }
        // mServer.enterHall();
    }

    private ServerCallback mServerCallback = new ServerCallback() {

        @Override
        public void onReceive(ServerData serverdata) {
            // TODO Auto-generated method stub

        }

        @Override
        public ServerData praseServerResult(String requestData, String data) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public int praseRequestCMD(String requestdata) {
            // TODO Auto-generated method stub
            return 0;
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("", "test onReceive");
        WifiManager wifiManager = WifiUtil.getWifiManager(HallActivity.this);
        setRoomData(wifiManager.getScanResults());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (registList != null) {
            for (CallbackKey key : registList) {
                NetworkChangedManager.getInstance().unRegistCallback(key);
            }
            registList = null;
        }
    }

}
