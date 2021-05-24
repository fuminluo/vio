package priv.rabbit.vio.design.rule;

import priv.rabbit.vio.entity.User;

public class UserByNameEqual extends CompositeSpecification {

    private String name;

    public UserByNameEqual(String name) {
        this.name = name;
    }

    @Override
    public boolean isSatisfiedBy(User user) {
        return user.getUsername().equals(name);
    }

}
