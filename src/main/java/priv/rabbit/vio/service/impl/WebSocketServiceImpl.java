package priv.rabbit.vio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import priv.rabbit.vio.service.WebSocketService;

import java.util.List;

import static priv.rabbit.vio.common.Constant.*;

@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public void sendToTopic(String msg) {
        template.convertAndSend(TOPIC_BASE_PATH, msg);
    }

    @Override
    public void sendToUsers(List<String> users, Object msg) {
        users.forEach(userName -> {
            template.convertAndSendToUser(userName, P2P_PUSH_PATH, msg);
        });
    }

    /**
     * @描述 发送点对点消息
     * @参数 [to 目标用户, msg 消息内容]
     * @返回值 void
     * @创建人 LuoFuMIn
     * @创建时间 2018/7/16
     */
    @Override
    public void sendToUser(String to, String msg) {
        template.convertAndSendToUser(to, P2P_PUSH_PATH, msg);
    }


}
