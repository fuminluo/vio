package priv.rabbit.vio.dto;

/**
 * @Author administered
 * @Description
 * @Date 2020/4/14 1:27
 **/
public class Company {
    private String name;
    private String address;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    public String toString() {
        return "Company [name=" + name + ", address=" + address + "]";
    }

}
