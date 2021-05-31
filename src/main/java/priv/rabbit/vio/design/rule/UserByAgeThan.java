package priv.rabbit.vio.design.rule;


public class UserByAgeThan<T> extends CompositeSpecification<T> {

    private int age;

    public UserByAgeThan(int age) {
        this.age = age;
    }

    @Override
    public boolean isSatisfiedBy(T data) {
        // return user.getAge() > age;
        return true;
    }

}
