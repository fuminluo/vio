package priv.rabbit.vio.design.single;

/**
 * @Author administered
 * @Description
 * @Date 2019/5/17 22:13
 **/
public class SingleDoubleCheck {
    private static SingleDoubleCheck instance;
    private SingleDoubleCheck() {}
    public static SingleDoubleCheck getInstance() {
        if (instance == null) {
            synchronized (SingleDoubleCheck.class) {
                if (instance == null) {
                    instance = new SingleDoubleCheck();
                }
            }
        }
        return instance;
    }
}
