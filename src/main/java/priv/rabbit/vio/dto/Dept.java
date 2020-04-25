package priv.rabbit.vio.dto;

/**
 * @Author administered
 * @Description
 * @Date 2020/4/14 1:27
 **/
public class Dept {
    private String dname;
    private String loc;
    private Company company = new Company();
    public String getDname() {
        return dname;
    }
    public void setDname(String dname) {
        this.dname = dname;
    }
    public String getLoc() {
        return loc;
    }
    public void setLoc(String loc) {
        this.loc = loc;
    }
    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }
    @Override
    public String toString() {
        return "Dept [dname=" + dname + ", loc=" + loc + "] " + "\n" + this.company;
    }

}
