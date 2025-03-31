package app;

import app.config.HibernateConfig;
import app.daos.SecurityDAO;
import app.config.ApplicationConfig;
import app.routes.Routes;
import app.security.controllers.SecurityController;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.get;

public class Main
{
    final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    final static SecurityController securityController = new SecurityController(emf);
    // initialize controllers vars here

    static SecurityDAO securityDAO = new SecurityDAO(emf);

    public static void main(String[] args)
    {

        Routes.setSecurityController(securityController);
        // set controllers here


        ApplicationConfig
            .getInstance()
            .initiateServer()
            .checkSecurityRoles()
            .setRoute(Routes.getRoutes())
            .handleExceptions()
            .startServer(7000);

    }
}