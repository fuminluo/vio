package priv.rabbit.vio.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.rabbit.vio.config.SocketSessionRegistry;
import priv.rabbit.vio.dto.CallackDTO;
import priv.rabbit.vio.dto.WiselyMessage;
import priv.rabbit.vio.entity.User;
import priv.rabbit.vio.service.WebSocketService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

@Controller
public class WebSocketController {

    private static Logger LOG = LoggerFactory.getLogger(WebSocketController.class);

    @Resource
    private WebSocketService webSocketService;

    @Autowired
    private SocketSessionRegistry socketSessionRegistry;

    @GetMapping(value = "/webSocket")
    public  String index(){
        return "/webSocket";
    }

    @MessageMapping("welcome")//@MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。
    @SendTo("/topic/getResponse")//如果服务器接受到了消息，就会对订阅了@SendTo括号中的地址传送消息。
    public Object say(WiselyMessage message) throws Exception {

        List<String> users = Lists.newArrayList();
        users.add(message.getName());//此处写死只是为了方便测试,此值需要对应页面中订阅个人消息的userId

        //一对一的推送消息
        webSocketService.send2Users(users, "点对点推送消息");

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("topic","主题消息");

        return jsonObject;
    }


    @MessageMapping("/callback")//@MessageMapping和@RequestMapping功能类似，用于设置URL映射地址，浏览器向服务器发起请求，需要通过该地址。
    public void Callack(CallackDTO callackDTO) throws Exception {
        LOG.info("==state=="+callackDTO.getState());
        LOG.info("==userId=="+callackDTO.getUserId());
    }
}
