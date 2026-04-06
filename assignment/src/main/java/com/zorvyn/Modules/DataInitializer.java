package com.zorvyn.Modules;

import com.zorvyn.Modules.User.Models.Role;
import com.zorvyn.Modules.User.Models.Status;
import com.zorvyn.Modules.User.Models.User;
import com.zorvyn.Modules.User.Repository.JpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DataInitializer implements CommandLineRunner {

    private final JpaRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Check if admin already exists to avoid duplicates
        if (userRepository.findByEmail("admin@example.com").isEmpty()) {

            User admin = new User();
                    admin.setName("Admin");
                    admin.setEmail("admin@example.com");
                    admin.setPassword(passwordEncoder.encode("admin123"));
                    admin.setRole(Role.ADMIN);
                    admin.setStatus(Status.ACTIVE);

            userRepository.save(admin);
            System.out.println("Admin user created.");
        }
    }
}
