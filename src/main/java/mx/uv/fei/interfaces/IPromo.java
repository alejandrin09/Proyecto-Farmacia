/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.uv.fei.interfaces;

import java.sql.Date;
import java.util.List;
import mx.uv.fei.logic.DAOException;
import mx.uv.fei.logic.Promo;

/**
 *
 * @author Palom
 */
public interface IPromo {
    public int  registerPromo (Promo promo) throws DAOException;
    public int deletePromo (int idPromo) throws DAOException;
    public Promo getInformation (String namePromo)throws DAOException;
    public List<Promo> allPromosPerEmployee (int idSede)throws DAOException;
    public int updatePromo (Promo promo, int idPromo)throws DAOException;
    public List<Promo> allPromosCurrent (int idSede, Date currentDate)throws DAOException;
}
