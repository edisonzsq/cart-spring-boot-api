package sg.edu.ntu.cart_api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import sg.edu.ntu.cart_api.helper.MinimumPayableCheckHelper;

@SpringBootApplication
@EnableAutoConfiguration
public class CartApiApplication {

	@Value("${MIN_PURCHASE}")
    float minimumPurchase;

	public static void main(String[] args) {
		SpringApplication.run(CartApiApplication.class, args);
	}

	@Bean
	public MinimumPayableCheckHelper minimumPayableCheckHelper(){
		return new MinimumPayableCheckHelper(minimumPurchase);
	}
}
