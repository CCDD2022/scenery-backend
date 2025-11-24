package com.doubleshan.scenery;

import com.doubleshan.scenery.model.Admin;
import com.doubleshan.scenery.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
public class SceneryBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SceneryBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner initAdminData(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // 检查是否已存在admin用户
            Optional<Admin> existingAdmin = adminRepository.findByUsername("admin");
            if (existingAdmin.isEmpty()) {
                Admin admin = new Admin();
                admin.setUsername("admin");
                admin.setPasswordHash(passwordEncoder.encode("admin123"));
                adminRepository.save(admin);
                System.out.println("✅ 默认管理员已创建：用户名 admin，密码 admin123");
            } else {
                System.out.println("ℹ️ 管理员账户已存在，跳过初始化");
            }
        };
    }
}