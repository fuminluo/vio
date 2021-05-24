package priv.rabbit.vio.design.rule;

import priv.rabbit.vio.entity.User;

public abstract class CompositeSpecification implements IUserSpecification {

    // 是否满足要求由子类去实现
    public abstract boolean isSatisfiedBy(User user);

    @Override
    public IUserSpecification and(IUserSpecification spec) {
        return new AndSpecification(this, spec);
    }

    @Override
    public IUserSpecification or(IUserSpecification spec) {
        return new OrSpecification(this, spec);
    }

    @Override
    public IUserSpecification not() {
        return new NotSpecification(this);
    }

}
