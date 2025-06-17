package com.ebusiness.ebusiness.config;

import com.ebusiness.ebusiness.entity.Role;
import com.ebusiness.ebusiness.entity.UserEntity;
import com.ebusiness.ebusiness.repository.RoleRepository;
import com.ebusiness.ebusiness.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void run(String... args) {
        List<String> roleNames = List.of("ADMIN", "CLIENT", "DRIVER");

        for (String roleName : roleNames) {
            roleRepository.findByName(roleName)
                    .orElseGet(() -> roleRepository.save(new Role(roleName)));
        }

        String adminEmail = "admin@admin.com";
        if (!userRepository.existsByEmail(adminEmail)) {
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("root"));
            admin.setRegistrationDate(LocalDateTime.now());

            Role role = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role ADMIN not found"));
            admin.setRoles(Collections.singletonList(role));

            userRepository.save(admin);
        }
    }
}
