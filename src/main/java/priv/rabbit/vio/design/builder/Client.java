package priv.rabbit.vio.design.builder;

/**
 * @Author administered
 * @Description
 * @Date 2019/5/17 22:18
 **/
public class Client {
    private static Builder acerBuilder = new AcerBuilder();
    private static  Builder dellBuilder = new DellBuilder();
    public static void main(String[] args) {

        System.out.print("宏基");
        Director director = new Director(acerBuilder);
        director.construct();
        Product product = acerBuilder.getFinalResult();
        product.show();

        System.out.println();

        System.out.print("戴尔");
        director = new Director(dellBuilder);
        director.construct();
        product = dellBuilder.getFinalResult();
        product.show();
    }
}
