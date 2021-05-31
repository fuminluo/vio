package priv.rabbit.vio.design.rule;

import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Field;

public class AnyRule extends CompositeSpecification<T> {

    private Object object;

    private String fieldName;

    private String operator;

    public AnyRule(Object object, String fieldName, String operator) {
        this.object = object;
        this.fieldName = fieldName;
        this.operator = operator;
    }

    @Override
    public boolean isSatisfiedBy(T data) throws NoSuchFieldException, IllegalAccessException {
        Field field = data.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Object obj = field.get(data);
        field.getType();
        return object.equals(obj);
    }
}
