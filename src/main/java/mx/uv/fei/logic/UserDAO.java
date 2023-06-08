/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.logic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.interfaces.IUser;

/**
 *
 * @author alexs
 */
public class UserDAO implements IUser {

    private UserType userType;

    private final int USER_TYPE_ADMINISTRATOR = 1;
    private final int USER_TYPE_EMPLOYEE = 2;
    private final int USER_TYPE_CUSTOMER = 3;

    @Override
    public UserType loginUserByType(String email, String password) throws DAOException {
        try {
            Map<UserType, Boolean> userTypes = validateUser(email, password);
            if (userTypes.containsKey(UserType.ADMINISTRATOR) && userTypes.get(UserType.ADMINISTRATOR)) {
                setUserType(UserType.ADMINISTRATOR);
            } else if (userTypes.containsKey(UserType.EMPLOYEE) && userTypes.get(UserType.EMPLOYEE)) {
                setUserType(UserType.EMPLOYEE);
            } else if (userTypes.containsKey(UserType.CUSTOMER) && userTypes.get(UserType.CUSTOMER)) {
                setUserType(UserType.CUSTOMER);
            } else {
                throw new IllegalArgumentException("Correo o contraseña inválidos");
            }
        } catch (IllegalArgumentException ex) {
            throw new DAOException(ex.getMessage(), Status.WARNING);
        }
        return userType;
    }

    @Override
    public Map<UserType, Boolean> validateUser(String email, String password) throws DAOException {
        Map<UserType, Boolean> userTypes = new HashMap<>();
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            CallableStatement statement = connection.prepareCall("{CALL ValidarUsuario(?, ?, ?)}");
            statement.setString(1, email);
            statement.setString(2, password);
            statement.registerOutParameter(3, Types.INTEGER);
            statement.execute();
            int user = statement.getInt(3);

            switch (user) {
                case USER_TYPE_ADMINISTRATOR:
                    userTypes.put(UserType.ADMINISTRATOR, true);
                    break;
                case USER_TYPE_EMPLOYEE:
                    userTypes.put(UserType.EMPLOYEE, true);
                    break;
                case USER_TYPE_CUSTOMER:
                    userTypes.put(UserType.CUSTOMER, true);
                    break;
            }
        } catch (SQLException ex) {
            throw new DAOException("No fue posible validar el usuario", Status.WARNING);
        } finally {
            DataBaseManager.close();
        }
        return userTypes;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    @Override
    public User getUserData(String email) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
