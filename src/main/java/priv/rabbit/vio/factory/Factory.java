package priv.rabbit.vio.factory;

/**
 * 反射工厂
 *
 * @Author administered
 * @Description
 * @Date 2018/8/30 0:12
 **/
public class Factory {
    public static Fruit getInstance(String ClassName) {
        Fruit fruit = null;
        try {
            fruit = (Fruit) Class.forName(ClassName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fruit;
    }

    public static void main(String[] args) {
        Fruit f = Factory.getInstance("priv.rabbit.vio.factory.Apple");
        if (f != null) {
            f.eat();
        }
    }
}
