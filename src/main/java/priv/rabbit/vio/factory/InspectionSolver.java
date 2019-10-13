package priv.rabbit.vio.factory;

/**
 * @Author administered
 * @Description
 * @Date 2019/10/13 20:29
 **/
public abstract class InspectionSolver {

    public abstract void solve(Long orderId, Long userId);

    public abstract String[] supports();
}
