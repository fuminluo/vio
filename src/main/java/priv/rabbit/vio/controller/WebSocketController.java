package priv.rabbit.vio.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import priv.rabbit.vio.config.websocket.SocketSessionRegistry;
import priv.rabbit.vio.dto.websocket.CallackDTO;
import priv.rabbit.vio.dto.websocket.SendToUserListReq;
import priv.rabbit.vio.dto.websocket.SendToUserReq;
import priv.rabbit.vio.dto.websocket.WiselyMessage;
import priv.rabbit.vio.service.WebSocketService;

import javax.annotation.Resource;

@Controller
public class WebSocketController {

    private static Logger LOG = LoggerFactory.getLogger(WebSocketController.class);

    @Resource
    private WebSocketService webSocketService;

    @Autowired
    private SocketSessionRegistry socketSessionRegistry;

    @GetMapping(value = "/api/web/socket/v1/webSocket.html")
    public String index() {
        return "/webSocket";
    }


    @MessageMapping("/welcome")//@MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。
    @SendTo("/topic/getResponse")//如果服务器接受到了消息，就会对订阅了@SendTo括号中的地址传送消息。
    public Object sendToTop(WiselyMessage wiselyMessage) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("主题信息：", wiselyMessage.getName());

        return jsonObject;
    }

    /**
     * 发送点对点消息
     *
     * @param sendToUseReq
     */
    @MessageMapping("/sendToUser")
    public void sendToUser(SendToUserReq sendToUseReq) {
        webSocketService.sendToUser(sendToUseReq.getToUserId(), sendToUseReq.getContent());
    }

    /**
     * 群发消息
     *
     * @param sendToUserListReq
     */
    @MessageMapping("/SendToUserList")
    public void SendToUserList(SendToUserListReq sendToUserListReq) {

        webSocketService.sendToUsers(sendToUserListReq.getToUserIds(), sendToUserListReq.getContent());
    }


    /**
     * 消息回掉
     *
     * @param callackDTO
     * @throws Exception
     */
    @MessageMapping("/callback")//@MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。

    public void Callack(CallackDTO callackDTO) throws Exception {
        LOG.info("==state==" + callackDTO.getState() + ",==userId==" + callackDTO.getUserId());
    }

}
