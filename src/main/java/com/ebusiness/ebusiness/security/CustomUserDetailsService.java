package com.ebusiness.ebusiness.security;

import com.ebusiness.ebusiness.entity.Role;
import com.ebusiness.ebusiness.entity.UserEntity;
import com.ebusiness.ebusiness.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            UserEntity user = userService.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
            return new User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
        }
        catch (Exception exception) {
            throw new UsernameNotFoundException(email);
        }
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toList());
    }
}
