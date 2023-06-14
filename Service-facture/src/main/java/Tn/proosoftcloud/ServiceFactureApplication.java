package Tn.proosoftcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class ServiceFactureApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceFactureApplication.class, args);
	}

}
