package com.basaki.hateoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * {@code Application} represents the entry point for the Spring
 * boot application example on hateoas tier.
 * <p/>
 *
 * @author Indra Basak
 * @since 08/04/18
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.basaki.hateoas"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
