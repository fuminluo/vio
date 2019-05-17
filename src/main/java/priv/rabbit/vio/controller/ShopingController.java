package priv.rabbit.vio.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import priv.rabbit.vio.common.ResultInfo;
import priv.rabbit.vio.design.strategy.context.PayContext;

/**
 * @Author administered
 * @Description
 * @Date 2019/5/17 20:57
 **/
@Api(value = "ShiroController", description = "策略模式")
@RestController
public class ShopingController {

    @Autowired
    PayContext payContext;

    /**
     * @描述 付款
     * @参数 [userId, payType, amount]
     * @返回值 priv.rabbit.vio.common.ResultInfo<org.apache.poi.ss.formula.functions.T>
     * @创建人 admin
     * @创建时间 2019/5/17
     */
    @ApiOperation(value = "支付接口", notes = "支付接口")
    @PostMapping(value = "/api/v1/shoping/pay")
    public ResultInfo<T> shoping(@RequestParam String userId, @RequestParam String payType, @RequestParam double amount) {

        return payContext.pay(payType, userId, amount);
    }
}
