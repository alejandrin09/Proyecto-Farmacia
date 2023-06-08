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

import mx.uv.fei.interfaces.ISite;

/**
 *
 * @author aresj
 */
public class SiteDAO implements ISite{

    @Override
    public List<Site> showNameSite() throws DAOException {
        List<Site> nameSites = new ArrayList<>();
        try{
           String query = "SELECT nombre_sede FROM farmacia.sede";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Site site = new Site();
                site.setName(result.getString("nombre_sede"));
                nameSites.add(site);
            } 
        }catch(SQLException ex){
            throw new DAOException("No fue posible establecer la conexion con la base de datos", Status.ERROR);
        }
        return nameSites;
    }

    @Override
    public int getIdSite(String name) throws DAOException {
        int result;
        try {
            String query = "SELECT sede_id FROM farmacia.sede WHERE sede.nombre_sede = ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("sede_id");
            } else {
                throw new DAOException("No se encontró la sede", Status.ERROR);
            }
        } catch (SQLException ex) {
            throw new DAOException("No fue posible obtener el Id de la sede", Status.ERROR);
        } finally {
            DataBaseManager.close();
        }
        return result;
    }   

    @Override
    public List<Site> getDataSite() throws DAOException {
        List<Site> dataSites = new ArrayList<>();
        try{
           String query = "SELECT s.sede_id, s.nombre_sede, c.nombre_ciudad FROM farmacia.sede s inner join farmacia.ciudad c on s.ciudad_id = c.ciudad_id";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Site site = new Site();
                site.setIdSite(result.getInt("sede_id"));
                site.setName(result.getString("nombre_sede"));
                site.setNameCity(result.getString("nombre_ciudad"));
                dataSites.add(site);
            } 
        }catch(SQLException ex){
            throw new DAOException("No fue posible obtener la informacion de las farmacias", Status.ERROR);
        }
        return dataSites;
    }

    @Override
    public Site getSiteEmployee(int personalNumber) throws DAOException {
        Site site = new Site();
        try {
            String query = "SELECT s.nombre_sede FROM farmacia.sede s inner join farmacia.empleado e on s.sede_id = e.sede_id where e.empleado_id = ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, personalNumber);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                site.setName(result.getString("nombre_sede"));
            }
        } catch (SQLException ex) {
            throw new DAOException("No fue posible establecer la conexion con la base de datos", Status.ERROR);
        }
        return site;
    }

    @Override
    public int insertProductSite(Site site, Product product) throws DAOException {
        int result = -1;
        try {
            String query = "insert into sedeproducto(sede_id, producto_id, existencia)"
                    + "values (?,?,?)";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, site.getIdSite());
            statement.setInt(2, product.getIdProduct());
            statement.setInt(3, product.getAmount());
            result = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("No fue posible registrar el producto en la sede", Status.ERROR);
        } finally {
            DataBaseManager.close();
        }
        return result;
    }

    @Override
    public List<Site> getAllSite() throws DAOException {
        List<Site> sites = new ArrayList<>();
        try {
            String query = "SELECT nombre_sede FROM farmacia.sede";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Site site = new Site();
                site.setName(result.getString("nombre_sede"));
                sites.add(site);
            }
        } catch (SQLException ex) {
            throw new DAOException("No fue posible establecer la conexion con la base de datos", Status.ERROR);
        } finally {
            DataBaseManager.close();
        }
        return sites;
    }

    @Override
    public int updateSite(int productId, Site site, Product product) throws DAOException {
        int result = -1;
        try {
            String query = "UPDATE sedeproducto SET sede_id = (?), existencia = (?) WHERE producto_id = ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, site.getIdSite());
            statement.setInt(2, product.getAmount());
            statement.setInt(3, productId);
            result = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("No fue posible actualizar el producto", Status.ERROR);
        } finally {
            DataBaseManager.close();
        }
        return result;
    }

    @Override
    public int deleteSite(int productId) throws DAOException {
        int result = -1;
        try {
            String query = "DELETE FROM sedeproducto WHERE producto_id = ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productId);
            result = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("No fue posible eliminar el producto de la sede", Status.ERROR);
        } finally {
            DataBaseManager.close();
        }
        return result;
    }
}
