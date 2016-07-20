package io.ovd.mcs.security.resource;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ResourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceApplication.class, args);
	}


	/**
	 * An opinionated WebApplicationInitializer to run a SpringApplication from a traditional WAR deployment.
	 * Binds Servlet, Filter and ServletContextInitializer beans from the application context to the servlet container.
	 *
	 * @link http://docs.spring.io/spring-boot/docs/current/api/index.html?org/springframework/boot/context/web/SpringBootServletInitializer.html
	 */
	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}*/


}
