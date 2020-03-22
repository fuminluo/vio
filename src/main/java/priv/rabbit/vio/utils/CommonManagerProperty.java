package priv.rabbit.vio.utils;

import org.assertj.core.internal.cglib.beans.BeanGenerator;
import org.assertj.core.internal.cglib.beans.BeanMap;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Author administered
 * @Description
 * @Date 2020/2/28 20:16
 **/
public class CommonManagerProperty {

    /**
     * 实体Object
     */
    private Object object = null;

    /**
     * 属性map
     */
    private BeanMap beanMap = null;

    /**
     * 空的构造方法
     */
    public CommonManagerProperty(){

    }

    /**
     * 有参数构造方法
     * @param propertyMap 属性名称与类型集合
     */
    @SuppressWarnings("unchecked")
    public CommonManagerProperty(Map propertyMap){
        this.object = generateBean(propertyMap);
        this.beanMap = BeanMap.create(this.object);
    }

    /**
     * 给bean属性赋值
     * @param property 属性名
     * @param value 值
     */
    public void setValue(String property, Object value) {
        beanMap.put(property, value);
    }

    /**
     * 通过属性名得到属性值
     * @param property 属性名
     * @return 值
     */
    public Object getValue(String property) {
        return beanMap.get(property);
    }

    /**
     * 得到该实体bean对象
     * @return
     */
    public Object getObject() {
        return this.object;
    }

    /**
     * 迭代属性名称与类型并设置入对象
     * @param propertyMap
     * @return
     */
    @SuppressWarnings("unchecked")
    private Object generateBean(Map propertyMap) {
        BeanGenerator generator = new BeanGenerator();
        Set keySet = propertyMap.keySet();
        for (Iterator i = keySet.iterator(); i.hasNext();) {
            String key = (String) i.next();
            generator.addProperty(key, (Class) propertyMap.get(key));
        }
        return generator.create();
    }
}
