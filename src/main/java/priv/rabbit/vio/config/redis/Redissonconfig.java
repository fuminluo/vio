package priv.rabbit.vio.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.redisson.config.Config;

/**
 * @Author administered
 * @Description
 * @Date 2019/7/3 21:34
 **/
//@Configuration
public class Redissonconfig {

    @Value("${spring.redis.host}")
    private String addressUrl;

    @Bean
    public RedissonClient redissonClient() throws Exception{
        RedissonClient redisson = null;
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://"+addressUrl+":6379");
        redisson = Redisson.create(config);
        System.out.println(redisson.getConfig().toJSON().toString());
        return redisson;
    }
}

