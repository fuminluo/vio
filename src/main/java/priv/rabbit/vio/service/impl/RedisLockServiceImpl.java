package priv.rabbit.vio.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import priv.rabbit.vio.config.redis.DistributedLocker;
import priv.rabbit.vio.service.RedisLockService;

import java.util.concurrent.TimeUnit;

/**
 * @Author administered
 * @Description
 * @Date 2019/7/3 23:22
 **/
@Service
public class RedisLockServiceImpl implements RedisLockService {

    private static Logger LOG = LoggerFactory.getLogger(RedisLockServiceImpl.class);


    @Autowired
    private DistributedLocker distributedLocker;

    @Async
    @Override
    public void tryLock(Long waitTime, Long lesaeTime, Long sleepTime) {
        LOG.info("waitTime : "+waitTime+"   lesaeTime : "+ lesaeTime+"   sleepTime: "+sleepTime);
        String key = "redisson_key1";
        boolean isGetLock = false;
        try {
                        /*
                         * distributedLocker.lock(key,10L); //直接加锁，获取不到锁则一直等待获取锁
						 * Thread.sleep(100); //获得锁之后可以进行相应的处理
						 * System.err.println("======获得锁后进行相应的操作======"+Thread.
						 * currentThread().getName());
						 * distributedLocker.unlock(key); //解锁
						 * System.err.println("============================="+
						 * Thread.currentThread().getName());
						 */
            // 尝试获取锁，等待5秒，自己获得锁后一直不解锁则10秒后自动解锁
            isGetLock = distributedLocker.tryLock(key, TimeUnit.SECONDS, waitTime, lesaeTime);
           // isGetLock = distributedLocker.lock(key).isLocked();
            LOG.info("=========isGetLock=========" + isGetLock);
            if (isGetLock) {
                LOG.info("线程:" + Thread.currentThread().getName() + ",获取到了锁");
                Thread.sleep(sleepTime); // 获得锁之后可以进行相应的处理
                distributedLocker.unlock(key);
                LOG.info("=======sleepTime=======任务结束");
            }
        } catch (Exception e) {
            LOG.error("出现异常:{}", e);
            LOG.error("出现异常:{}", e.getMessage());
            distributedLocker.unlock(key);
        }
        LOG.info("=======isGetLock=======isGetLock");
    }
}
