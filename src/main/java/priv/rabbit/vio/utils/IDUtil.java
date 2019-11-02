package priv.rabbit.vio.utils;

import java.util.Random;

/**
 * @author LuoFuMin
 * @data 2018/7/16
 */
public class IDUtil {


    /**
     * @描述 生成随机数
     * @参数 [firstNumber：首位数字, length：随机数长度]
     * @返回值 java.lang.String
     * @创建人 LuoFuMIn
     * @创建时间 2018/7/16
     */
    public static String createID(int firstNumber, int length) {

        StringBuilder str = new StringBuilder();//定义变长字符串
        Random random = new Random();
        //随机生成数字，并添加到字符串
        for (int i = 0; i < length; i++) {
            str.append(random.nextInt(10));
        }
        return firstNumber + str.toString();
    }

    public static void main(String[] args) {
        System.out.println("=====" + "multipart/form-data;".indexOf("multipart/form-data"));

    }
}
