package com.ebusiness.ebusiness.rest;

import com.ebusiness.ebusiness.dto.*;
import com.ebusiness.ebusiness.security.TokenGenerator;
import com.ebusiness.ebusiness.service.service.ClientService;
import com.ebusiness.ebusiness.service.service.DriverService;
import com.ebusiness.ebusiness.service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final ClientService clientService;
    private final DriverService driverService;
    private final TokenGenerator tokenGenerator;

    AuthController(AuthenticationManager authenticationManager, TokenGenerator tokenGenerator, ClientService clientService, DriverService driverService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
        this.clientService = clientService;
        this.driverService = driverService;
        this.userService = userService;
    }


    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenGenerator.generateToken(authentication);

        AuthResponseDto authResponseDto = new AuthResponseDto(token);

        return ResponseEntity.ok(authResponseDto);
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
    @PutMapping("admin/verify-driver")
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
    @PutMapping("admin/block-driver")
    public ResponseEntity<String> blockDriver(@RequestBody VerificationDto verificationDto) {
        try {
            driverService.blockDriver(verificationDto.getEmail());
            return ResponseEntity.ok("Driver blocked!");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
