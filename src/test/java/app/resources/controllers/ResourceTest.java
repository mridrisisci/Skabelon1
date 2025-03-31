package app.resources.controllers;

import app.config.HibernateConfig;
import app.config.ApplicationConfig;
import app.routes.Routes;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ResourceTest
{

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private final Logger logger = LoggerFactory.getLogger(ResourceTest.class.getName());
    private final ObjectMapper objectMapper = new ObjectMapper();
    // declare entity variables here

    @BeforeAll
    static void beforeAll()
    {
        ApplicationConfig
            .getInstance()
            .initiateServer()
            .setRoute(Routes.getRoutes())
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
