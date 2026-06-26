package com.mor.tfis.config;

import com.mor.tfis.entity.Informant;
import com.mor.tfis.entity.Taxpayer;
import com.mor.tfis.entity.User;
import com.mor.tfis.repository.InformantRepository;
import com.mor.tfis.repository.TaxpayerRepository;
import com.mor.tfis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final InformantRepository informantRepository;
    private final TaxpayerRepository taxpayerRepository;
    private final PasswordEncoder passwordEncoder;  // Add this field

    @Override
    public void run(String... args) {
        // Create sample user if none exists
        if (userRepository.count() == 0) {
            User sampleUser = User.builder()
                    .username("intelligence.officer1")
                    .passwordHash(passwordEncoder.encode("password123"))  // Now works
                    .role(User.Role.INTELLIGENCE_OFFICER)
                    .mfaEnabled(false)
                    .build();
            userRepository.save(sampleUser);
            log.info("Created sample user: {} with password: password123", sampleUser.getUsername());
        }

        // Create sample informant if none exists
        if (informantRepository.count() == 0) {
            Informant informant = Informant.builder()
                    .name("John Doe")
                    .contactInfo("john@example.com")
                    .isAnonymous(false)
                    .preferredChannel("EMAIL")
                    .build();
            informantRepository.save(informant);
            log.info("Created sample informant: {}", informant.getName());
        }

        // Create sample taxpayer if none exists
        if (taxpayerRepository.count() == 0) {
            Taxpayer taxpayer = Taxpayer.builder()
                    .tin("1234567890")
                    .name("ABC Trading PLC")
                    .businessType("Import/Export")
                    .address("Addis Ababa, Bole Road")
                    .phone("+251911234567")
                    .email("info@abctrading.com")
                    .build();
            taxpayerRepository.save(taxpayer);
            log.info("Created sample taxpayer: {}", taxpayer.getName());
        }
    }
}