package api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import api.interceptor.AuthentificationInterceptor;

@Configuration
public class AppConfig implements WebMvcConfigurer {
	@Bean
	public AuthentificationInterceptor authentificationInterceptor() {
		return new AuthentificationInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authentificationInterceptor());
	}
}
