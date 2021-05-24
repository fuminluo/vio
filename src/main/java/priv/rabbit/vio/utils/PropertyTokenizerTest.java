package priv.rabbit.vio.utils;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import priv.rabbit.vio.entity.Department;
import priv.rabbit.vio.entity.Employee;
import priv.rabbit.vio.entity.User;

import java.util.Arrays;

public class PropertyTokenizerTest {

    public static void main(String[] args) {

        Employee employee = new Employee();
        Department department = new Department();
        department.setId(1);
        Employee employee2 = new Employee();
        employee2.setEmail("1414");
        Employee employee3 = new Employee();
        employee3.setId(12);

        department.setEmps(Arrays.asList(employee2,employee3));
        employee.setDept(department);

        Configuration configuration = new Configuration();
        MetaObject metaObject = configuration.newMetaObject(employee);

        System.out.println("==metaObject=="+metaObject.getValue("dept.emps[0].email"));
        System.out.println("==metaObject=="+metaObject.getValue("dept.emps"));

    }
}
