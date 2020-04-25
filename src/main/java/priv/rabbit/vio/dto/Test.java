package priv.rabbit.vio.dto;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author administered
 * @Description
 * @Date 2020/4/14 1:29
 **/
public class Test {
    public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
        String value = "emp.ename:康康|emp.job:clerk|emp.dept.dname:财务部|emp.dept.loc:良好|emp.dept.company.name:尖商科技|emp.dept.company.address:北京";
        EmpAction action = new EmpAction();
        action.setValue(value);    //传入字符串参数设置并获取相应成员类属性的值
        System.out.println(action.getEmp());
    }
}
