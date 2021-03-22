package priv.rabbit.vio.design.visitor;

/**
 * @Author LuoFuMin
 * @DATE 2021/2/18 12:28
 */
public interface Element {
    /**
     * 接收一个抽象访问者访问
     */
    void accept(Visitor visitor);

}
