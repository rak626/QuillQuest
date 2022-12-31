package com.rakesh.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.rakesh.blog.config.AppConstants;
import com.rakesh.blog.entities.Role;
import com.rakesh.blog.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rakesh.blog.entities.User;
import com.rakesh.blog.exceptions.ResourceNotFoundException;
import com.rakesh.blog.payloads.request.UserDto;
import com.rakesh.blog.repositories.UserRepo;
import com.rakesh.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

    @Override
    public UserDto registerNewUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);

		//encode password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		//role set
		Optional<Role> foundRole = this.roleRepo.findById(AppConstants.NORMAL_USER);
		if(foundRole.isPresent()){
			user.getRoles().add(foundRole.get());
		}else {
			System.out.println("--------------found role is null ---------------");
		}

		User newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}

    @Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		this.userRepo.save(user);
		return this.userToDto(user);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updatedUser = this.userRepo.save(user);
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList = this.userRepo.findAll();
		/*
		 * List<UserDto> userDtoList = new ArrayList<>(); for (User user : userList) {
		 * userDtoList.add(this.userToDto(user)); }
		 */

		// using stream api

		List<UserDto> userDtoList = userList.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtoList;
	}

	@Override
	public void deleteUser(Integer userId) {
		// this.userRepo.deleteById(userId); not using this because it will not work
		// with our own exception
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		this.userRepo.delete(user);
	}

	private User dtoToUser(UserDto userDto) {
		// User user = new User();
		// user.setId(userDto.getId());
		// user.setName(userDto.getName());
		// user.setEmail(userDto.getEmail());
		// user.setPassword(userDto.getPassword());
		// user.setAbout(userDto.getAbout());
		// return user;

		return this.modelMapper.map(userDto, User.class);
	}

	private UserDto userToDto(User user) {
		// UserDto userDto = new UserDto();
		// userDto.setId(user.getId());
		// userDto.setName(user.getName());
		// userDto.setEmail(user.getEmail());
		// userDto.setPassword(user.getPassword());
		// userDto.setAbout(user.getAbout());
		// return userDto;

		return this.modelMapper.map(user, UserDto.class);
	}

}
