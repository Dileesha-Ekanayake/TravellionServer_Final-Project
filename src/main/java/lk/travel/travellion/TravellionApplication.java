package lk.travel.travellion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TravellionApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravellionApplication.class, args);
    }

}
