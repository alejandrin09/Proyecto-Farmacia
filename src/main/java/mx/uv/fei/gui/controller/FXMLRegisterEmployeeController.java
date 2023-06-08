/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import mx.uv.fei.logic.DAOException;
import mx.uv.fei.logic.Employee;
import mx.uv.fei.logic.EmployeeDAO;
import mx.uv.fei.logic.Site;
import mx.uv.fei.logic.SiteDAO;
import mx.uv.fei.logic.Status;
/**
 * FXML Controller class
 *
 * @author aresj
 */
public class FXMLRegisterEmployeeController implements Initializable {


    @FXML
    private Button btnModify;
    @FXML
    private TextField TxtPersonalNumber;
    @FXML
    private TextField txtEmail;
    @FXML
    private ChoiceBox<String> chbrol;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<Site> cbSede;
    
    private final String[]role={"Farmacéutico","Cajero","Vendedor","Encargado"};
    @FXML
    private TextField txtRFC;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chbrol.getItems().addAll(role);
        inicializeComboBoxSite();
    }    
    
    @FXML
    private void btnModify(ActionEvent event) {
        String name = txtName.getText();
        String email = txtEmail.getText();
        int numberPersonal = Integer.parseInt(TxtPersonalNumber.getText());
        String RFC = txtRFC.getText();
        String nameSite = cbSede.getSelectionModel().getSelectedItem().toString();
        String rol = chbrol.getValue();
        Employee employee = new Employee();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Site site = new Site();
        SiteDAO siteDAO = new SiteDAO();
        try {
            int idSite = siteDAO.getIdSite(nameSite);
            employee.setEmail(email);
            employee.setName(name);
            employee.setPassword(employeeDAO.generatePassword());
            employee.setId(numberPersonal);
            employee.setRol(rol);
            employee.setRFC(RFC);
            site.setName(nameSite);
            site.setIdSite(idSite );
            if (employeeDAO.isEmployeeExistingRFC(RFC) == false) {
                if (employeeDAO.isEmailExisting(email) == false) {
                    employeeDAO.registerEmployee(employee, site);
                    DialogGenerator.getConfirmationDialog("El Empleado fue registrado exitosamente Da click en el boton para continuar", "Aviso");
                } else {
                    DialogGenerator.getDialog("El correo del Empleado que tratas de ingresar, ya registrado previamente", Status.ERROR);
                }
            } else {
                DialogGenerator.getDialog("El empledo que tratas de ingresar, ya registrado previamente verifica el RFC", Status.ERROR);
            }
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        } catch (IllegalArgumentException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
    }
    
    public void inicializeComboBoxSite(){
        SiteDAO site = new SiteDAO();
        List<Site> listSites = new ArrayList();
        try {
            listSites = site.showNameSite();
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
        ObservableList<Site> getName = FXCollections.observableArrayList(listSites);
        cbSede.setItems(getName);
    }
}