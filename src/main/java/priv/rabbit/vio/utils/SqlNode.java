package priv.rabbit.vio.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 抽象语法树 AST
 */
public class SqlNode {

    public static void main(String[] args) {

        try {
            long start = System.currentTimeMillis();
            Writer w = new FileWriter("D:/NewReadMe.xml");
            BufferedWriter buffWriter = new BufferedWriter(w);

            List<String> list = new ArrayList<>();

            StringBuilder stringBuilder = new StringBuilder(1000 * 999);

            for (int i = 0; i < 90000; i++) {
                stringBuilder.append(UUID.randomUUID().toString()).append("\n");
                System.out.println("=======" + i);
                list.add(stringBuilder.toString());
                if (i % 1000 == 0) {
                    buffWriter.write(stringBuilder.toString());
                    stringBuilder = null;
                    stringBuilder = new StringBuilder(1000 * 999);
                }
            }
            buffWriter.write(stringBuilder.toString());
            buffWriter.close();
            w.close();

            System.out.println("写入成功！ 耗时 ：" + (System.currentTimeMillis() - start) + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List list = new ArrayList();


    }
}
