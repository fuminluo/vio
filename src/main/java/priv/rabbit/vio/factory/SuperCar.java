package priv.rabbit.vio.factory;

/**
 * @Author administered
 * @Description
 * @Date 2019/3/15 21:32
 **/
public class SuperCar extends Car{
    public void start()
    {
        System.out.println("车在飞");
    }

    public static void main(String[] args) {
        Car car=new SuperCar();
        car.start();

        String s = " hello";

        String s2  = " hell" + new String("o");

        System.out.println(s==s2);
    }
}
