package priv.rabbit.vio.design.factory;


import priv.rabbit.vio.design.Fruit;

/**
 * 橘子类
 * @Author administered
 * @Description
 * @Date 2018/8/30 0:09
 **/
public class Orange implements Fruit {
    @Override
    public void eat() {
        System.out.println("Orange");
    }
}
