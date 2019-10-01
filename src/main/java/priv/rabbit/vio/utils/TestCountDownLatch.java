package priv.rabbit.vio.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;

import java.util.concurrent.CountDownLatch;

/**
 * @Author administered
 * @Description
 * @Date 2019/9/30 22:05
 **/
public class TestCountDownLatch {

    /**
     * 日志
     */
    private static Log LOGGER = LogFactory.getLog(TestCountDownLatch.class);

    static {
        BasicConfigurator.configure();
    }

    public static void main(String[] args) throws Throwable {
        // 同步计数器从5开始计数
        final CountDownLatch countDownLatch = new CountDownLatch(5);

        // 启动子线程，处理“其他”业务
        for(int index = 0 ; index < 5 ; index++) {
            Thread childThread = new Thread() {
                @Override
                public void run() {
                    //等待，以便模型业务处理过程消耗的时间
                    synchronized (this) {
                        try {
                            this.wait(1000);
                        } catch (InterruptedException e) {
                            TestCountDownLatch.LOGGER.error(e.getMessage(), e);
                        }
                    }

                    // 完成业务处理过程，计数器-1
                    long threadid = Thread.currentThread().getId();
                    TestCountDownLatch.LOGGER.info("子线程(" + threadid + ")执行完成！");
                    countDownLatch.countDown();
                }
            };
            childThread.start();
        }

        // 等待所有子线程的业务都处理完成（计数器的count为0时）
        //countDownLatch.await();
        TestCountDownLatch.LOGGER.info("所有子线程的处理都完了，主线程继续执行...");
    }
}
