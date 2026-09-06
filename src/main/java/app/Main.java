package app;

import app.config.hibernate.HibernateConfig;
import app.service.LLMAPI;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;

public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public static void main(String[] args) throws IOException, InterruptedException {

        LLMAPI llmApi = new LLMAPI();
        llmApi.askLlm();



        emf.close();
    }
}
