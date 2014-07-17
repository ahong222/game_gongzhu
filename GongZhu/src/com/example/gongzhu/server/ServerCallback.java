package com.example.gongzhu.server;

import com.syh.dalilystudio.ServerData;

public interface ServerCallback {

    public void onReceive(ServerData serverdata);
    
    public ServerData praseServerResult(String requestData,String data);
    public int praseRequestCMD(String requestdata);
}
