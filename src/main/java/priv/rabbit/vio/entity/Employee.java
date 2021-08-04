package priv.rabbit.vio.entity;

import java.util.List;

/**
 * @Author administered
 * @Description
 * @Date 2019/11/1 21:08
 **/
public class Employee {
    private Integer id;
    private String lastName;
    private String email;
    private String gender;
    private Department dept;

    List<User> userList;

    public Employee() {
        super();
    }


    public Employee(Integer id, String lastName, String email, String gender) {
        super();
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }


    public Employee(Integer id, String lastName, String email, String gender, Department dept) {
        super();
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dept = dept;
    }




    @Override
    public String toString() {
        return "Employee [id=" + id + ", lastName=" + lastName + ", email=" + email + ", gender=" + gender + ", dept="
                + dept + "]";
    }



    public Department getDept() {
        return dept;
    }
    public void setDept(Department dept) {
        this.dept = dept;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
