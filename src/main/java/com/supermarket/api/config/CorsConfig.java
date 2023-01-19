package com.supermarket.api.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true);
		config.setAllowedOriginPatterns(Arrays.asList("http://localhost:4200", "http://localhost:4200/"));
		config.setAllowedHeaders(Arrays.asList("Accept", "Origin", "Content-Type", "Depth", "User-Agent",
				"If-Modified-Since,", "Cache-Control", "Authorization", "X-Req", "X-File-Size", "X-Requested-With",
				"X-File-Name", "Content-Disposition"));
		// Arrays.asList("Origin", "Content-Type", "Accept")
		config.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
		config.setExposedHeaders(Arrays.asList("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization",
				"Content-Disposition"));
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
