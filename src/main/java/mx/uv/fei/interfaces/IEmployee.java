/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.uv.fei.interfaces;

import java.util.List;
import mx.uv.fei.logic.DAOException;
import mx.uv.fei.logic.Employee;
import mx.uv.fei.logic.Site;

/**
 *
 * @author alexs
 */
public interface IEmployee {
    
    public List <Employee> getEmployeeData(int idSite) throws DAOException;
    public int registerEmployee (Employee employee, Site site) throws DAOException;
    public int updateEmployee (Employee employee, Site site, int idEmployee) throws DAOException;
    public int deleteEmployee (Employee employee) throws DAOException;
    public boolean isEmailExisting (String email) throws DAOException;
    public boolean isEmployeeExistingRFC (String RFC) throws DAOException;
    public String generatePassword();
    public int getSedeEmployee (String email)throws DAOException;
    public String getRFCEmployee (int personalNumber) throws DAOException;
    public boolean isEmployeeExisting (int personalNumber) throws DAOException;
}
