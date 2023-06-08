/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.logic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.interfaces.IPromo;

/**
 *
 * @author Palom
 */
public class PromoDAO implements IPromo {

    @Override
    public int registerPromo(Promo promo) throws DAOException {
        int result = -1;
        try {
            String query = "insert into promocion(nombre, descripcion, TerminosYConciciones, fecha_inicio, fecha_fin, sede_id) values (?,?,?,?,?,?)";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, promo.getNamePromo());
            statement.setString(2, promo.getDescription());
            statement.setString(3, promo.getTermsAndConditions());
            statement.setDate(4, promo.getStartDate());
            statement.setDate(5, promo.getEndDate());
            statement.setInt(6, promo.getId());
            result = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("No fue posible registrar la promo", Status.ERROR);
        }finally {
            DataBaseManager.close();
        }
        return result;
    }

    @Override
    public int deletePromo(int idPromo) throws DAOException {
        int result = -1;
        try {
            String query = "delete from farmacia.promocion where promocion_id = ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idPromo);
            result = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("No fue posible eliminar la promo", Status.ERROR);
        }finally {
            DataBaseManager.close();
        }
        return result;
    }

    @Override
    public Promo getInformation(String namePromo) throws DAOException {
        Promo promo = new Promo();
        try {
            String query = "select * from farmacia.promocion where nombre = ? ";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, namePromo);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                promo.setNamePromo(result.getString("nombre"));
                promo.setDescription(result.getString("descripcion"));
                promo.setTermsAndConditions(result.getString("TerminosYConciciones"));
                promo.setEndDate(result.getDate("fecha_fin"));
                promo.setStartDate(result.getDate("fecha_inicio"));
                promo.setId(result.getInt("sede_id"));
                promo.setIdPromo(result.getInt("promocion_id"));
            }
        } catch (SQLException ex) {
            throw new DAOException("No fue posible obtener la info de la promo", Status.ERROR);
        }finally {
            DataBaseManager.close();
        }
        return promo;
    }

    @Override
    public List<Promo> allPromosPerEmployee(int idSede) throws DAOException {
        List<Promo> promos = new ArrayList<>();
        try {
            String query = "select nombre from farmacia.promocion where sede_id = ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idSede);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Promo promo = new Promo();
                promo.setNamePromo(result.getString("nombre"));
                promos.add(promo);
            }
        } catch (SQLException ex) {
            throw new DAOException("No fue posible obtener las promociones", Status.ERROR);
        } finally {
            DataBaseManager.close();
        }
        return promos;
    }

    @Override
    public int updatePromo(Promo promo, int idPromo) throws DAOException {
        int result = -1;
        try {
            String query = "UPDATE farmacia.promocion set nombre =?, descripcion=?,  TerminosYConciciones =?, fecha_inicio =?, "
                    + "fecha_fin =?  WHERE promocion_id =?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, promo.getNamePromo());
            statement.setString(2, promo.getDescription());
            statement.setString(3, promo.getTermsAndConditions());
            statement.setDate(4, promo.getStartDate());
            statement.setDate(5, promo.getEndDate());
            statement.setInt(6, idPromo);
            result = statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("No fue posible actualizar el curso", Status.ERROR);
        }finally {
            DataBaseManager.close();
        }
        return result;
    }

    @Override
    public List<Promo> allPromosCurrent(int idSede, Date currentDate) throws DAOException {
        List<Promo> promos = new ArrayList<>();
        try {
            String query = "select nombre from farmacia.promocion where sede_id = ? and fecha_fin > ?";
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idSede);
            statement.setDate(2, currentDate); 
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Promo promo = new Promo();
                promo.setNamePromo(result.getString("nombre"));
                promos.add(promo);
            }
        } catch (SQLException ex) {
            throw new DAOException("No fue posible obtener las promociones vigentes", Status.ERROR);
        }finally {
            DataBaseManager.close();
        }
        return promos;
    }

}
