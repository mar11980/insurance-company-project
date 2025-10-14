package vaudoise.insurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"vaudoise.insurance", "vaudoise.insurance.model","vaudoise.insurance.controller","vaudoise.insurance.controlleradvice"})
public class VaudoiseInsuranceApplication {
    public static void main(String[] args) {
          SpringApplication.run(VaudoiseInsuranceApplication.class, args);
        }
    }
