/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package mx.uv.fei.logic;

/**
 *
 * @author alexs
 */
public enum UserType {
    
    ADMINISTRATOR("100"),
    EMPLOYEE("200"),
    CUSTOMER("300");
    
    private String code;

    private UserType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
