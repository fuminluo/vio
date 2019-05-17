package priv.rabbit.vio.design.strategy.context;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import priv.rabbit.vio.common.ResultInfo;
import priv.rabbit.vio.design.strategy.payment.Payment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 付款策略模式
 *
 * @Author administered
 * @Description
 * @Date 2019/5/17 21:06
 **/
@Component
public class PayContext {

    private Map<String, Payment> paymentMap = new ConcurrentHashMap<>();


    /**
     * 注入所以实现了Payment接口的Bean
     *
     * @param paymentMap
     */
    @Autowired
    public PayContext(Map<String, Payment> paymentMap) {
        this.paymentMap.clear();
        paymentMap.forEach((k, v) -> this.paymentMap.put(k, v));
    }

    /**
     * 计算价格
     *
     * @param payType 支付方式
     * @param userId  用户id
     * @param amount  金额
     * @return ResultInfo
     */
    public ResultInfo<T> pay(String payType, String userId, double amount) {
        if (paymentMap.containsKey(payType)) {
            return paymentMap.get(payType).pay(userId, amount);
        }
        return ResultInfo.FALL("不存在此付款方式");
    }
}
