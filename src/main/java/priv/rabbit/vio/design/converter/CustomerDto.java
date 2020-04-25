package priv.rabbit.vio.design.converter;

import lombok.Builder;
import lombok.Data;
import priv.rabbit.vio.dto.Company;

import java.util.List;

/**
 * @Author administered
 * @Description
 * @Date 2020/4/25 19:16
 **/
@Data
@Builder
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

}
