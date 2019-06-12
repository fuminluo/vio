package priv.rabbit.vio.design.single;

/**
 * @Author administered
 * @Description
 * @Date 2019/5/17 22:12
 **/
public class SingleVolatile {
    private static volatile SingleVolatile instance;
    private SingleVolatile() {}
    public static SingleVolatile getInstance() {
        if (instance == null) {
            synchronized (SingleVolatile.class) {
                if (instance == null) {
                    instance = new SingleVolatile();
                }
            }
        }
        return instance;
    }
}
