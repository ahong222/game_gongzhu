package com.example.gongzhu;

import com.syh.dalilystudio.WifiUtil;

import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * 登陆页，显示局域网游戏，网络游戏，单机游戏 局域网游戏点击后显示大厅，还可创建游戏桌 网络游戏点击后显示网络大厅 单机游戏点击后直接开始游戏
 * 
 * @author shenyh
 * 
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            return rootView;
        }
    }

    public void lanPlay(View view) {
        Intent intent=new Intent(this,HallActivity.class);
        intent.putExtra(Constants.EXTRA_PLAY_TYPE, Constants.LAN_PLAY);
        startActivity(intent);
    }

    public void wanPlay(View view) {
        Intent intent=new Intent(this,HallActivity.class);
        intent.putExtra(Constants.EXTRA_PLAY_TYPE, Constants.WAN_PLAY);
        startActivity(intent);
    }

    public void singlePlay(View view) {
        Intent intent=new Intent(this,GameActivity.class);
        intent.putExtra(Constants.EXTRA_PLAY_TYPE, Constants.SINGLE_PLAY);
        startActivity(intent);
    }
}
