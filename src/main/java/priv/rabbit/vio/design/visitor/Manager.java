package priv.rabbit.vio.design.visitor;

import java.util.Random;

/**
 * @Author LuoFuMin
 * @DATE 2021/2/18 14:48
 */
// 经理
public class Manager extends Staff {

    public Manager(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    // 一年做的产品数量
    public int getProducts() {
        return new Random().nextInt(10);
    }
}