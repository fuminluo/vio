package priv.rabbit.vio.service;

import java.util.List;

public interface WebSocketService {
     void sendToTopic(String msg);

     void sendToUser(String to, String msg);

     void sendToUsers(List<String> users, Object msg);

}
