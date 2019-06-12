package priv.rabbit.vio.design.builder;

/**
 * @Author administered
 * @Description
 * @Date 2019/5/17 22:18
 **/
public class Director {
    private Builder builder;
    public Director(Builder builder) {
        this.builder = builder;
    }
    public void construct() {
        builder.buildCPU();
        builder.buildMemory();
        builder.buildDisplayCard();
    }
}
