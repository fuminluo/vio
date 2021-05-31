package priv.rabbit.vio.design.rule;


public interface IUserSpecification<T> {
    // 候选者是否满足要求
    boolean isSatisfiedBy(T data) throws NoSuchFieldException, IllegalAccessException;

    // AND 操作
    public IUserSpecification and(IUserSpecification spec);

    // OR 操作
    public IUserSpecification or(IUserSpecification spec);

    // NOT 操作
    public IUserSpecification not();
}
