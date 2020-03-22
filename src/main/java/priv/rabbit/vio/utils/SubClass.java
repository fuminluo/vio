package priv.rabbit.vio.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 调用举例
 */
public class SubClass extends CommonManagerProperty{

    /**
     * 无参数构造器
     */
    public SubClass() {
        super();
    }

    /**
     * 含参数的构造器
     * @param propertyMap
     */
    @SuppressWarnings("unchecked")
    public SubClass(Map propertyMap) {
        super(propertyMap);
    }

    public static void main(String args[]){
        //设置欲添加的 属性名称 与 属性类型
        Map map=new HashMap();
        try {
            Class clazz = Class.forName("priv.rabbit.vio.utils.CommonPropertyClass");
            Field[] fields = clazz.getDeclaredFields();//根据Class对象获得属性(含私有)
            for(Field f : fields) {
                map.put(f.getName(),Class.forName(f.getType().getName()));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }


        //测试。。。。。
        SubClass sc=new SubClass(map);

        //设置属性值
        sc.setValue("id", 123);
        sc.setValue("startTime", "2014");
        sc.setValue("endTime", "2015");

        //取出属性值
        System.out.println(" >> id = " + sc.getValue("id"));
        System.out.println(" >> startTime = " + sc.getValue("startTime"));
        System.out.println(" >> endTime = " + sc.getValue("endTime"));

        //获得bean的实体
        Object object = sc.getObject();

        // 通过反射查看所有方法名 获得属性的getter setter
        Class clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            System.out.println(methods[i].getName());
        }

    }

}