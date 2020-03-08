package priv.rabbit.vio.design.single;

/**
 * @Author administered
 * @Description
 * @Date 2019/5/17 22:12
 **/
public class SingleVolatile {
    private static volatile SingleVolatile instance;
    private SingleVolatile() {}

    /**
     * 如果没有volatile关键字，在第5行会出现问题： instance = SingleVolatile();
     * 第一步 1 memory=allocate();// 分配内存 相当于c的malloc
     * 第二步 2 ctorInstanc(memory) //初始化对象
     * 第三步 3 instance=memory //设置instance指向刚分配的地址
     * 上面的代码在编译器运行时，可能会出现重排序 从1-2-3 排序为1-3-2
     * 如此在多线程下就会出现问题
     * 例如现在有2个线程A,B
     * 线程A在执行第5行代码时，B线程进来，而此时A执行了 1和3，没有执行2，此时B线程判断instance不为null 直接返回一个未初始化的对象，就会出现问题
     * 而用了volatile，上面的重排序就会在多线程环境中禁止，不会出现上述问题。
     */
    public static SingleVolatile getInstance() {//1
        if (instance == null) {//2
            synchronized (SingleVolatile.class) {//3
                if (instance == null) {//4
                    instance = new SingleVolatile();//5
                }
            }
        }
        return instance;//6
    }
}
