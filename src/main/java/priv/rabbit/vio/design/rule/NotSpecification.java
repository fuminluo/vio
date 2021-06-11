package priv.rabbit.vio.design.rule;



public class NotSpecification extends CompositeSpecification {

    private ISpecification spec;

    public NotSpecification(ISpecification specification) {
        this.spec = specification;
    }

    @Override
    public boolean isSatisfiedBy(Object data) {
        return !spec.isSatisfiedBy(data);
    }

}
