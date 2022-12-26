package com.vss.example;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class ExampleApplication {

    public static void main(String[] args) {

        Logger logger = Logger.getLogger("namelogge");
        logger.setLevel(Level.FINEST);

        org.slf4j.Logger logger1 = LoggerFactory.getLogger("logger");


        SpringApplication.run(ExampleApplication.class, args);
        logger.info("this is info log level");
        logger1.error("this is error");

        System.out.println("Hello maven");
        logger.warning("this is waring log level");

        logger1.error("something error");
    }

}
