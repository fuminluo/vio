package priv.rabbit.vio.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 消息发送服务
 *
 * @author LuoFuMin
 * @data 2018/7/27
 */
@Component
public class MQSenderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RabbitTemplate rabbitTemplate;


}
