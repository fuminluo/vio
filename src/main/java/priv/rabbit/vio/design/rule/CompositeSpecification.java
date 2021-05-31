package priv.rabbit.vio.design.rule;


public abstract class CompositeSpecification<T> implements IUserSpecification<T> {

    // 是否满足要求由子类去实现
    public abstract boolean isSatisfiedBy(T data) throws NoSuchFieldException, IllegalAccessException;

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
