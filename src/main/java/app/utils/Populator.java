package app.utils;

import jakarta.persistence.EntityManager;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Populator
{

    // initialize entities here

    public Populator()
    {

    }

    public Map<String, Object> getEntities()
    {
        Map<String, Object> entities = new HashMap<>();
        // populate hashmap with entities here
        return entities;
    }

    public void resetAndPersistEntities(EntityManager em)
    {
        em.getTransaction().begin();
        //em.createNativeQuery("DELETE FROM table_name_here").executeUpdate();
        for (Object entity : getEntities().values())
        {
            em.persist(entity);
        }
        em.getTransaction().commit();
    }
}
