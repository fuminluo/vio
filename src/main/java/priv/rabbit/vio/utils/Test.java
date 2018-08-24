package priv.rabbit.vio.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author LuoFuMin
 * @data 2018/8/16
 */
public class Test {
    public static int count = 0;
    public static int clientTotal = 5000;

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    add();
                } catch (Exception e) {
                    System.out.println("exception"+ e);
                }
            });
        }

        System.out.println("count=="+count);
    }

    private static void add() {
        count++;
    }


}
