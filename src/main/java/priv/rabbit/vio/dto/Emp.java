package priv.rabbit.vio.dto;

/**
 * @Author administered
 * @Description
 * @Date 2020/4/14 1:26
 **/
public class Emp {
    private String ename;
    private String job;
    private Dept dept = new Dept();
    public String getEname() {
        return ename;
    }
    public void setEname(String name) {
        this.ename = name;
    }
    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public Dept getDept() {
        return dept;
    }
    public void setDept(Dept dept) {
        this.dept = dept;
    }
    @Override
    public String toString() {
        return "Emp [name=" + ename + ", job=" + job + "]" + "\n" + this.dept;
    }

}
