package io.ovd.mcs.security.auth;

import io.ovd.mcs.security.auth.model.Role;
import io.ovd.mcs.security.auth.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UserService userService){
		return (args) -> {
			if (userService.userCount() == 0L) {
				userService.registerUser("user", "pass", Arrays.asList(new Role("USER")));
				userService.registerUser("admin", "admin1", Arrays.asList(new Role("USER"),new Role("ADMIN")));
				userService.registerUser("test", "test1", Arrays.asList(new Role("ROLE_FUN"),new Role("TEST")));
			}
		};
	}

}
