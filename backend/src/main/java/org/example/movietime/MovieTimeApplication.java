package org.example.movietime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MovieTimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieTimeApplication.class, args);
    }

}
