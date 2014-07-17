package com.example.gongzhu;

public class Constants {

    /**
     * 作战类型<br>
     * @see {@link LAN_PLAY},{@link WAN_PLAY},{@link SINGLE_PLAY}
     */
    public static final String EXTRA_PLAY_TYPE="PLAY_TYPE";
    /**局域网对战**/
    public static final int LAN_PLAY=0;
    /**联网对战**/
    public static final int WAN_PLAY=1;
    /**单机对战**/
    public static final int SINGLE_PLAY=2;
    /**未知对战值**/
    public static final int UNKNOW_PLAY=-1;
    
    public static interface CMD{
        public static int UNKNOW=-1;
        public static int ENTER_HALL=0;
    }
}
