package priv.rabbit.vio.config.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import priv.rabbit.vio.common.Constant;

import java.util.concurrent.TimeUnit;

/**
 * Created by baiguantao on 2017/8/4.
 * STOMP监听类
 * 用于session注册 以及key值获取
 */
public class STOMPConnectEventListener implements ApplicationListener<SessionConnectEvent> {

    private Logger LOG = LoggerFactory.getLogger(STOMPConnectEventListener.class);

    @Autowired
    SocketSessionRegistry webAgentSessionRegistry;


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {

        Message msg = event.getMessage();
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(msg);
        String sessionId = sha.getSessionId();
        //login get from browser
      /*  LOG.info("=====getPasscode======" + sha.getPasscode());

        LOG.info("=====getMessage======" + sha.getMessage());

        LOG.info("=====getLogin======" + sha.getLogin());

        LOG.info("=====getAck======" + sha.getAck());*/

        String userNo = sha.getLogin();

        //String passcode = sha.getPasscode();

        //String token = sha.getMessage();

        LOG.info("@新webSocket连接建立 用户信息 userId=" + userNo + "，sessionId=" + sessionId);

        redisTemplate.opsForValue().set(Constant.WEB_SOCKET_USER_NO + userNo, userNo, 30, TimeUnit.SECONDS);

        webAgentSessionRegistry.registerSessionId(userNo, sessionId);
    }
}
