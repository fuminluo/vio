package priv.rabbit.vio.design.template;

/**
 * @Author administered
 * @Description
 * @Date 2019/5/18 14:45
 **/
public class HouseOne extends HouseTemplate{


    public HouseOne(String name) {
        super(name);
    }

    @Override
    protected void buildDoor() {
        System.out.println(name +"的门要采用防盗门");
    }

    @Override
    protected void buildWindow() {
        System.out.println(name + "的窗户要面向北方");
    }

    @Override
    protected void buildWall() {
        System.out.println(name + "的墙使用大理石建造");
    }

    @Override
    protected void buildBase() {
        System.out.println(name + "的地基使用钢铁地基");
    }
}
