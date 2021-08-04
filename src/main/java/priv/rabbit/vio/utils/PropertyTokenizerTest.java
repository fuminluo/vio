package priv.rabbit.vio.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import priv.rabbit.vio.entity.Department;
import priv.rabbit.vio.entity.Employee;
import priv.rabbit.vio.entity.User;
import springfox.documentation.spring.web.json.Json;

import java.util.Arrays;

public class PropertyTokenizerTest {

    public static void main(String[] args) {

        Employee employee = new Employee();
        employee.setLastName("数据库");

        Department department = new Department();
        department.setId(1);
        department.setDepartmentName("马化腾");

        User user1 = new User();
        user1.setNickname("张三丰");
        User user2 = new User();
        user2.setUsername("张丽丽");

        department.setUserList(Arrays.asList(user1,user2));

        employee.setDept(department);

        MetaObject metaObject = SystemMetaObject.forObject(employee);

       System.out.println("==metaObject==" + JSONObject.toJSONString(metaObject.getValue("dept.userList[0].nickname")));

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
