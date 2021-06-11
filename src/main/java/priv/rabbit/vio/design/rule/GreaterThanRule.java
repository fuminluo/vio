package priv.rabbit.vio.design.rule;

import com.alibaba.druid.util.StringUtils;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * 大于
 */
public class GreaterThanRule extends CompositeSpecification {

    protected Number value;

    public GreaterThanRule(String key, Number value) {
        valid(key, value);
        this.value = value;
        this.key = key;
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
        return new BigDecimal(target).compareTo(new BigDecimal(value.toString())) > 0;
    }
}
