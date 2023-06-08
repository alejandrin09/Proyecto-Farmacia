/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.logic;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.interfaces.IEmployee;

/**
 *
 * @author alexs
 */
public class EmployeeDAO implements IEmployee{

    @Override
    public List <Employee> getEmployeeData(int idSite) throws DAOException {
       List<Employee> employees = new ArrayList<>();
        try{
           String query = "SELECT nombre, puesto, empleado_id FROM farmacia.empleado where sede_id = ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,idSite);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Employee employee = new Employee();
                employee.setName(result.getString("nombre"));
                employee.setRol(result.getString("puesto"));
                employee.setId(result.getInt("empleado_id"));
                employees.add(employee);
            } 
        }catch(SQLException ex){
            throw new DAOException("No fue posible obtener la informacion de los empleados", Status.ERROR);
        }
        return employees;
    }

    @Override
    public int registerEmployee(Employee employee, Site site) throws DAOException {
        int result;
        try{
            String query = "insert into empleado(empleado_id, nombre, puesto, correo, contraseña, sede_id, RFC) values (?,?,?,?,?,?,?)";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, employee.getId());
            statement.setString(2, employee.getName());
            statement.setString(3,employee.getRol());
            statement.setString(4, employee.getEmail());
            statement.setString(5, employee.getPassword());
            statement.setInt(6,site.getIdSite());
            statement.setString(7, employee.getRFC());
            result = statement.executeUpdate();  
        }catch (SQLException ex) {
            throw new DAOException("No fue posible agregar el Cliente", Status.ERROR);
        }        
        return result;
    }

    @Override
    public int updateEmployee(Employee employee, Site site, int idEmployee) throws DAOException {
        int result;
        try {
            String query = "UPDATE farmacia.empleado SET empleado_id = ?, nombre = ?, puesto = ?, sede_id = ? WHERE (empleado_id = ?)";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, employee.getId());
            statement.setString(2, employee.getName());
            statement.setString(3, employee.getRol());
            statement.setInt(4, site.getIdSite());
            statement.setInt(5,idEmployee);
            result = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("No fue posible actualizar los datos del estudiante", Status.ERROR);
        }
        return result;
    }

    @Override
    public int deleteEmployee(Employee employee) throws DAOException {
        int result;
        try {
            String query = "DELETE FROM farmacia.empleado WHERE (empleado_id = ?)";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, employee.getId());
            result = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("No fue posible actualizar los datos del estudiante", Status.ERROR);
        }
        return result;
    }

    @Override
    public boolean isEmailExisting(String email) throws DAOException {
       boolean validateEmail = false;
        try{
            String query = "select * from farmacia.empleado where correo = (?)";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                validateEmail = true;
            } else {
                validateEmail = false;
            }  
        }catch (SQLException ex) {
            throw new DAOException("No fue posible conectar con la base de datos", Status.ERROR);
        }  
        return validateEmail;
    }

    @Override
    public boolean isEmployeeExistingRFC(String RFC) throws DAOException {
        boolean validateEmployee = false;
        try{
            String query = "select * from farmacia.empleado where RFC = (?)";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,RFC);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                validateEmployee = true;
            } else {
                validateEmployee = false;
            }  
        }catch (SQLException ex) {
            throw new DAOException("No fue posible conectar con la base de datos", Status.ERROR);
        }  
        return validateEmployee;
    }
    
    @Override
    public String generatePassword() {
        String charsRange = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&¡";
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(charsRange.length());
            stringBuilder.append(charsRange.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }

    @Override
    public int getSedeEmployee(String email) throws DAOException {
        int idSede = 0;
        try{
            String query = "select sede_id from farmacia.empleado where correo = ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                idSede = result.getInt("sede_id");
            }
        }catch(SQLException ex){
            
            throw new DAOException("No fue posible obtener el id del usario ", Status.ERROR);
        }
        return idSede;
    }

    @Override
    public String getRFCEmployee(int personalNumber) throws DAOException {
        String idSede = "";
        try{
            String query = "select RFC from farmacia.empleado where empleado_id = ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, personalNumber);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                idSede = result.getString("RFC");
            }
        }catch(SQLException ex){
            
            throw new DAOException("No fue posible obtener el id del usario ", Status.ERROR);
        }
        return idSede;
    }

    @Override
    public boolean isEmployeeExisting(int personalNumber) throws DAOException {
        boolean validateEmployee = false;
        try{
            String query = "select * from farmacia.empleado where empleado_id = (?)";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,personalNumber);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                validateEmployee = true;
            } else {
                validateEmployee = false;
            }  
        }catch (SQLException ex) {
            throw new DAOException("No fue posible conectar con la base de datos", Status.ERROR);
        }  
        return validateEmployee;
    }

}