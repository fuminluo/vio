package priv.rabbit.vio;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.CountDownLatch;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import priv.rabbit.vio.config.RedisKeyExpiredListener;

@SpringBootApplication
@MapperScan(basePackages = "priv.**.mapper")
public class VioApplication {


    @Bean
    CountDownLatch latch() {
        return new CountDownLatch(1);
    }

    /*
  * Redis消息监听器容器
  * 这个容器加载了RedisConnectionFactory和消息监听器
  */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("__keyevent@0__:expired"));
        return container;
    }

    /*
     * RedisKeyExpiredListener实例
     */
    @Bean
    RedisKeyExpiredListener receiver(CountDownLatch latch) {
        return new RedisKeyExpiredListener(latch);
    }
    @Bean
    MessageListenerAdapter listenerAdapter(RedisKeyExpiredListener redisKeyExpiredListener) {
        return new MessageListenerAdapter(redisKeyExpiredListener, "receiveMessage");
    }

    public static void main(String[] args) {
        SpringApplication.run(VioApplication.class, args);
    }
}
