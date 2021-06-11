package priv.rabbit.vio.design.rule;


import java.util.Collection;

public class InRule extends CompositeSpecification {

    private Collection value;

    public InRule(String key, Collection value) {
        valid(key, value);
        this.value = value;
        this.key = key;
    }

    @Override
    public boolean isSatisfiedBy(Object data) {
        return value.contains(getTargetValue(data));
    }
}
