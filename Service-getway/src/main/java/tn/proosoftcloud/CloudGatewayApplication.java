package tn.proosoftcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudGatewayApplication {
	// Static route
	/*@Bean
	RouteLocator routes(RouteLocatorBuilder builder){
		return builder.routes()
				.route(r->r.path("/companies/**").uri("http://localhost:8080"))
				.build();

	}*/
	@Bean
	DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp){
		return new DiscoveryClientRouteDefinitionLocator(rdc,dlp);
	}
//micro service No Params
	/*@Bean
	RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
		return builder.routes()
				//micro service No Params
				.route(predicateSpec -> predicateSpec.path("/muslimsalat/**")
						.filters(f->f
								.addRequestHeader("x-rapidapi-host","muslimsalat.p.rapidapi.com")
								.addRequestHeader("x-rapidapi-key", "928562e11fmsh0cf113f6f5bc4f7p112efajsn2e9714fe6da2")
								.rewritePath("/muslimsalat/(?<segment>.*)","/${segment}")

						)
						.uri("https://muslimsalat.p.rapidapi.com")
				)
				//micro service with params
				*//*.route(predicateSpec -> predicateSpec.path("/calorieninjas/**")
						.filters(f->f
								.addRequestHeader("x-rapidapi-host","calorieninjas.p.rapidapi.com")
								.addRequestHeader("x-rapidapi-key", "928562e11fmsh0cf113f6f5bc4f7p112efajsn2e9714fe6da2")
								.addRequestParameter("query","tomato")
								.rewritePath("/nutrition(?<segment>.*)","/${segment}")

						)
						.uri("https://calorieninjas.p.rapidapi.com")

				)*//*

				.build();
	}*/





	public static void main(String[] args) {
		SpringApplication.run(CloudGatewayApplication.class, args);
	}

}
