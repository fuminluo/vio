package priv.rabbit.vio.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 配置rabbirmq
 *
 * @author LuoFuMin
 * @data 2018/7/27
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue Queue() {
        return new Queue("hello");
    }



    @Bean("exchange")
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    @Bean
    public Queue autoDeleteQueue1() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue autoDeleteQueue2() {
        return new AnonymousQueue();
    }



    @Bean
    public Binding binding1a(TopicExchange topic,
                             Queue autoDeleteQueue1) {
        return BindingBuilder.bind(autoDeleteQueue1)
                .to(topic)
                .with("*.orange.*");
    }

    @Bean
    public Binding binding1b(TopicExchange topic,
                             Queue autoDeleteQueue1) {
        return BindingBuilder.bind(autoDeleteQueue1)
                .to(topic)
                .with("*.*.rabbit");
    }

    @Bean
    public Binding binding2a(TopicExchange topic,
                             Queue autoDeleteQueue2) {
        return BindingBuilder.bind(autoDeleteQueue2)
                .to(topic)
                .with("lazy.#");
    }
}
