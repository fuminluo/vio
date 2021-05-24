package priv.rabbit.vio.design.rule;

import priv.rabbit.vio.entity.User;

public class NotSpecification  extends CompositeSpecification {

    private IUserSpecification spec;

    public NotSpecification(IUserSpecification specification) {
        this.spec = specification;
    }

    @Override
    public boolean isSatisfiedBy(User user) {
        return !spec.isSatisfiedBy(user);
    }

}
