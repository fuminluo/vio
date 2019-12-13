package priv.rabbit.vio.mapper;

import priv.rabbit.vio.entity.Department;
import priv.rabbit.vio.entity.Employee;

import java.util.List;

/**
 * @Author administered
 * @Description
 * @Date 2019/11/1 21:09
 **/
public interface EmployeeMapper {
     Employee getEmpAndDept(Integer id);

     List<Employee>  getEmpAndDepts(Integer id);

     Department getDeptByIdPlus(Integer id);

}
