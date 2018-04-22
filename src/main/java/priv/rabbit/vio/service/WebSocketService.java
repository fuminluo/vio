package priv.rabbit.vio.service;

import java.util.List;

public interface WebSocketService {
    public void sendToTopic(String msg);

    public void sendToUser(String userId, Object msg);

    public void sendToUsers(List<String> users, Object msg);

}
