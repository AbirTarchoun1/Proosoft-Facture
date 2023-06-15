package Tn.proosoftcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@ComponentScan(basePackages = "Tn.proosoftcloud")
@EnableEurekaClient
public class ServiceFactureApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceFactureApplication.class, args);
	}

}
