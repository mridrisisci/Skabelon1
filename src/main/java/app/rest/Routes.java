package app.rest;

import app.config.HibernateConfig;
import app.security.controllers.ISecurityController;
import app.security.controllers.SecurityController;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes
{
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static ISecurityController securityController = new SecurityController(emf);
    private static ObjectMapper objectMapper = new ObjectMapper();
    // CONTROLLER HERE

    public static EndpointGroup getRoutes()
    {
        return () ->
        {
            path("/", () ->
            {
               // routes go here
            });
            path("/auth", () ->
            {
                post("/register", securityController.register());
                post("/login", securityController.login());
            });
            path("/secured", () ->
            {
                get("demo", ctx -> ctx.json(objectMapper.createObjectNode().put("demo","its friday bitch")), Role.ACCOUNT);

            });
        };
    }


    public static void setSecurityController(SecurityController securityController)
    {
        Routes.securityController = securityController;
    }

    // add setter methods here for other controllers

}
