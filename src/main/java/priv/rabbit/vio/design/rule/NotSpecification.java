package priv.rabbit.vio.design.rule;


public class NotSpecification<T> extends CompositeSpecification<T> {

    private IUserSpecification spec;

    public NotSpecification(IUserSpecification specification) {
        this.spec = specification;
    }

    @Override
    public boolean isSatisfiedBy(T data) throws NoSuchFieldException, IllegalAccessException {
        return !spec.isSatisfiedBy(data);
    }

}
