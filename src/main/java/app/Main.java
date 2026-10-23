package app;

import app.config.ServerConfig;
import app.config.hibernate.HibernateConfig;

public class Main {
    public static void main(String[] args) {
        ServerConfig.setupServer(7070, HibernateConfig.getEntityManagerFactory());
    }
}
