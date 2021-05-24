package priv.rabbit.vio.design.rule;

import priv.rabbit.vio.entity.User;

public class UserByAgeThan extends CompositeSpecification {

    private int age;

    public UserByAgeThan(int age) {
        this.age = age;
    }

    @Override
    public boolean isSatisfiedBy(User user) {
        return user.getAge() > age;
    }

}
