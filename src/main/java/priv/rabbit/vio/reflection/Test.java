package priv.rabbit.vio.reflection;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import priv.rabbit.vio.entity.Department;
import priv.rabbit.vio.entity.Employee;
import priv.rabbit.vio.entity.User;

import java.util.Arrays;
import java.util.Properties;

public class Test {

    public static void main(String[] args) {
        Employee employee = new Employee();
        Department department = new Department();
        department.setId(1);

        Employee employee2 = new Employee();
        employee2.setEmail("1414");
        Employee employee3 = new Employee();
        employee3.setId(12);
        employee3.setEmail("1415");


        User user1 = new User();
        user1.setNickname("张三丰");
        User user2 = new User();
        user2.setNickname("李丽丽");


        User user3 = new User();
        user3.setNickname("冒泡泡");
        User user4 = new User();
        user4.setNickname("女演员");

        employee2.setUserList(Arrays.asList(user1, user2));

        employee3.setUserList(Arrays.asList(user3, user4));

        department.setEmps(Arrays.asList(employee2, employee3));
        employee.setDept(department);

        ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
        ObjectFactory objectFactory = new DefaultObjectFactory();
        ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

        MyMetaObject metaObject = new MyMetaObject(employee, objectFactory, objectWrapperFactory, reflectorFactory);

        //System.out.println("==metaObject==" + metaObject.getValue("dept.emps.email"));
        System.out.println("==metaObject==" + metaObject.getValue("dept.emps.userList.nickname"));

        System.out.println("==metaObject==" + JSONObject.toJSONString(employee));
    }
}
