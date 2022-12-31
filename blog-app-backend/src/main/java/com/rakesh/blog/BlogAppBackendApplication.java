package com.rakesh.blog;

import com.rakesh.blog.config.AppConstants;
import com.rakesh.blog.entities.Role;
import com.rakesh.blog.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogAppBackendApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("xyz"));
		System.out.println(this.passwordEncoder.encode("abc"));

		try{
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_USER");

			List<Role> roles = List.of(role, role1);
			List<Role> savedRoles = this.roleRepo.saveAll(roles);
			savedRoles.stream().forEach(r -> System.out.println(r.getName()));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
