package priv.rabbit.vio.design.rule;



public class OrSpecification extends CompositeSpecification {

    private ISpecification left;
    private ISpecification right;

    public OrSpecification(ISpecification left, ISpecification right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isSatisfiedBy(Object data){
        return left.isSatisfiedBy(data) || right.isSatisfiedBy(data);
    }


}
