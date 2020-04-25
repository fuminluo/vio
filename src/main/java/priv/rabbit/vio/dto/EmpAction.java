package priv.rabbit.vio.dto;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author administered
 * @Description
 * @Date 2020/4/14 1:28
 **/
public class EmpAction {
    Emp emp = new Emp();    //实例化成员
    //    设置成员属性
    public void setValue(String value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
        BeanOperation.setBeanValue(this,value);
    }
    public Emp getEmp() {
        return emp;
    }

}
