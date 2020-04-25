package priv.rabbit.vio.dto;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author administered
 * @Description
 * @Date 2020/4/14 1:28
 **/
public class BeanOperation {
    private BeanOperation() {}
    public static void setBeanValue(Object actionObject,String msg) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
        String[] result = msg.split("\\|");    //不同成员变量间分隔
        for(int x = 0; x < result.length; x ++) {
            String[] temp = result[x].split(":");    //名称和值的分隔
            String attribute = temp[0];    //类名和成员属性名
            String value = temp[1];    //属性值
            String[] fields = attribute.split("\\.");    //级层数组
            int i = fields.length-1;    //最高层级层脚标为成员名
            Object currentObject = actionObject;
            for(int y = 0; y < i; y ++) {
//                通过反射在actionObject类中查找成员fields[y]，返回被实例化的成员类
                currentObject = ObjectUtils.getObject(currentObject,fields[y]);//第一次返回emp
            }
            ObjectUtils.setObjectValue(currentObject, fields[i], value);
//            emp.dept.company    fields[0++]    (actionObject = currentObject,fields[0++])
        }
    }
}
