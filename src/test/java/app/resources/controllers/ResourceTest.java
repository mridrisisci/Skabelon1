package app.resources.controllers;

import app.config.HibernateConfig;
import app.config.ApplicationConfig;
import app.rest.Routes;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class ResourceTest
{

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    ObjectMapper objectMapper = new ObjectMapper();
    // declare entity variables here

    @BeforeAll
    static void setupAll()
    {
        ApplicationConfig
            .getInstance()
            .initiateServer()
            .setRoute(Routes.getRoutes())
            .handleExceptions()
            .startServer(7777);
        RestAssured.baseURI = "http://localhost:7777/api";
    }

    @BeforeEach
    void setUp()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();

            // wipe db
            //em.createQuery("DELETE FROM Room").executeUpdate();
            //em.createQuery("DELETE FROM Hotel").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE room_id_seq RESTART WITH 1");
            em.createNativeQuery("ALTER SEQUENCE hotel_id_seq RESTART WITH 1");



            // save to db
            em.getTransaction().commit();

            em.clear();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDown()
    {
        if (emf != null && emf.isOpen())
        {
            emf.close();
            System.out.println("EMF is closed....");
        }
        ApplicationConfig.getInstance().stopServer();
    }


}
