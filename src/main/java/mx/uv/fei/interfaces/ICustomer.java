/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.uv.fei.interfaces;

import mx.uv.fei.logic.Customer;
import mx.uv.fei.logic.DAOException;

/**
 *
 * @author alexs
 */
public interface ICustomer {
    
    public Customer getDataCustomer(String email) throws DAOException;
}
