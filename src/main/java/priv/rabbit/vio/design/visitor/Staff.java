package priv.rabbit.vio.design.visitor;

import java.util.Random;

/**
 * @Author LuoFuMin
 * @DATE 2021/2/18 14:47
 */
public abstract class Staff {
    public String name;
    public int kpi;// 员工KPI

    public Staff(String name) {
        this.name = name;
        kpi = new Random().nextInt(10);
    }
    // 核心方法，接受Visitor的访问
    public abstract void accept(Visitor visitor);
}
