package priv.rabbit.vio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import priv.rabbit.vio.service.WebSocketService;

import java.util.List;

import static priv.rabbit.vio.common.Constant.P2PPUSHPATH;
import static priv.rabbit.vio.common.Constant.PRODUCERPATH;

@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public void sendMsg(String msg) {
        template.convertAndSend(PRODUCERPATH, msg);
    }

    @Override
    public void send2Users(List<String> users, String msg) {
        users.forEach(userName -> {
            template.convertAndSendToUser(userName, P2PPUSHPATH, msg);
        });
    }

}
