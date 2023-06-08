/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.interfaces.IProduct;

/**
 *
 * @author alexs
 */
public class ProductDAO implements IProduct {

    @Override
    public int addProduct(Product product) throws DAOException {
        try {
            Product validateProduct = getValidatedProductForAdd(product);
            return registerProduct(validateProduct);
        } catch (IllegalArgumentException ex) {
            throw new DAOException(ex.getMessage(), Status.WARNING);
        }
    }

    @Override
    public int registerProduct(Product product) throws DAOException {
        int result = -1;
        try {
            String query = "insert into producto(nombre_producto, fecha_caducidad, presentacion, "
                    + "tamano, tipo, disponible, proveedor_id, precio)"
                    + "values (?,?,?,?,?,?,?,?)";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getProductName());
            statement.setDate(2, product.getExpirationDate());
            statement.setString(3, product.getPresentation());
            statement.setString(4, product.getSize());
            statement.setString(5, product.getTypeProduct());
            statement.setInt(6, product.getAvailable());
            statement.setInt(7, product.getIdSupplier());
            statement.setInt(8, product.getPrice());
            result = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("No fue posible registrar el producto", Status.ERROR);
        } finally {
            DataBaseManager.close();
        }
        return result;
    }

    private Product getValidatedProductForAdd(Product product) throws DAOException {
        checkProductDuplication(product);
        return product;
    }

    private void checkProductDuplication(Product product) throws DAOException {
        try {
            String query = "SELECT * FROM producto WHERE producto.nombre_producto = ? AND fecha_caducidad = ? "
                    + "AND presentacion = ? AND tamano = ? AND tipo = ? AND disponible = ? AND precio = ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getProductName());
            statement.setDate(2, product.getExpirationDate());
            statement.setString(3, product.getPresentation());
            statement.setString(4, product.getSize());
            statement.setString(5, product.getTypeProduct());
            statement.setInt(6, product.getAvailable());
            statement.setInt(7, product.getPrice());
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                throw new IllegalArgumentException("El producto ya se encuentra registrado en el sistema");
            }
        } catch (SQLException ex) {
            throw new DAOException("No fue posible validar el producto en el sistema", Status.ERROR);
        } finally {
            DataBaseManager.close();
        }
    }

    @Override
    public int getIdProduct(String productName) throws DAOException {
        int result;
        try {
            String query = "SELECT producto.producto_id FROM farmacia.producto WHERE producto.nombre_producto = ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, productName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("producto_id");
            } else {
                throw new DAOException("No se encontró el producto", Status.ERROR);
            }
        } catch (SQLException ex) {
            throw new DAOException("No fue posible obtener el Id del producto", Status.ERROR);
        } finally {
            DataBaseManager.close();
        }
        return result;
    }

    @Override
    public List<Product> getProductToModify() throws DAOException {
        List<Product> products = new ArrayList<>();
        try {
            String query = "SELECT farmacia.producto.nombre_producto, farmacia.producto.fecha_caducidad, farmacia.producto.presentacion, \n"
                    + "farmacia.producto.tamano, farmacia.producto.tipo, \n"
                    + "farmacia.producto.disponible, farmacia.proveedor.nombreProveedor, farmacia.producto.precio,\n"
                    + "farmacia.sede.nombre_sede, farmacia.sedeproducto.existencia\n"
                    + "FROM farmacia.producto\n"
                    + "INNER JOIN farmacia.proveedor ON farmacia.producto.proveedor_id = farmacia.proveedor.proveedor_id\n"
                    + "INNER JOIN farmacia.sedeproducto ON farmacia.producto.producto_id = farmacia.sedeproducto.producto_id\n"
                    + "INNER JOIN farmacia.sede ON farmacia.sede.sede_id = farmacia.sedeproducto.sede_id";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Product product = new Product();
                product.setProductName(result.getString("nombre_producto"));
                product.setExpirationDate(result.getDate("fecha_caducidad"));
                product.setPresentation(result.getString("presentacion"));
                product.setSize(result.getString("tamano"));
                product.setTypeProduct(result.getString("tipo"));
                product.setAvailable(result.getInt("disponible"));
                product.setNameSupplier(result.getString("nombreProveedor"));
                product.setPrice(result.getInt("precio"));
                product.setNameSite(result.getString("nombre_sede"));
                product.setAmount(result.getInt("existencia"));
                products.add(product);
            }
        } catch (SQLException ex) {
            throw new DAOException("No fue posible recuperar los productos para modificar", Status.ERROR);
        } finally {
            DataBaseManager.close();
        }
        return products;
    }

    @Override
    public Product getInfoToModifyProduct(String productName) throws DAOException {
        Product product = new Product();
        try {
            String query = "SELECT farmacia.producto.nombre_producto, farmacia.producto.fecha_caducidad, farmacia.producto.presentacion, \n"
                    + "farmacia.producto.tamano, farmacia.producto.tipo, \n"
                    + "farmacia.producto.disponible, farmacia.proveedor.nombreProveedor, farmacia.producto.precio,\n"
                    + "farmacia.sede.nombre_sede, farmacia.sedeproducto.existencia\n"
                    + "FROM farmacia.producto\n"
                    + "INNER JOIN farmacia.proveedor ON farmacia.producto.proveedor_id = farmacia.proveedor.proveedor_id\n"
                    + "INNER JOIN farmacia.sedeproducto ON farmacia.producto.producto_id = farmacia.sedeproducto.producto_id\n"
                    + "INNER JOIN farmacia.sede ON farmacia.sede.sede_id = farmacia.sedeproducto.sede_id where farmacia.producto.nombre_producto = ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, productName);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                product.setProductName(result.getString("nombre_producto"));
                product.setExpirationDate(result.getDate("fecha_caducidad"));
                product.setPresentation(result.getString("presentacion"));
                product.setSize(result.getString("tamano"));
                product.setTypeProduct(result.getString("tipo"));
                product.setAvailable(result.getInt("disponible"));
                product.setNameSupplier(result.getString("nombreProveedor"));
                product.setPrice(result.getInt("precio"));
                product.setNameSite(result.getString("nombre_sede"));
                product.setAmount(result.getInt("existencia"));
            }
        } catch (SQLException ex) {
            throw new DAOException("No fue posible recuperar la info del producto a modificar", Status.ERROR);
        } finally {
            DataBaseManager.close();
        }
        return product;
    }

    @Override
    public int updateProduct(String productName, Product product) throws DAOException {
        int result = -1;
        try {
            String query = "UPDATE producto SET nombre_producto = (?), fecha_caducidad = (?), "
                    + "presentacion = (?), tamano = (?), tipo = (?), disponible = (?), proveedor_id = (?), precio = (?)"
                    + "WHERE nombre_producto = ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, product.getProductName());
            statement.setDate(2, product.getExpirationDate());
            statement.setString(3, product.getPresentation());
            statement.setString(4, product.getSize());
            statement.setString(5, product.getTypeProduct());
            statement.setInt(6, product.getAvailable());
            statement.setInt(7, product.getIdSupplier());
             statement.setInt(8, product.getPrice());
            statement.setString(9, productName);
            result = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("No fue posible actualizar el producto", Status.ERROR);
        } finally {
            DataBaseManager.close();
        }
        return result;
    }

    @Override
    public int deleteProduct(int productId) throws DAOException {
        int result = -1;
        try {
            String query = "DELETE FROM producto WHERE producto_id = ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productId);
            result = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("No fue posible eliminar el producto", Status.ERROR);
        } finally {
            DataBaseManager.close();
        }
        return result;
    }

    @Override
    public List<Product> getProductWithPrice(int idSede) throws DAOException {
        List<Product> products = new ArrayList<>();
        try {
            String query = "select p.nombre_producto, p.precio from farmacia.producto p inner join farmacia.sedeproducto sp on p.producto_id = sp.producto_id where sp.sede_id = ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idSede);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Product product = new Product();
                product.setProductName(result.getString("nombre_producto"));
                product.setPrice(result.getInt("precio"));
                products.add(product);
            }
        } catch (SQLException ex) {
            throw new DAOException("No fue posible obtener los productos con precio", Status.ERROR);
        } finally {
            DataBaseManager.close();
        }
        return products;
    }
}
