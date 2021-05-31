package priv.rabbit.vio.design.rule;


public class AndSpecification<T> extends CompositeSpecification<T> {

    private IUserSpecification left;
    private IUserSpecification right;

    public AndSpecification(IUserSpecification left, IUserSpecification right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isSatisfiedBy(T data) throws NoSuchFieldException, IllegalAccessException {
        return left.isSatisfiedBy(data) && right.isSatisfiedBy(data);
    }
}
