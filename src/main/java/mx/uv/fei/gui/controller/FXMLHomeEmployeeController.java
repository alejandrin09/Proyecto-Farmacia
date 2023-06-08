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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import mx.uv.fei.logic.Status;

/**
 * FXML Controller class
 *
 * @author Palom
 */
public class FXMLHomeEmployeeController implements Initializable {

    @FXML
    private MenuItem btnRegisterPromocion;
    @FXML
    private MenuItem btnModifyPromo;
    @FXML
    private MenuItem btnRegisterMedicina;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ImageView btnBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showFXML("FXMLHomeEncargadoP");
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

    @FXML
    private void btnRegisterPromo(ActionEvent event) {
        showFXML("FXMLRegisterPromo");
    }

    @FXML
    private void btnModifyPromo(ActionEvent event) {
        showFXML("FXMLModifyPromo");
    }

    @FXML
    private void btnRegisterMedicina(ActionEvent event) {
    }

    @FXML
    private void backOnClicked(MouseEvent event) {
        try {
            App.openNewWindow("/fxml/FXMLLogin.fxml");
        } catch (IOException ex) {
            Logger.getLogger(FXMLHomeAdministratorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clickedLogo(MouseEvent event) {
        showFXML("FXMLHomeEncargadoP");
    }

}
