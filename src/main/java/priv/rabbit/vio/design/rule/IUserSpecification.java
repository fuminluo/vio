package priv.rabbit.vio.design.rule;

import priv.rabbit.vio.entity.User;

public interface IUserSpecification {
    // 候选者是否满足要求
    boolean isSatisfiedBy(User user);

    // AND 操作
    public IUserSpecification and(IUserSpecification spec);

    // OR 操作
    public IUserSpecification or(IUserSpecification spec);

    // NOT 操作
    public IUserSpecification not();
}
