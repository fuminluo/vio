package priv.rabbit.vio.design.template;

/**
 * 抽象模板角色
 *
 * @Author administered
 * @Description
 * @Date 2019/5/18 14:04
 **/
public abstract class Template {
    public void oreder() {
        System.out.println("templateTest  先下订单");
    }

    abstract void eatFood();

    public void payMoney() {
        System.out.println("templateTest   吃完付钱");
    }

    //模板方法，final修饰的方法，不可以被子类重写
    public final void process() {
        this.oreder();
        this.eatFood();
        this.payMoney();
    }

}
