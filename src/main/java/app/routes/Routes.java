package app.routes;

import app.controllers.IController;
import app.controllers.ISecurityController;
import app.controllers.SecurityController;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.EndpointGroup;

import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes
{
    private final ISecurityController securityController;
    private final ObjectMapper jsonMapper = new ObjectMapper();
    // initialize controllers here

    public Routes(Map<String, IController> controllers)
    {
        this.securityController = (SecurityController) controllers.get("security");
        // insert controllers here
    }

    public EndpointGroup getRoutes()
    {
        return () ->
        {
            path("/", () ->
            {
               // routes go here
            });
            path("/auth", () ->
            {
                post("/register", (ctx) -> securityController.register(ctx));
                post("/login", (ctx) -> securityController.login(ctx));
            });
            path("/protected", () ->
            {

            });
        };
    }

}
