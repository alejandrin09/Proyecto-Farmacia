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
import mx.uv.fei.interfaces.ISupplier;

/**
 *
 * @author alexs
 */
public class SupplierDAO implements ISupplier {

    @Override
    public List<Supplier> getAllSupplier() throws DAOException {
        List<Supplier> suppliers = new ArrayList<>();
        try {
            String query = "SELECT nombreProveedor FROM farmacia.proveedor";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Supplier supplier = new Supplier();
                supplier.setName(result.getString("nombreProveedor"));
                suppliers.add(supplier);
            }
        } catch (SQLException ex) {
            throw new DAOException("No fue posible establecer la conexion con la base de datos", Status.ERROR);
        } finally {
            DataBaseManager.close();
        }
        return suppliers;
    }

    @Override
    public int getIdSupplier(String supplier) throws DAOException {
        int result;
        try {
            String query = "SELECT proveedor.proveedor_id FROM farmacia.proveedor WHERE proveedor.nombreProveedor = ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, supplier);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("proveedor_id");
            } else {
                throw new DAOException("No se encontró el proveedor", Status.ERROR);
            }
        } catch (SQLException ex) {
            throw new DAOException("No fue posible obtener el Id del proveedor", Status.ERROR);
        } finally {
            DataBaseManager.close();
        }
        return result;
    }
}
