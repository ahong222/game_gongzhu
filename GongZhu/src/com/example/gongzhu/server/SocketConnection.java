package com.example.gongzhu.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.text.TextUtils;

import com.example.gongzhu.Constants;
import com.syh.dalilystudio.LogUtil;
import com.syh.dalilystudio.ServerData.ERROR;

public class SocketConnection {
    /**
     * 回车换行符
     */
    public static final String DATA_END = "\r\n";
//    private final static String TAG = "SocketConnection";

    private SocketManager mSocketManager;
    private Socket internalSocket;
    private String address = null;
    private int port;
    private DataOutputStream DOS = null;
    private BufferedReader DIS = null;
    /**
     * 是否是长链接
     */
    private boolean mLongLink = false;
    /**
     * 回调
     */
    private ServerCallback mServerCallback;

    public SocketConnection(SocketManager socketManager) {
        this.mSocketManager=socketManager;
        this.internalSocket = new Socket();
    }
    
    public String getAddress(){
        return address;
    }
    
    public int getPort(){
        return port;
    }

    public boolean setAddress(String address, int port) {
        this.address = address;
        this.port = port;
        InetSocketAddress add = new InetSocketAddress(address, port);
        try {
            internalSocket.connect(add, 5000);// 等待5S时间连接
            this.DIS = new BufferedReader(new InputStreamReader(
                    internalSocket.getInputStream(), "UTF-8"));
            this.DOS = new DataOutputStream(internalSocket.getOutputStream());

            if (mLongLink) {
                // 循环读数据
                receiveLongLinkMessage();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 设置是否是长连接,在调用setAddress方法之前设置
     * 
     * @param longLink
     */
    public void setLongLink(boolean longLink, ServerCallback serverCallback) {
        if (address != null) {
            throw new RuntimeException(
                    "should invoke setLongLink before setAdress");
        }
        mLongLink = longLink;
        mServerCallback = serverCallback;
    }

    /**
     * 向服务器发送消息
     */
    public void sendMessage(String msg) {
        LogUtil.d("sendMessage :" + msg);
        String result = null;
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        try {
            // 1.发送数据
            DOS.write((msg + DATA_END).getBytes("UTF-8"));
            DOS.flush();
            LogUtil.d("sendMessage end start readLine mLongLink:" + mLongLink);
            // 2.读取数据
            if (mLongLink) {
                return;
            }

            result = DIS.readLine();
            LogUtil.d("sendMessage result:" + result);
            if (mSocketManager != null) {
                mSocketManager.onReceive(msg,result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // TODO 判断是否是网络不可用
            if (mSocketManager != null) {
                mSocketManager.onRequestError(msg,ERROR.IO_ERROR,null);
            }
        }
    }

    /**
     * 接收消息
     */
    public void receiveLongLinkMessage() {
        // 开一个线程
        new Thread() {

            @Override
            public void run() {
                LogUtil.d("start receiveLongLinkMessage");
                while (!closed) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        String result = DIS.readLine();
                        LogUtil.d("long link receiveMessage:" + result);

                        if (mSocketManager != null) {
                            mSocketManager.onReceive(null,result);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        if (!closed) {
                         // TODO 判断是否是网络不可用
                            if (mSocketManager != null) {
                                mSocketManager.onRequestError(null,ERROR.IO_ERROR,null);
                            }
                        }
                    }
                }
            }

        }.start();

    }

    private boolean closed = false;

    public void close() {
        closed = true;
        if (DOS != null) {
            try {
                DOS.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                DOS.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (DIS != null) {
                try {
                    DIS.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getCMD(String cmdStr) {
        int tempCmd = Constants.CMD.UNKNOW;
        return tempCmd;
    }
}
