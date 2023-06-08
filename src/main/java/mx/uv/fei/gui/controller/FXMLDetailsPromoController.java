/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.gui.controller;

import java.net.URL;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import mx.uv.fei.logic.Promo;

/**
 * FXML Controller class
 *
 * @author Palom
 */
public class FXMLDetailsPromoController implements Initializable {

    @FXML
    private Label txtName;
    @FXML
    private Label labelDescripcion;
    @FXML
    private Label labelEndDate;
    @FXML
    private Label labelStartDate;
    @FXML
    private Label labelTyC;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initAttributtes(Promo promo) {
       txtName.setText(String.format("%s", promo.getNamePromo()));
       
        labelDescripcion.setText(String.format("%s", promo.getDescription()));
        labelDescripcion.setWrapText(true);
        labelTyC.setText(String.format(" %s", promo.getTermsAndConditions()));
        labelTyC.setWrapText(true);
        labelEndDate.setText(String.format("%s", promo.getEndDate()));
        labelStartDate.setText(String.format("%s", promo.getStartDate()));
    }
}
