package priv.rabbit.vio.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import priv.rabbit.vio.common.Constant;
import priv.rabbit.vio.config.websocket.SocketSessionRegistry;
import priv.rabbit.vio.dto.websocket.*;
import priv.rabbit.vio.service.WebSocketService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@Api(value = "WebSocketController", description="websocket案例")
@Controller
public class WebSocketController {

    private static Logger LOG = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @GetMapping(value = "/v1/app/web-socket/init-html")
    public String index() {
        return "/web-socket";
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
     * @param wsMessage
     */
    @MessageMapping("/sendToUser")
    public void sendToUser(@Valid @RequestBody WSMessage wsMessage) {
        webSocketService.sendToUser(wsMessage.getTo(), wsMessage.getContent());
    }

    /**
     * 群发消息
     *
     * @param sendToUserListReq
     */
    @MessageMapping("/sendToUserList")
    public void sendToUserList(SendToUserListReq sendToUserListReq) {
        webSocketService.sendToUsers(sendToUserListReq.getToUserIds(), sendToUserListReq.getContent());
    }


    /**
     * 消息回掉
     *
     * @param callackDTO
     * @throws Exception
     */
    @MessageMapping("/callback")//@MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。
    public void callback(CallackDTO callackDTO) throws Exception {
        LOG.info("==state==" + callackDTO.getState() + ",==userId==" + callackDTO.getUserId());
    }

    @MessageMapping("/heartbeat")
    public void heartbeat(@Valid @RequestBody WSHeartbeat heartbeat) throws Exception {
        LOG.info("》》》 heartbeat==" + heartbeat.getFrom()+"，时间戳"+heartbeat.getTimestamp());
        redisTemplate.opsForValue().set(Constant.WEB_SOCKET_USER_NO +  heartbeat.getFrom(),  heartbeat.getFrom(), 30, TimeUnit.SECONDS);


    }
}
