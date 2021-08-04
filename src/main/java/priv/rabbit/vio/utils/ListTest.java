package priv.rabbit.vio.utils;

import java.util.ArrayList;
import java.util.List;

public class ListTest {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 100000000; i++) {
            stringList.add(i + "S");
        }
        System.out.println("==结束==" + (System.currentTimeMillis() - start));
    }


}
