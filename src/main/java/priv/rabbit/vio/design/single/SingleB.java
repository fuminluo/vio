package priv.rabbit.vio.design.single;

/**
 * @Author administered
 * @Description
 * @Date 2019/5/17 22:11
 **/
public class SingleB {
    private static final SingleB INSTANCE = new SingleB();
    private SingleB() {}
    public static SingleB getInstance() {
        return INSTANCE;
    }
}
