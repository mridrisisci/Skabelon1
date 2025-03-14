package app.rest;

import app.config.HibernateConfig;
import app.enums.Role;
import app.security.controllers.ISecurityController;
import app.security.controllers.SecurityController;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes
{
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static ISecurityController securityController = new SecurityController();
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
}
