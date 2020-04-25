package priv.rabbit.vio.design.converter;

import lombok.Builder;
import lombok.Data;
import priv.rabbit.vio.config.annotation.BussAnnotation;
import priv.rabbit.vio.dto.Company;

import java.util.List;

/**
 * @Author administered
 * @Description
 * @Date 2020/4/25 19:17
 **/
@Data
@Builder
public class Customer {
    private String customerId;
    private String customerName;
    private String customerLastName;
    private boolean status;

    public Customer(String customerId, String customerName, String customerLastName, boolean status) {
        super();
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerLastName = customerLastName;
        this.status = status;
    }

}
