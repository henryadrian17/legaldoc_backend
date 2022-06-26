package dev.henrymolina.legaldoc_web_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LegaldocWebServicesApplication /*implements CommandLineRunner*/ {
    //@Autowired
    //private BCryptPasswordEncoder passwordEncoder;
    public static void main(String[] args) {
        SpringApplication.run(LegaldocWebServicesApplication.class, args);
    }
   /* @Override
    public void run(String... args) throws Exception {
        String password = "12345";

        for (int i = 0; i < 4; i++) {
            String passwordBcrypt = passwordEncoder.encode(password);
            System.out.println(passwordBcrypt);
        }

    }*/

}
