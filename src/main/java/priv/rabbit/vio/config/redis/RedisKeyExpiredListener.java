package priv.rabbit.vio.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import priv.rabbit.vio.utils.TestCountDownLatch;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class RedisKeyExpiredListener {
    private static Logger LOG = LoggerFactory.getLogger(RedisKeyExpiredListener.class);

    @Autowired
    RedisTemplate redisTemplate;

    private CountDownLatch latch;

    private DefaultRedisScript<Boolean> lockScript;

    @Autowired
    public RedisKeyExpiredListener(CountDownLatch latch) {
        this.latch = latch;
        this.lockScript = new DefaultRedisScript<Boolean>();
        lockScript.setScriptSource(
                new ResourceScriptSource(new ClassPathResource("redis/redisLock.lua")));
        lockScript.setResultType(Boolean.class);
    }

    public void receiveMessage(String redisKey) throws InterruptedException {
        LocalDateTime beginTime = LocalDateTime.now();
        LOG.info(beginTime + "::" + "redis-key::" + redisKey);
        //加锁键
        String redisLockkey = "lock-" + redisKey;
        // 封装参数
        List<Object> keyList = new ArrayList<Object>();
        // lockKey
        keyList.add(redisLockkey);
        // lockValue
        keyList.add(redisKey);
        // 过期时间 lockTime
        keyList.add("10");
        Boolean result = (Boolean) redisTemplate.execute(lockScript, keyList);
        LOG.info("》》》 {} 获取锁结果 ： {}", redisLockkey, result);
        try {
            // 尝试获取锁，等待5秒，自己获得锁后一直不解锁则10秒后自动解锁
            if (result) {
                LOG.info("线程:" + Thread.currentThread().getName() + ",获取到了锁");
                synchronized (RedisKeyExpiredListener.class) {
                    Thread childThread = new Thread();
                    Thread.sleep(3000);
                    childThread.start();
                }
            }
        } catch (Exception e) {
            LOG.error(">> 出现异常:{}", e);
            // TODO 写异常表记录
        } finally {
            if (result) {
                boolean delete = redisTemplate.delete(redisLockkey);
                LOG.info(">> finally 执行完成删除 {} 锁 : {}", redisLockkey, delete);
            }
            //TODO 判断异常表有无记录
            LocalDateTime endTine = LocalDateTime.now();
            double timeConsuming = (double)Duration.between(beginTime, endTine).toMillis() / 1000;
            LOG.info(">> beginTime : {} , endTine : {} , timeConsuming : {} s", beginTime, endTine, timeConsuming);
        }
    }
}
