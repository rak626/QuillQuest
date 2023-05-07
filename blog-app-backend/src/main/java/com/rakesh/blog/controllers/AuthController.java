package com.rakesh.blog.controllers;

import com.rakesh.blog.exceptions.ApiException;
import com.rakesh.blog.payloads.request.UserDto;
import com.rakesh.blog.security.JWTAuthRequest;
import com.rakesh.blog.security.JWTAuthResponse;
import com.rakesh.blog.security.JWTTokenHelper;
import com.rakesh.blog.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final JWTTokenHelper jwtTokenHelper;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JWTAuthRequest request) throws Exception {


        System.out.println("user hit this url....");
        this.authenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

        String generatedToken = this.jwtTokenHelper.generateToken(userDetails);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setToken(generatedToken);

        System.out.println("user authenticated successfully.................");

        return ResponseEntity.ok(jwtAuthResponse);
    }

    private void authenticate(String username, String password) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            System.out.println("Unable to do Authentication ...... ");
            throw new ApiException("Invalid password.......");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        UserDto newUserDto = this.userService.registerNewUser(userDto);
        return new ResponseEntity<>(newUserDto, HttpStatus.CREATED);
    }
}
