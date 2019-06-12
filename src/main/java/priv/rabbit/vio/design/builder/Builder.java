package priv.rabbit.vio.design.builder;

/**
 * @Author administered
 * @Description
 * @Date 2019/5/17 22:16
 **/
public interface Builder {
    public void buildCPU(); //CUP
    public void buildMemory(); //内存
    public void buildDisplayCard(); //显卡
    public Product getFinalResult();//最终产品
}
