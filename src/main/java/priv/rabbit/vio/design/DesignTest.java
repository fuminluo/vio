package priv.rabbit.vio.design;

import priv.rabbit.vio.design.template.*;

/**
 * @Author administered
 * @Description
 * @Date 2019/5/18 14:09
 **/
public class DesignTest {
    public static void main(String[] args) {
        Template eatNoodle = new NoodleShop();
        eatNoodle.process();

        Template eatBarbecue = new BarbecueShop();
        eatBarbecue.process();

        HouseTemplate houseOne = new HouseOne("房子1");
        HouseTemplate houseTwo = new HouseTwo("房子2");
        houseOne.buildHouse();
        houseTwo.buildHouse();

    }
}
