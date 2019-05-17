package priv.rabbit.vio.design.strategy.payment.impl;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import priv.rabbit.vio.common.ResultInfo;
import priv.rabbit.vio.design.strategy.payment.Payment;

/**
 * 支付宝支付
 *
 * @Author administered
 * @Description
 * @Date 2019/5/17 20:49
 **/
@Service("aliPay")
public class AliPay implements Payment {
    @Override
    public ResultInfo<T> pay(String uid, double amount) {
        System.out.println("欢迎来到支付宝支付");
        return ResultInfo.OK("支付成功");
    }
}
