package com.example.gongzhu.server;

public class SocketManager {

    /**
     * 
     * @param request 长连接时，可以为空
     * @param response
     */
    public void onReceive(String request, String response) {

    }

    public void onRequestError(String request, int errorType, String errorMsg) {

    }
    
    private ServerCallback mServerCallback;
    public SocketManager(ServerCallback serverCallback){
        this.mServerCallback=serverCallback;
    }
    
    public void request(String requestData,String address,int port,int connectType){
        SocketConnection socketConnection=new SocketConnection(this);
        socketConnection.setLongLink(connectType==1,mServerCallback);
        socketConnection.setAddress(address, port);
        //TODO 缓存SocketConnection
        socketConnection.sendMessage(requestData);
    }
}
