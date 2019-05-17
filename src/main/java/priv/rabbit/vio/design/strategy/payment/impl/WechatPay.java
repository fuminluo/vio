package priv.rabbit.vio.design.strategy.payment.impl;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import priv.rabbit.vio.common.ResultInfo;
import priv.rabbit.vio.design.strategy.payment.Payment;

/**
 * 微信支付
 *
 * @Author administered
 * @Description
 * @Date 2019/5/17 20:55
 **/
@Service("wechatPay")
public class WechatPay implements Payment {
    @Override
    public ResultInfo<T> pay(String uid, double amount) {
        System.out.println("欢迎来到微信支付");
        return ResultInfo.OK("微信支付闪付成功");
    }
}
