package priv.rabbit.vio.controller;

import priv.rabbit.vio.factory.*;

import java.util.HashMap;

public class EcharController {

    public void init() {
        EchartsFactory echartsFactory = DefaultEchartsFactory.getInstance();

        AbstractBarSimple createBarSimple = echartsFactory.createBarSimple(new HashMap<>(), BarSimpleImpl.class);
        createBarSimple.initEchartsData();

        AbstractLineSimple createLineSimple = echartsFactory.createLineSimple(new HashMap<>(), LineSimpleImpl.class);
        createLineSimple.initEchartsData();
    }
}
