package com.example.gongzhu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 局域网对战可以创建游戏房间
 * @author shenyh
 *
 */
public class HallActivity extends BaseActivity {

    public static final String EXTRA_PLAY_TYPE="PLAY_TYPE";
    /**局域网对战**/
    public static final int LAN_PLAY=0;
    /**联网对战**/
    public static final int WAN_PLAY=1;
    /**
     * 启动HallActivity
     * @param context
     * @param playType
     */
    public static void startActivity(Context context,int playType){
        Intent intent=new Intent(context,HallActivity.class);
        intent.putExtra(EXTRA_PLAY_TYPE, playType);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    
}
