package app.daos;

import app.entities.UserAccount;
import app.enums.Roles;
import app.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;


public interface ISecurityDAO
{
    UserDTO getVerifiedUser(String username, String password) throws ValidationException;
    UserAccount create(String username, String password);
    UserAccount addRole(String username, Roles role);
    UserAccount removeRole(String username, Roles role);
}
