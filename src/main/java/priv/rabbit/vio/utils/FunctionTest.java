package priv.rabbit.vio.utils;

import priv.rabbit.vio.design.converter.AbstractConverter;
import priv.rabbit.vio.design.converter.Customer;
import priv.rabbit.vio.design.converter.CustomerDto;
import priv.rabbit.vio.design.converter.CustomerConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author administered
 * @Description
 * @Date 2020/4/25 18:57
 **/
public class FunctionTest {
    public static void main(String[] args) {
        /*Function<Integer,Integer> A=i->i+1;
        Function<Integer,Integer> B=i->i*i;
        System.out.println("F1:"+B.apply(A.apply(5)));
        System.out.println("F1:"+B.compose(A).apply(5));
        System.out.println("F2:"+A.apply(B.apply(5)));
        System.out.println("F2:"+B.andThen(A).apply(5));*/
        AbstractConverter<CustomerDto, Customer> abstractConverter = new CustomerConverter();
        CustomerDto dtoCustomer = new CustomerDto("100", "Ramesh", "Fadatare", true);
        Customer Customer = abstractConverter.convertFromDto(dtoCustomer);
        System.out.println("Entity converted from DTO:" + Customer.toString());
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("100", "Ramesh1", "Fadatare", true));
        customers.add(new Customer("200", "Ramesh2", "Fadatare", true));
        customers.add(new Customer("300", "Ramesh3", "Fadatare", true));
        customers.forEach(System.out::println);
        customers.forEach((customer) -> System.out.println(customer.getCustomerId()));
        System.out.println("DTO entities converted from domain:");
        List<CustomerDto> dtoEntities = abstractConverter.createFromEntities(customers);
        dtoEntities.forEach(System.out::println );
        dtoEntities.forEach((customer) -> System.out.println(customer.getCustomerId()));


    }
}
