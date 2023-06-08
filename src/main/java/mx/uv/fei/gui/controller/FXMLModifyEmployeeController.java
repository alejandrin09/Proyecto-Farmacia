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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mx.uv.fei.logic.DAOException;
import mx.uv.fei.logic.Employee;
import mx.uv.fei.logic.EmployeeDAO;
import mx.uv.fei.logic.Site;
import mx.uv.fei.logic.SiteDAO;
import mx.uv.fei.logic.Status;
import mx.uv.fei.logic.UserHolder;
/**
 * FXML Controller class
 *
 * @author aresj
 */
public class FXMLModifyEmployeeController implements Initializable {


    @FXML
    private Button btnExit;
    @FXML
    private TableColumn<Employee, String> columnName;
    @FXML
    private TableColumn<Employee, String> columnRol;
    @FXML
    private TableColumn<Employee, String> columnNPersonal;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnDelete;
    
    private int idSite;
    
    UserHolder userHolder = UserHolder.getInstance();
    @FXML
    private TableView<Employee> tableViewEmployee;
    @FXML
    private TextField TxtPersonalNumber;
    @FXML
    private ChoiceBox<String> chbRole;
    @FXML
    private ComboBox<Site> cbSite;
    @FXML
    private TextField txtName;
    
    private int personalNumber;
    
    private final String[]role={"Farmacéutico","Cajero","Vendedor","Encargado"};
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idSite = userHolder.getSite().getIdSite();
        columnNPersonal.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        tableViewEmployee.setItems(getTableViewSites());
        chbRole.getItems().addAll(role);
        inicializeComboBoxSite();
    }    
    
    private ObservableList<Employee> getTableViewSites() {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employee> listEmployees = new ArrayList();
        try{
            listEmployees = employeeDAO.getEmployeeData(idSite);
        }catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
        ObservableList<Employee> getAllEmployees = FXCollections.observableArrayList(listEmployees);
        return getAllEmployees;
    }
    
    @FXML
    private void btnExit(ActionEvent event) {
        Stage stage = (Stage) this.btnExit.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnModify(ActionEvent event) {
        Site site = new Site();
        Employee employee = new Employee();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        SiteDAO siteDAO = new SiteDAO();
        employee = this.tableViewEmployee.getSelectionModel().getSelectedItem();
        String name = txtName.getText();
        int persinalNumber = Integer.parseInt(TxtPersonalNumber.getText());
        String rol = chbRole.getValue();
        String nameSite = cbSite.getSelectionModel().getSelectedItem().toString();
        employee.setName(name);
        employee.setId(persinalNumber);
        employee.setRol(rol);
        site.setName(nameSite);
        try {
            site.setIdSite(siteDAO.getIdSite(nameSite));
            employeeDAO.updateEmployee(employee, site, personalNumber);
            tableViewEmployee.setItems(getTableViewSites());
            DialogGenerator.getConfirmationDialog("Los datos del empleado fueron actualizados exitosamente Da click en el boton para continuar", "Aviso");
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.ERROR);
        }
    }

    @FXML
    private void btnDelete(ActionEvent event) {
        Site site = new Site();
        Employee employee = new Employee();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        SiteDAO siteDAO = new SiteDAO();
        employee = this.tableViewEmployee.getSelectionModel().getSelectedItem();
        String name = txtName.getText();
        int persinalNumber = Integer.parseInt(TxtPersonalNumber.getText());
        String rol = chbRole.getValue();
        String nameSite = cbSite.getSelectionModel().getSelectedItem().toString();
        employee.setName(name);
        employee.setId(persinalNumber);
        employee.setRol(rol);
        site.setName(nameSite);
        try {
            site.setIdSite(siteDAO.getIdSite(nameSite));
            if(employeeDAO.isEmployeeExisting(personalNumber) == true){
                employeeDAO.deleteEmployee(employee);
                tableViewEmployee.setItems(getTableViewSites());
                DialogGenerator.getConfirmationDialog("El empleado fue eliminado exitosamente Da click en el boton para continuar", "Aviso");
            }else{
                DialogGenerator.getConfirmationDialog("El empleado que tratas de eliminar no existe Da click en el boton para continuar", "Aviso");  
            }  
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.ERROR);
        }
    }

    @FXML
    private void selected(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Employee employee = new Employee();
            SiteDAO siteDAO = new SiteDAO();
            employee = tableViewEmployee.getSelectionModel().getSelectedItem();
            PersonalNumber(employee);
            if (employee != null) {
                try {
                    txtName.setText(employee.getName());
                    TxtPersonalNumber.setText(String.valueOf(employee.getId()));
                    chbRole.setValue(employee.getRol());
                    cbSite.getSelectionModel().select(siteDAO.getSiteEmployee(employee.getId()));
                } catch (DAOException ex) {
                    DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
                }
            }
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
        cbSite.setItems(getName);
    }
    
    public void PersonalNumber(Employee employee){
        personalNumber = employee.getId();
    }
}
