/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.logic;

/**
 *
 * @author aresj
 */
public class Site {

    private int idSite;
    private String name;
    private int idCity;
    private String NameCity;
    
    public Site (){
        
    }
    
    public Site(String site){
        this.name = site;
    }

    public int getIdSite() {
        return idSite;
    }

    public void setIdSite(int idSite) {
        this.idSite = idSite;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getNameCity() {
        return NameCity;
    }

    public void setNameCity(String NameCity) {
        this.NameCity = NameCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
