package priv.rabbit.vio.design.strategy.payment;

import org.apache.poi.ss.formula.functions.T;
import priv.rabbit.vio.common.ResultInfo;

/**
 * 支付方式的接口
 *
 * @Author administered
 * @Description
 * @Date 2019/5/17 20:47
 **/
public interface Payment {
    /**
     *
     * @param uid   表示人的uid
     * @param amount  表示支付的金额
     * @return
     */
    ResultInfo<T> pay(String uid, double amount);
}
