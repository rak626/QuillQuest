package com.rakesh.blog.security;

import lombok.Data;

@Data
public class JWTAuthRequest {

    private String username;
    private String password;
}
