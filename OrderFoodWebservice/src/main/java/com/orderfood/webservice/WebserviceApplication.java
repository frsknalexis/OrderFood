package com.orderfood.webservice;

import com.orderfood.webservice.dto.FileStorageProperties;
import com.orderfood.webservice.repository.RoleRepository;
import com.orderfood.webservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class WebserviceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebserviceApplication.class, args);
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        /*// Khi chương trình chạy
        // Insert vào csdl một user.
        UserEntity user = new UserEntity();
        user.setUsername("123");
        user.setPassword(passwordEncoder.encode("123"));
        List<RoleEntity> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_ADMIN"));
        user.setRoles(roles);
        userRepository.save(user);
        System.out.println(user);*/
    }
}
