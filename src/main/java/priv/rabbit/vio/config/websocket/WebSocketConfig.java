package priv.rabbit.vio.config.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static priv.rabbit.vio.common.Constant.*;

/**
 * socket核心配置容器
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    /**
     * 配置信息代理
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        //服务端发送消息给客户端的域,多个用逗号隔开
        config.enableSimpleBroker(TOPIC_BASE_PATH, P2P_PUSH_BASE_PATH);
        //定义一对一推送的时候前缀
        config.setUserDestinationPrefix(P2P_PUSH_BASE_PATH);
        //定义websoket前缀
        config.setApplicationDestinationPrefixes(WEBSOCKET_PATH_PERFIX);
    }

    /**
     * 注册stomp的端点
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //注册一个Stomp的节点（endpoint）,并指定使用SockJS协议。
        registry.addEndpoint(STOMP_PATH).setAllowedOrigins("*").withSockJS().setHeartbeatTime(2000);

    }
    @Bean
    public SocketSessionRegistry SocketSessionRegistry() {
        return new SocketSessionRegistry();
    }
    @Bean
    public STOMPConnectEventListener STOMPConnectEventListener() {
        return new STOMPConnectEventListener();
    }
}