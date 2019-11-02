package priv.rabbit.vio.entity;

import java.util.List;

/**
 * @Author administered
 * @Description
 * @Date 2019/11/1 21:09
 **/
public class Department {

    private Integer id;
    private String departmentName;
    private List<Employee> emps;



    public List<Employee> getEmps() {
        return emps;
    }
    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    @Override
    public String toString() {
        return "Department [id=" + id + ", departmentName=" + departmentName + "]";
    }

}
