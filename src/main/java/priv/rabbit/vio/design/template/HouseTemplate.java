package priv.rabbit.vio.design.template;

/**
 * @Author administered
 * @Description
 * @Date 2019/5/18 14:43
 **/
public abstract class HouseTemplate {
    protected HouseTemplate(String name) {
        this.name = name;
    }

    protected String name;

    protected abstract void buildDoor();

    protected abstract void buildWindow();

    protected abstract void buildWall();

    protected abstract void buildBase();

    //公共逻辑
    public final void buildHouse() {

        buildBase();
        buildWall();
        buildDoor();
        buildWindow();
    }

}
