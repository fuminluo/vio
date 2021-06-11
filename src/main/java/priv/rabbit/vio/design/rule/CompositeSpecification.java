package priv.rabbit.vio.design.rule;


import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.util.Assert;

public abstract class CompositeSpecification implements ISpecification {

    protected String key;


    // 是否满足要求由子类去实现
    public abstract boolean isSatisfiedBy(Object data);

    @Override
    public ISpecification and(ISpecification spec) {
        return new AndSpecification(this, spec);
    }

    @Override
    public ISpecification or(ISpecification spec) {
        return new OrSpecification(this, spec);
    }

    @Override
    public ISpecification not() {
        return new NotSpecification(this);
    }

    protected Object getTargetValue(Object data) {
        MetaObject metaObject = SystemMetaObject.forObject(data);
        return metaObject.getValue(key);
    }

    protected void valid(String key, Object... value) {
        Assert.notNull(key, "key can not be null");
        int size = value.length;
        for (int i = 0; i < size; i++) {
            Assert.notNull(value[i], String.format("value%d can not be null",i));
        }
    }
}
