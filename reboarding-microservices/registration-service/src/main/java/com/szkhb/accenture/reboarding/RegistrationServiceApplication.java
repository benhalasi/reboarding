package com.szkhb.accenture.reboarding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.szkhb.accenture.reboarding.config.RegistrationServiceConfig;
import com.szkhb.accenture.reboarding.domain.EntryRequest;
import com.szkhb.accenture.reboarding.domain.User;

import ch.sbb.esta.openshift.gracefullshutdown.GracefulshutdownSpringApplication;

@SpringBootApplication
@RestController
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class RegistrationServiceApplication {

    @Autowired
    RegistrationServiceConfig config;

    @Autowired
    ObjectWriter writer;

    public static void main(String[] args) {
        GracefulshutdownSpringApplication.run(RegistrationServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("CommandLineRunner has started by " + this.getClass()
                    .getSimpleName());

        };
    }

    private int c = 0;

    @GetMapping("/registration/{userId}")
    public String getEntryRequest(@PathVariable("userId") int userId) throws JsonProcessingException {
        System.out.println("GET: getEntryRequest for user@" + userId + " :: " + ++c);
        return writer.writeValueAsString(getEntry(userId));
    }

    private EntryRequest getEntry(int userId) {
        EntryRequest entryRequest = new EntryRequest();
        User user = new User();
        user.setId(userId);
        entryRequest.setUser(user);
        return entryRequest;
    }

    @PostMapping("/registration/{userId}")
    public String getExistingOrCreateNewEntryRequest(@PathVariable("userId") int userId)
            throws JsonProcessingException {
        System.out.println("POST: getEntryRequest for user@" + userId);
        return writer.writeValueAsString(getEntry(userId));
    }
}