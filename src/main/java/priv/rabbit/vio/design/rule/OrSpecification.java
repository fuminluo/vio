package priv.rabbit.vio.design.rule;

import priv.rabbit.vio.entity.User;

public class OrSpecification extends CompositeSpecification {

    private IUserSpecification left;
    private IUserSpecification right;

    public OrSpecification(IUserSpecification left, IUserSpecification right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isSatisfiedBy(User user) {
        return left.isSatisfiedBy(user) || right.isSatisfiedBy(user);
    }


}
