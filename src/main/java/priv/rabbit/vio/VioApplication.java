package priv.rabbit.vio;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "priv.**.mapper")
public class VioApplication {

	public static void main(String[] args) {
		SpringApplication.run(VioApplication.class, args);
	}
}
