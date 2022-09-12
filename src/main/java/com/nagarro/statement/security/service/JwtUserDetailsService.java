package com.nagarro.statement.security.service;

import com.nagarro.statement.security.dto.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    // this replaces/simple impl of user db tables
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("user".equals(username)) {
            return new User("user",
                    "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    Arrays.asList(new SimpleGrantedAuthority("USER")));
        }else if ("admin".equals(username)){

            return new User("admin",
                    "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
        } else{
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
