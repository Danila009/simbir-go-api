package ru.volga_it.simbir_go;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SimbirGoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimbirGoApplication.class, args);
    }

}
