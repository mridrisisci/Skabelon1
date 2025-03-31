package app.daos;

import dat.entities.UserAccount;
import dat.enums.Roles;
import dat.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;


public interface ISecurityDAO
{
    UserDTO getVerifiedUser(String username, String password) throws ValidationException;
    UserAccount createUser(String username, String password);
    UserAccount addRoleToUser(String username, Roles role);
    UserAccount removeRoleFromUser(String username, Roles role);
}
