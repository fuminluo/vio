package priv.rabbit.vio.service;


/**
 * @Author administered
 * @Description
 * @Date 2019/7/3 23:21
 **/
public interface RedisLockService {

    void tryLock(Long waitTime,  Long lesaeTime, Long sleepTime);
}
