package com.cardona.foroalura;

import com.cardona.foroalura.util.JPAUtils;
import jakarta.persistence.EntityManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForoAluraApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForoAluraApplication.class, args);
        EntityManager entityManager = JPAUtils.getEntityManager();
        entityManager.close();
    }


}
