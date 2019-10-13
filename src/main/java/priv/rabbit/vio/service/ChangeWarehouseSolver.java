package priv.rabbit.vio.service;

import org.springframework.stereotype.Component;
import priv.rabbit.vio.factory.InspectionSolver;

/**
 * @Author administered
 * @Description
 * @Date 2019/10/13 20:30
 **/
@Component
public class ChangeWarehouseSolver extends InspectionSolver {

    @Override
    public void solve(Long orderId, Long userId) {
        System.out.println("订单" + orderId + "开始进行批量转仓了。。");
    }

    @Override
    public String[] supports() {
        return new String[]{"abc","ABCD"};
    }
}



