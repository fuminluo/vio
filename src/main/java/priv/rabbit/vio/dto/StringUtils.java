package priv.rabbit.vio.dto;

/**
 * @Author administered
 * @Description
 * @Date 2020/4/14 1:29
 **/
public class StringUtils {
    private StringUtils() {}
    public static String initcap(String str) {
        return str.substring(0,1).toUpperCase() + str.substring(1);    //首字母大写+剩余字符串
    }

}
