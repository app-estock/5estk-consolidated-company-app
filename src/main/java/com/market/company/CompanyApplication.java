package com.market.company;

import com.market.company.config.JwtFilterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CompanyApplication {
	@Bean
	public FilterRegistrationBean jwtFilter(){
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilterConfig());
		registrationBean.addUrlPatterns("/api/v1.0/*");
		return registrationBean;
	}
	public static void main(String[] args) {
		SpringApplication.run(CompanyApplication.class, args);
	}

}
