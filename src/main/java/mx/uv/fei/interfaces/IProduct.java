/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.uv.fei.interfaces;

import java.util.List;
import mx.uv.fei.logic.DAOException;
import mx.uv.fei.logic.Product;

/**
 *
 * @author alexs
 */
public interface IProduct {

    public int addProduct(Product product) throws DAOException;
    public int registerProduct(Product product) throws DAOException;
    public int getIdProduct(String productName) throws DAOException;
    public List<Product> getProductToModify() throws DAOException;
    public Product getInfoToModifyProduct(String productName) throws DAOException;
    public int updateProduct(String productName, Product product) throws DAOException;
    public int deleteProduct(int productId) throws DAOException;
    public List<Product> getProductWithPrice(int idSede) throws DAOException;
}
