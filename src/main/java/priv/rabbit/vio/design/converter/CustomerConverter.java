package priv.rabbit.vio.design.converter;


/**
 * @Author administered
 * @Description
 * @Date 2020/4/25 19:17
 **/
public class CustomerConverter extends AbstractConverter<CustomerDto, Customer> {
    public CustomerConverter() {
        super(customerDto ->  Customer.builder().customerId(customerDto.getCustomerId()).build(),
                customer -> new CustomerDto(customer.getCustomerId(), customer.getCustomerName(),
                        customer.getCustomerLastName(), customer.isStatus()));
    }


}
