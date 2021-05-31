package priv.rabbit.vio.design.rule;


public class UserByNameEqual<T> extends CompositeSpecification<T> {

    private String name;

    public
    UserByNameEqual(String name) {
        this.name = name;
    }

    @Override
    public boolean isSatisfiedBy(T data) {
        // return user.getUsername().equals(name);
        return true;
    }

}
