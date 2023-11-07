package com.pms.preciousmetalsstats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableCaching
@EnableWebMvc
@SpringBootApplication
public class PreciousMetalsStatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PreciousMetalsStatsApplication.class, args);
	}

}
