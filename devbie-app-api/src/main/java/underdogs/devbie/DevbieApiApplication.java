package underdogs.devbie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DevbieApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevbieApiApplication.class, args);
    }
}