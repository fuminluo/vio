package priv.rabbit.vio.design.rule;


import javax.validation.constraints.NotNull;

public class EqualRule extends CompositeSpecification {

    protected Object value;

    public EqualRule(String key, Object value) {
        valid(key, value);
        this.value = value;
        this.key = key;
    }

    @Override
    public boolean isSatisfiedBy(Object data) {
        return value.equals(getTargetValue(data));
    }

    public EqualRule(String key, @NotNull GetRuleValue getRuleValue) {
        this(key, getRuleValue.getValue());
    }
}
