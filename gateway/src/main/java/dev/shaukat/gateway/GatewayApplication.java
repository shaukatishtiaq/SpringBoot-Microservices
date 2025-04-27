package dev.shaukat.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder){
		return builder.routes()
				.route(p -> p
						.path("/companies/**")
						.uri("lb://COMPANY-SERVICE"))
				.route(p -> p
						.path("/jobs/**")
						.uri("lb://JOB-SERVICE"))
				.route(p -> p
						.path("/reviews/**")
						.uri("lb://REVIEW-SERVICE:8083"))
				.route(p -> p
						.path("/eureka/**")
						.filters(spec -> spec
								.setPath("/"))
						.uri("http://localhost:8761"))
				.route(p -> p
						.path("/get")
						.filters(filter -> filter
								.addRequestHeader("hello", "world")
								.addRequestHeader("abc","def")

						)
						.uri("http://httpbin.org:80"))
				.build();
	}
}