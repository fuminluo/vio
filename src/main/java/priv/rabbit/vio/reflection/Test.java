package priv.rabbit.vio.reflection;

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

        department.setEmps(Arrays.asList(employee2, employee3));
        employee.setDept(department);

        ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
        ObjectFactory objectFactory = new DefaultObjectFactory();
        ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

        MyMetaObject metaObject = new MyMetaObject(employee,objectFactory,objectWrapperFactory,reflectorFactory);

       //System.out.println("==metaObject==" + metaObject.getValue("dept.emps.email"));
        System.out.println("==metaObject==" + metaObject.getValue("dept.emps"));
    }
}
