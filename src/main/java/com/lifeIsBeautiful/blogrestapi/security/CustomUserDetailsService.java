package com.lifeIsBeautiful.blogrestapi.security;

import com.lifeIsBeautiful.blogrestapi.entity.User;
import com.lifeIsBeautiful.blogrestapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/*
   Loading  a User object from the database and how to provide that,
   User object to the spring security.
   So that spring security will perform a database authentication.
   */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
                () -> new UsernameNotFoundException("The User with the given " + usernameOrEmail + " is not found.")

        );

        /* Spring security expects a Set of Granted Authorities,
        so we are converting Set of <Role> to Set of <GrantedAuthorities> */
        Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map(
                        (role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                user.getPassword(),
                authorities);


/*       Notes
        If the requirement is to use a username or email or both for Login functionality.
        Then,
        return new org.springframework.security.core.userdetails.User(usernameOrEmail,
        If you want to Login using only username
        Then,
        return new org.springframework.security.core.userdetails.User(user.getUsername()   */
    }
}
