package priv.rabbit.vio.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author LuoFuMin
 * @data 2018/7/26
 */
@RestController
public class RabbitController {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @GetMapping("/hello")
    public void hello() throws Exception {
        String context = "hello";
        rabbitTemplate.convertAndSend("hello", context);

    }

    @GetMapping("/quick.orange.rabbit")
    public void topicMessage() throws Exception {
        String context = "quick.orange.rabbit";
        rabbitTemplate.convertAndSend("exchange", "quick.orange.rabbit", context);
    }

    @GetMapping("/lazy.orange.elephant")
    public void topicMessages() throws Exception {
        String context = "lazy.orange.elephant";
        rabbitTemplate.convertAndSend("exchange", "lazy.orange.elephant", context);
    }

    @GetMapping("/lazy.brown.fox")
    public void lazy_brown_fox() throws Exception {
        String context = "lazy.brown.fox";
        rabbitTemplate.convertAndSend("exchange", "lazy.brown.fox", context);
    }

    @GetMapping("/lazy.pink.rabbit")
    public void lazy_pink_rabbit() throws Exception {
        String context = "lazy.brown.fox";
        rabbitTemplate.convertAndSend("exchange", "lazy.pink.rabbit", context);
    }

    @GetMapping("/quick.brown.fox")
    public void quick_brown_foxt() throws Exception {
        String context = "lazy.brown.fox";
        rabbitTemplate.convertAndSend("exchange", "quick.brown.fox", context);
    }

}
