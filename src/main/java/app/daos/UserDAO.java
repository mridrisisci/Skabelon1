package app.daos;

import app.config.HibernateConfig;
import app.entities.Role;
import app.entities.User;
import app.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDAO
{
    private GenericDAO genericDAO;
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static UserDAO instance;
    private Logger logger = LoggerFactory.getLogger(UserDAO.class);

    private UserDAO(EntityManagerFactory emf)
    {
        genericDAO = GenericDAO.getInstance(emf);

    }

    public static UserDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new UserDAO(emf);
        }
        return instance;
    }

    public User create(User user)
    {
        try (var em = emf.createEntityManager())
        {
            Set<Role> newRoleSet = new HashSet<>();
            if (user.getRoles().size() == 0)
            {
                Role userRole = em.find(Role.class, "account");
                if (userRole == null)
                {
                    userRole = new Role("account");
                    em.persist(userRole);
                }
                user.addRole(userRole);
            }
            user.getRoles().forEach(role ->
            {
                Role foundRole = em.find(Role.class, role.getRoleName());
                if (foundRole == null)
                {
                    throw new EntityNotFoundException("no role found with this id");
                } else
                {
                    newRoleSet.add(foundRole);
                }
            });
            user.setRoles(newRoleSet);
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            logger.error("Error creating user: " + e.getMessage());
            return null;
        }
        return user;
    }

    public Role createRole(Role role)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(role);
            em.getTransaction().commit();
            return role;
        }
    }

    public UserDTO getVerifiedUser(String username, String password) throws ValidationException
    {
        try (EntityManager em = emf.createEntityManager())
        {
            User user = em.find(User.class, username);
            if (user == null)
            {
                throw new EntityNotFoundException("User not found" + username);
            }
            user.getRoles().size();
            if (!user.verifyPassword(password))
            {
                throw new ValidationException("Wrong password");
            }
            return new UserDTO(user.getUsername(), user.getRoles().stream()
                .map(r -> r.getRoleName()).collect(Collectors.toSet()));
        }
    }

    public void updateUser(User user)
    {

    }

    public void deleteUser (User user)
    {

    }


}
