package priv.rabbit.vio.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Author administered
 * @Description
 * @Date 2019/1/26 17:34
 **/
public class GCUtil {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("---- test reduce ----");
        Optional<Integer> sum =
                list
                        .stream()
                        .reduce((a, b) -> a + b);
        sum.ifPresent(System.out::println);

        System.out.println("Xmx=" + Runtime.getRuntime().maxMemory() / 1024.0 / 1024 + "M");     //系统的最大空间

        System.out.println("free mem=" + Runtime.getRuntime().freeMemory() / 1024.0 / 1024 + "M");   //系统的空闲空间

        System.out.println("total mem=" + Runtime.getRuntime().totalMemory() / 1024.0 / 1024 + "M");   //当前可用的总空间

        for (int i = 0; i < 1000; i++) {
            byte[] b = new byte[1 * 1024 * 1024];
        }


    }
}
