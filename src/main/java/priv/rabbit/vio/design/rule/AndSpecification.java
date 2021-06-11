package priv.rabbit.vio.design.rule;



public class AndSpecification extends CompositeSpecification {

    private ISpecification left;
    private ISpecification right;

    public AndSpecification(ISpecification left, ISpecification right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isSatisfiedBy(Object data) {
        return left.isSatisfiedBy(data) && right.isSatisfiedBy(data);
    }
}
