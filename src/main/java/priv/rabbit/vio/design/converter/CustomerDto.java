package priv.rabbit.vio.design.converter;

/**
 * @Author administered
 * @Description
 * @Date 2020/4/25 19:16
 **/
public class CustomerDto {

    private String customerId;
    private String customerName;
    private String customerLastName;
    private boolean status;
    public CustomerDto(String customerId, String customerName, String customerLastName, boolean status) {
        super();
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerLastName = customerLastName;
        this.status = status;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerLastName() {
        return customerLastName;
    }
    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

}
