package priv.rabbit.vio.design.rule;


public interface ISpecification {

    // 候选者是否满足要求
    boolean isSatisfiedBy(Object data);

    // AND 操作
    public ISpecification and(ISpecification spec);

    // OR 操作
    public ISpecification or(ISpecification spec);

    // NOT 操作
    public ISpecification not();
}
