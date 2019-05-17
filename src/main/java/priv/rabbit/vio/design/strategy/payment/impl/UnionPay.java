package priv.rabbit.vio.design.strategy.payment.impl;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import priv.rabbit.vio.common.ResultInfo;
import priv.rabbit.vio.design.strategy.payment.Payment;

/**
 * 银联闪付
 *
 * @Author administered
 * @Description
 * @Date 2019/5/17 20:53
 **/
@Service("unionPay")
public class UnionPay implements Payment {
    @Override
    public ResultInfo<T> pay(String uid, double amount) {
        System.out.println("欢迎来到银联闪付");
        return ResultInfo.OK("银联闪付成功");
    }
}