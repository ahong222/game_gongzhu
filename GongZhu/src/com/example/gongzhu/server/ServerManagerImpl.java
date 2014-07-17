package com.example.gongzhu.server;

import android.content.Context;

public abstract class ServerManagerImpl {

    public Context context;
    public ServerCallback serverCallback;
    public SocketManager socketManager;

    public ServerManagerImpl(Context context, ServerCallback serverCallback) {
        this.context = context;
        this.serverCallback = serverCallback;
        socketManager=new SocketManager(serverCallback);
    }

    /**
     * 进入游戏大厅
     */
    public abstract void enterHall();

    /**
     * 连接服务器
     */
    public abstract void connectServer();
    
    /**
     * 
     * @param data 请求数据
     * @param ip IP或Url
     * @param port
     * @param connectType 0.http；1.socket；2.socket长连接
     */
    public void socketRequest(String requestData,String ip,int port,int connectType){
        socketManager.request(requestData, ip, port, connectType);
    }
    
    /**
     * 
     * @param data
     * @param url
     */
    public void httpRequest(byte[] data,String url){
        
    }
}
