/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import mx.uv.fei.logic.Status;

/**
 * FXML Controller class
 *
 * @author alexs
 */
public class FXMLHomeAdministratorController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private ImageView btnBack;
    @FXML
    private MenuItem btnModifyAdquisicion;
    @FXML
    private MenuItem btnModifyEmpleado;
    @FXML
    private MenuItem btnRegisterEmpleado;
    @FXML
    private MenuItem btnRegisterMedicina;
    @FXML
    private MenuItem btnmodifyMedicina;
    @FXML
    private MenuItem btnregisterAdquisicion;
    @FXML
    private MenuItem btnDeleteMedicina;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void backOnClicked(MouseEvent event) {
        try {
            App.openNewWindow("/fxml/FXMLLogin.fxml");
        } catch (IOException ex) {
            Logger.getLogger(FXMLHomeAdministratorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void btnModifyAdquisicion(ActionEvent event) {

    }

    @FXML
    void btnModifyEmpleado(ActionEvent event) {
        showFXML("FXMLShowDrugstores");
    }

    @FXML
    void btnRegisterEmpleado(ActionEvent event) {
        showFXML("FXMLRegisterEmployee");
    }

    @FXML
    void btnRegisterMedicina(ActionEvent event) {
        showFXML("FXMLRegisterProduct");
    }

    @FXML
    void btnmodifyMedicina(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLGetProductToModify.fxml"));
            Parent root = loader.load();
            App.newView(root);
        } catch (IOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
    }

    @FXML
    void btnregisterAdquisicion(ActionEvent event) {
        //showFXML("FXMLRegisterProduct");
    }

    @FXML
    void btnDeleteMedicina(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLGetProductToDelete.fxml"));
            Parent root = loader.load();
            App.newView(root);
        } catch (IOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
    }

    private void showFXML(String fxml) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/" + fxml + ".fxml"));
        } catch (IOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
        borderPane.setCenter(root);
    }
}
