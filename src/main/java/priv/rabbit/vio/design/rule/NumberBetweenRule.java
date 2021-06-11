package priv.rabbit.vio.design.rule;

import com.alibaba.druid.util.StringUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

public class NumberBetweenRule extends CompositeSpecification {

    private Object minValue;

    private Object maxValue;

    public NumberBetweenRule(String key, Object minValue, Object maxValue) {
        valid(key, minValue, maxValue);
        this.key = key;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public boolean isSatisfiedBy(Object data) {
        Object obj = getTargetValue(data);
        if (null == obj) {
            return false;
        }
        String target = obj.toString();
        if (!StringUtils.isNumber(target)) {
            throw new RuntimeException(key + " 非数字");
        }
        BigDecimal bigDecimal = new BigDecimal(target);
        if (bigDecimal.compareTo(new BigDecimal(maxValue.toString())) <= 0 && (bigDecimal.compareTo(new BigDecimal(minValue.toString())) >= 0)) {
            return true;
        }
        return false;
    }
}
