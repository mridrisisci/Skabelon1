package app.daos;

import app.enums.Roles;
import app.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;


public interface ISecurityDAO
{
    UserDTO getVerifiedUser(String username, String password) throws ValidationException;

}
