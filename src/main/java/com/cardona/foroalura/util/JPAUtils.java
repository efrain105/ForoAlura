package com.cardona.foroalura.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtils {

    public static EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("foroalura");

    public static EntityManager getEntityManager(){
        return FACTORY.createEntityManager();
    }

}
