package app.resources.controllers;

import app.config.HibernateConfig;
import app.config.ApplicationConfig;
import app.controllers.IController;
import app.controllers.SecurityController;
import app.routes.Routes;
import app.utils.Populator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ResourceTest
{

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private final Logger logger = LoggerFactory.getLogger(ResourceTest.class.getName());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Populator populator;
    // declare entity variables here

    @BeforeAll
    static void beforeAll()
    {
        final Map<String, IController> controllers = new HashMap<>();
        controllers.put("security", new SecurityController(emf));
        Routes routes = new Routes(controllers);
        ApplicationConfig
            .getInstance()
            .initiateServer()
            .setRoute(routes.getRoutes())
            .handleExceptions()
            .checkSecurityRoles()
            .startServer(7777);
        RestAssured.baseURI = "http://localhost:7777/api";
    }

    @BeforeEach
    void beforeEach()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            // initliza entity vars here

            em.getTransaction().begin();
            //em.createQuery("DELETE FROM Room").executeUpdate();
            //em.createQuery("DELETE FROM Hotel").executeUpdate();

            //em.persist(hotelTest1);
            //em.persist(hotelTest2);
            em.flush(); // force persistnce immediately
            em.getTransaction().commit();
            em.clear(); // detach entities from persistence context
        } catch (Exception e)
        {
            logger.error("error setting up test", e);
            fail();
        }
    }

    @Test
    void getById()
    {

    }

    @Test
    void getAll()
    {

    }

    @Test
    void update()
    {

    }

    @Test
    void delete()
    {

    }

    @AfterAll
    static void afterAll()
    {
        if (emf != null && emf.isOpen())
        {
            emf.close();
            System.out.println("EMF is closed....");
        }
        ApplicationConfig.getInstance().stopServer();
    }


}
