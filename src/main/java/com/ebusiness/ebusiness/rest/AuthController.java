package com.ebusiness.ebusiness.rest;

import com.ebusiness.ebusiness.dto.*;
import com.ebusiness.ebusiness.entity.UserEntity;
import com.ebusiness.ebusiness.security.TokenGenerator;
import com.ebusiness.ebusiness.service.service.ClientService;
import com.ebusiness.ebusiness.service.service.DriverService;
import com.ebusiness.ebusiness.service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private AuthenticationManager authenticationManager;
    private TokenGenerator tokenGenerator;
    private final UserService userService;
    private final ClientService clientService;
    private final DriverService driverService;

    AuthController(AuthenticationManager authenticationManager, TokenGenerator tokenGenerator, ClientService clientService, DriverService driverService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
        this.clientService = clientService;
        this.driverService = driverService;
        this.userService = userService;
    }


    @PostMapping("login")
    public ResponseEntity<? extends LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String email = loginRequestDto.getEmail();
            Optional<UserEntity> optionalUser = userService.getUserByEmail(email);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            UserEntity user = optionalUser.get();
            String token = tokenGenerator.generateToken(authentication);

            LoginResponseDto response = userService.buildResponse(user, token);
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (UsernameNotFoundException | IllegalStateException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(
            description = """
            Access restricted to users with roles: ADMIN.
            """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping("register/admin")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterDto registerDto) {
        try {
            userService.registerAdmin(registerDto);
            return ResponseEntity.ok("Admin successfully registered!");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("register/client")
    public ResponseEntity<String> registerClient(@RequestBody RegisterClientDto registerClientDto) {
        try {
            clientService.registerClient(registerClientDto);
            return ResponseEntity.ok("Client successfully registered!");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("register/driver")
    public ResponseEntity<String> registerDriver(@RequestBody RegisterDriverDto registerDriverDto) {
        try {
            driverService.registerDriver(registerDriverDto);
            return ResponseEntity.ok("Driver successfully registered!");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(
            description = """
            Access restricted to users with roles: ADMIN.
            """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping("auth/admin/verify-driver")
    public ResponseEntity<String> verifyDriver(@RequestBody VerificationDto verificationDto) {
        try {
            driverService.verifyDriver(verificationDto.getEmail());
            return ResponseEntity.ok("Driver successfully verified!");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(
            description = """
            Access restricted to users with roles: ADMIN.
            """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping("auth/admin/block-driver")
    public ResponseEntity<String> blockDriver(@RequestBody VerificationDto verificationDto) {
        try {
            driverService.blockDriver(verificationDto.getEmail());
            return ResponseEntity.ok("Driver blocked!");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(
            description = """
            Access restricted to users with roles: ADMIN.
            """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PutMapping("auth/admin/unblock-driver")
    public ResponseEntity<String> unblockDriver(@RequestBody VerificationDto verificationDto) {
        try {
            driverService.unblockDriver(verificationDto.getEmail());
            return ResponseEntity.ok("Driver unblocked!");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
