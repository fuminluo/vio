package priv.rabbit.vio.design.single;

/**
 * @Author administered
 * @Description
 * @Date 2019/5/17 22:10
 **/
public class Single1 {
    private static Single1 instance;
    private Single1() {}
    public static Single1 getInstance() {
        if (instance == null) {
            instance = new Single1();
        }
        return instance;
    }
}
