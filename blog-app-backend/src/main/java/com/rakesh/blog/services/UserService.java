package com.rakesh.blog.services;

import java.util.List;

import com.rakesh.blog.payloads.request.UserDto;

public interface UserService {

	UserDto registerNewUser(UserDto userDto);

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUsers();

	void deleteUser(Integer userId);
}
