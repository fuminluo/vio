package priv.rabbit.vio.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

        employee.setLastName("数据库");

        Department department = new Department();
        department.setId(1);
        department.setDepartmentName("马化腾");

        Employee employee2 = new Employee();
        employee2.setEmail("1414");
        Employee employee3 = new Employee();
        employee3.setId(12);

        department.setEmps(Arrays.asList(employee2, employee3));

        employee.setDept(department);


        Configuration configuration = new Configuration();
        MetaObject metaObject = configuration.newMetaObject(employee);

        System.out.println("==metaObject==" + metaObject.getValue("dept.departmentName"));

      /*  System.out.println("==metaObject==" + metaObject.getValue("dept.emps[0].email"));
        System.out.println("==metaObject==" + metaObject.getValue("dept.emps"));

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("abc", "1");

        JSONArray jsonArray = new JSONArray();
        JSONObject js = new JSONObject();
        js.put("js", "马尔代夫");
        jsonArray.add(js);
        jsonObject.put("list", jsonArray);

        MetaObject json = configuration.newMetaObject(jsonObject);

        System.out.println("==json==" + json.getValue("list[0].js"));*/


    }
}
