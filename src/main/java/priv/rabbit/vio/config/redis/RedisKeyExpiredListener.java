package priv.rabbit.vio.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.concurrent.CountDownLatch;


public class RedisKeyExpiredListener {
    private static Logger LOG = LoggerFactory.getLogger(RedisKeyExpiredListener.class);

    private CountDownLatch latch;

    @Autowired
    public RedisKeyExpiredListener(CountDownLatch latch) {
        this.latch = latch;
    }

    public void receiveMessage(String message) {
        LOG.info(new Date() + "::" + "message::" + message);
        latch.countDown();
    }

}
