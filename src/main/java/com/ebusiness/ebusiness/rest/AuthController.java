package com.ebusiness.ebusiness.rest;

import com.ebusiness.ebusiness.dto.*;
import com.ebusiness.ebusiness.entity.Client;
import com.ebusiness.ebusiness.entity.Driver;
import com.ebusiness.ebusiness.entity.Role;
import com.ebusiness.ebusiness.entity.UserEntity;
import com.ebusiness.ebusiness.security.TokenGenerator;
import com.ebusiness.ebusiness.service.service.ClientService;
import com.ebusiness.ebusiness.service.service.DriverService;
import com.ebusiness.ebusiness.service.service.RoleService;
import com.ebusiness.ebusiness.service.service.UserService;
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

import java.time.LocalDateTime;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final ClientService clientService;
    private final DriverService driverService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;

    AuthController(AuthenticationManager authenticationManager, RoleService roleService, PasswordEncoder passwordEncoder, TokenGenerator tokenGenerator, ClientService clientService, DriverService driverService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
        this.clientService = clientService;
        this.driverService = driverService;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication);

        AuthResponseDto authResponseDto = new AuthResponseDto(token);

        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }

    @PostMapping("register/admin")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterDto registerDto) {

        if (clientService.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
  ;
        //Generate current datetime
        user.setRegistrationDate(LocalDateTime.now());

        Role role = roleService.getRoleByName("ADMIN").get();
        user.setRoles(Collections.singletonList(role));

        userService.createUser(user);

        return new ResponseEntity<>("Client successfully registered!", HttpStatus.OK);
    }



    @PostMapping("register/client")
    public ResponseEntity<String> registerClient(@RequestBody RegisterClientDto registerClientDto) {

        if (clientService.existsByEmail(registerClientDto.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        Client client = new Client();
        client.setUsername(registerClientDto.getUsername());
        client.setEmail(registerClientDto.getEmail());
        client.setPassword(passwordEncoder.encode(registerClientDto.getPassword()));
        client.setPhone(registerClientDto.getPhone());
        client.setAddress(registerClientDto.getAddress());

        //Generate current datetime
        client.setRegistrationDate(LocalDateTime.now());

        Role role = roleService.getRoleByName("CLIENT").get();
        client.setRoles(Collections.singletonList(role));

        clientService.createClient(client);

        return new ResponseEntity<>("Client successfully registered!", HttpStatus.OK);
    }

    @PostMapping("register/driver")
    public ResponseEntity<String> registerDriver(@RequestBody RegisterDriverDto registerDriverDto) {

        if (clientService.existsByEmail(registerDriverDto.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }

        Driver driver = new Driver();
        driver.setUsername(registerDriverDto.getUsername());
        driver.setEmail(registerDriverDto.getEmail());
        driver.setPassword(passwordEncoder.encode(registerDriverDto.getPassword()));
        driver.setPhone(registerDriverDto.getPhone());
        driver.setVehicleDetails(registerDriverDto.getVehicleDetails());
        driver.setAvailabilityStatus(false);
        driver.setVerificationStatus(false);

        //Generate current datetime
        driver.setRegistrationDate(LocalDateTime.now());

        Role role = roleService.getRoleByName("DRIVER").get();
        driver.setRoles(Collections.singletonList(role));

        driverService.createDriver(driver);

        return new ResponseEntity<>("Driver successfully registered!", HttpStatus.OK);
    }
}
