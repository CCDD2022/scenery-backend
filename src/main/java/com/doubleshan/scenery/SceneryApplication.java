package com.doubleshan.scenery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.doubleshan.scenery.repository.*;
import com.doubleshan.scenery.model.*;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import com.doubleshan.scenery.model.*;
import com.doubleshan.scenery.repository.*;
import com.doubleshan.scenery.service.PointsRuleAdminService;

@SpringBootApplication
public class SceneryApplication {
    public static void main(String[] args) {
        SpringApplication.run(SceneryApplication.class, args);
    }

}
