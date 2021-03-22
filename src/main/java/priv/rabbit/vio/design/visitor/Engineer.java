package priv.rabbit.vio.design.visitor;

import java.util.Random;

/**
 * @Author LuoFuMin
 * @DATE 2021/2/18 14:48
 */
// 工程师
public class Engineer extends Staff {

    public Engineer(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    // 工程师一年的代码数量
    public int getCodeLines() {
        return new Random().nextInt(10 * 10000);
    }
}