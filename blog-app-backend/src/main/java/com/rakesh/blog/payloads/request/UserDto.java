package com.rakesh.blog.payloads.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	@NotEmpty
	@Size(min = 4, message = "Name must have at least 4 character")
	private String name;
	@Email(message = "Your Email Address is not valid")
	private String email;
	@NotEmpty
	@Size(min = 3, max = 10, message = "Password must be between 3 to 10 character")
	private String password;
	@NotEmpty
	private String about;

	private Set<RoleDto> roles = new HashSet<>();

}
