package priv.rabbit.vio.factory;

/**
 * @Author administered
 * @Description
 * @Date 2019/3/15 22:48
 **/
public class HelloA {

    public HelloA(){
        System.out.println("HelloA");
    }

    {
        System.out.println("I am HelloA");
    }

    static {
        System.out.println("I static HelloA");
    }
}
