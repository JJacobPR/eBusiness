package com.ebusiness.ebusiness.rest;

import com.ebusiness.ebusiness.dto.AuthResponseDto;
import com.ebusiness.ebusiness.dto.LoginDto;
import com.ebusiness.ebusiness.dto.RegisterDto;
import com.ebusiness.ebusiness.entity.Role;
import com.ebusiness.ebusiness.entity.UserEntity;
import com.ebusiness.ebusiness.security.TokenGenerator;
import com.ebusiness.ebusiness.service.RoleService;
import com.ebusiness.ebusiness.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;

    AuthController(AuthenticationManager authenticationManager, UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, TokenGenerator tokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication);

        AuthResponseDto authResponseDto = new AuthResponseDto(token);

        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {

        if (userService.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role role = roleService.getRoleByName("USER").get();
        user.setRoles(Collections.singletonList(role));

        userService.createUser(user);

        return new ResponseEntity<>("User successfully registered!", HttpStatus.OK);
    }
}
