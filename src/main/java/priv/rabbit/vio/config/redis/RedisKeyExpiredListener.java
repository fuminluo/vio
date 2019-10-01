package priv.rabbit.vio.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import priv.rabbit.vio.utils.TestCountDownLatch;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class RedisKeyExpiredListener {
    private static Logger LOG = LoggerFactory.getLogger(RedisKeyExpiredListener.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private CountDownLatch latch;

    @Autowired
    public RedisKeyExpiredListener(CountDownLatch latch) {
        this.latch = latch;
    }

    public void receiveMessage(String message) throws InterruptedException {
        LOG.info(new Date() + "::" + "redis-key::" + message);
        String lock = "lock-" + message;
        boolean isGetLock = stringRedisTemplate.delete(lock);
        try {
            // 尝试获取锁，等待5秒，自己获得锁后一直不解锁则10秒后自动解锁
            LOG.info("=========isGetLock=========" + isGetLock + "   message : " + message);
            if (isGetLock) {
                LOG.info("线程:" + Thread.currentThread().getName() + ",获取到了锁");
                synchronized (RedisKeyExpiredListener.class) {
                    Thread childThread = new Thread();
                    Thread.sleep(3000);
                    childThread.start();
                    LOG.info("执行完成:" + message);
                }
            }
        } catch (Exception e) {
            LOG.error("出现异常:{}", e.getStackTrace());
        }


    }

}
