package priv.rabbit.vio.design.template;

/**
 * @Author administered
 * @Description
 * @Date 2019/5/18 14:46
 **/
public class HouseTwo extends HouseTemplate {

    public HouseTwo(String name) {
        super(name);
    }

    @Override
    protected void buildDoor() {
        System.out.println(name + "的门采用木门");
    }

    @Override
    protected void buildWindow() {
        System.out.println(name + "的窗户要向南");
    }

    @Override
    protected void buildWall() {
        System.out.println(name + "的墙使用玻璃制造");
    }

    @Override
    protected void buildBase() {
        System.out.println(name + "的地基使用花岗岩");
    }
}
