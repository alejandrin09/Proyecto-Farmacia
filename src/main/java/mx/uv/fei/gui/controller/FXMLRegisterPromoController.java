/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.gui.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mx.uv.fei.logic.DAOException;
import mx.uv.fei.logic.EmployeeDAO;
import mx.uv.fei.logic.Promo;
import mx.uv.fei.logic.PromoDAO;
import mx.uv.fei.logic.Status;
import mx.uv.fei.logic.UserHolder;

/**
 * FXML Controller class
 *
 * @author Palom
 */
public class FXMLRegisterPromoController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextArea txtTermsAndConditions;
    @FXML
    private TextArea txtDescription;
    @FXML
    private DatePicker datePickeRStartDate;
    @FXML
    private DatePicker datePickerEndDate;
    @FXML
    private Button btnRegistrar;
    
    UserHolder userHolder = UserHolder.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void registerPromo(ActionEvent event) {     
        try {
            Promo promo = new Promo();           
            promo = getDataFromForm();          
            PromoDAO promoDAO = new PromoDAO();                   
            promoDAO.registerPromo(promo);
            DialogGenerator.getConfirmationDialog("La promocion fue registrada exitosamente", "Aviso");
        } catch (DAOException ex) {
           DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }          
    }

    private Promo getDataFromForm() throws DAOException {
        Promo promo = new Promo();
        promo.setNamePromo(txtName.getText());
        promo.setDescription(txtDescription.getText());
        promo.setTermsAndConditions(txtTermsAndConditions.getText());
        LocalDate localStartDate = datePickeRStartDate.getValue();
        if (localStartDate != null) {
            java.sql.Date startDate = java.sql.Date.valueOf(localStartDate);
            promo.setStartDate(startDate);
        }
        LocalDate localEndDate = datePickerEndDate.getValue();
        if (localEndDate != null) {
            java.sql.Date startDate = java.sql.Date.valueOf(localEndDate);
            promo.setEndDate(startDate);
        }
        EmployeeDAO employee = new EmployeeDAO();
        int id= employee.getSedeEmployee(userHolder.getUser().getEmail());
        promo.setId(id);
        return promo;
    }
}
