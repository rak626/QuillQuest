package com.rakesh.blog.controllers;

import com.rakesh.blog.payloads.request.UserDto;
import com.rakesh.blog.payloads.response.ApiResponse;
import com.rakesh.blog.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {


	private final UserService userService;

	// post

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdUser = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUser, HttpStatus.OK);

	}

	// put

	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}

	// delete
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true, "200 OK"),
				HttpStatus.OK);
	}

	// get

	@GetMapping("/{userId}")
	public ResponseEntity<?> findUser(@PathVariable Integer userId) {
		UserDto foundUser = this.userService.getUserById(userId);
		return ResponseEntity.ok(foundUser);
	}

	// Get all users
	@GetMapping("/")
	public ResponseEntity<?> getAllUser() {
		List<UserDto> allUsers = this.userService.getAllUsers();
		return new ResponseEntity<List<UserDto>>(allUsers, HttpStatus.OK);
	}
}
