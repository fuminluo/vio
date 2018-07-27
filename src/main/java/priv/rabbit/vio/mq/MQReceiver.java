package priv.rabbit.vio.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * 消息监听器
 *
 * @author LuoFuMin
 * @data 2018/7/27
 */
@Component
public class MQReceiver {
    private static final Logger log = LoggerFactory.getLogger(MQReceiver.class);


    @RabbitHandler
    @RabbitListener(queues = "hello")
    public void process(String context) {
        System.out.println("接收 hello : " + context);
    }

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive1(String context) throws InterruptedException {
        System.out.println("receive1 接收  :" + context);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receive2(String context) throws InterruptedException {
        System.out.println("receive2 接收  :" + context);
    }


}
