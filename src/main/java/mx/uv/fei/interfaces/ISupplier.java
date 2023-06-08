/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.uv.fei.interfaces;

import java.util.List;
import mx.uv.fei.logic.DAOException;
import mx.uv.fei.logic.Supplier;

/**
 *
 * @author alexs
 */
public interface ISupplier {

    public List<Supplier> getAllSupplier() throws DAOException;
    public int getIdSupplier(String supplier) throws DAOException;
}
