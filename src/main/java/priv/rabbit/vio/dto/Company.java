package priv.rabbit.vio.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @Author administered
 * @Description
 * @Date 2020/4/14 1:27
 **/
@Data
@Builder
public class Company {
    private String name;
    private String address;

    public Company(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
