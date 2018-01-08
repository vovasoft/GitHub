package com.vova.tessarwebserver;

import com.vova.tessarwebserver.dbmapper.CityMapper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TessarWebServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(TessarWebServerApplication.class, args);
    }
//
//    private final CityMapper cityMapper;
//
//    public TessarWebServerApplication(CityMapper cityMapper) {
//        this.cityMapper = cityMapper;
//    }
//
//    @Bean
//    CommandLineRunner sampleCommandLineRunner() {
//        return (args) -> System.out.println(this.cityMapper.findByState("CA"));
//    }

}
