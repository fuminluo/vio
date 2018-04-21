package priv.rabbit.vio.service;

import java.util.List;

public interface WebSocketService {
    public void sendMsg(String msg);

    public void send2Users(List<String> users, String msg);

}
