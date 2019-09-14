package priv.rabbit.vio.config.redis;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @Author administered
 * @Description
 * @Date 2019/7/3 21:32
 **/
public interface DistributedLocker {

    RLock lock(String lockKey);

    RLock lock(String lockKey, long timeout);

    RLock lock(String lockKey, TimeUnit unit, long timeout);

    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);

}
