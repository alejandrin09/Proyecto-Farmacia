/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.uv.fei.interfaces;

import java.util.Map;
import mx.uv.fei.logic.DAOException;
import mx.uv.fei.logic.User;
import mx.uv.fei.logic.UserType;

/**
 *
 * @author alexs
 */
public interface IUser {
    public UserType loginUserByType(String email, String password) throws DAOException;
    public Map<UserType, Boolean> validateUser(String email, String password) throws DAOException;
    public User getUserData(String email) throws DAOException;
}
