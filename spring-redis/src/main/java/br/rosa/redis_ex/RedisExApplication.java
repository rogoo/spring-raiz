package br.rosa.redis_ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisExApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisExApplication.class, args);
    }
}
