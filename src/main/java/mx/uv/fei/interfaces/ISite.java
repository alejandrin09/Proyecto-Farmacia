/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.uv.fei.interfaces;

import java.util.List;
import mx.uv.fei.logic.DAOException;
import mx.uv.fei.logic.Product;
import mx.uv.fei.logic.Site;

/**
 *
 * @author aresj
 */
public interface ISite {
    public int insertProductSite(Site site, Product product) throws DAOException;
    public List<Site> getAllSite() throws DAOException;
    public List <Site> showNameSite() throws DAOException;
    public int getIdSite(String name) throws DAOException;
    public List <Site> getDataSite() throws DAOException;
    public Site getSiteEmployee(int personalNumber) throws DAOException;
    public int updateSite(int productId, Site site, Product product) throws DAOException;
    public int deleteSite(int productId) throws DAOException;
}
