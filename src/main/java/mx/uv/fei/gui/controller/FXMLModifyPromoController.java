/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.gui.controller;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
public class FXMLModifyPromoController implements Initializable {

    @FXML
    private TableView<Promo> tableViewPromos;
    @FXML
    private TableColumn<String, Promo> columnPromo;
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
    private Button btnModify;
    @FXML
    private Button btnDelete;

    UserHolder userHolder = UserHolder.getInstance();

    private int idPromo = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnPromo.setCellValueFactory(new PropertyValueFactory<>("namePromo"));
        tableViewPromos.setItems(getTableViewPromo());
    }

    @FXML
    private void modifyPromo(ActionEvent event) {
        try {
            Promo promo = new Promo();
            promo = getDataFromForm();
            PromoDAO promoDAO = new PromoDAO();
            promoDAO.updatePromo(promo, idPromo);
            DialogGenerator.getConfirmationDialog("La promocion fue actualizada exitosamente ", "Aviso");
            tableViewPromos.setItems(getTableViewPromo());
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
    }

    @FXML
    private void deletePromo(ActionEvent event) {
        try {
            PromoDAO promoDAO = new PromoDAO();
            promoDAO.deletePromo(idPromo);
            DialogGenerator.getConfirmationDialog("La promocion fue eliminada exitosamente ", "Aviso");
            tableViewPromos.setItems(getTableViewPromo());
        } catch (DAOException ex) {
            Logger.getLogger(FXMLModifyPromoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void selected(MouseEvent event) {
        try {
            String namePromo = this.tableViewPromos.getSelectionModel().getSelectedItem().getNamePromo();
            Promo promo = new Promo();
            PromoDAO promoDAO = new PromoDAO();
            promo = promoDAO.getInformation(namePromo);
            initAttributtes(promo);
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
    }

    public ObservableList<Promo> getTableViewPromo() {
        PromoDAO promoDAO = new PromoDAO();
        List<Promo> listPromo = new ArrayList();
        EmployeeDAO employee = new EmployeeDAO();
        try {
            int id = employee.getSedeEmployee(userHolder.getUser().getEmail());
            listPromo = promoDAO.allPromosPerEmployee(id);
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
        ObservableList<Promo> getAllPromos = (ObservableList<Promo>) FXCollections.observableArrayList(listPromo);
        return getAllPromos;
    }

    public void initAttributtes(Promo promo) {
        idPromo = promo.getIdPromo();
        this.txtName.setText(promo.getNamePromo());
        this.txtDescription.setText(promo.getDescription());
        txtDescription.setWrapText(true);
        this.txtTermsAndConditions.setText(promo.getTermsAndConditions());
        txtTermsAndConditions.setWrapText(true);
        Date inicio = promo.getStartDate();
        LocalDate start = inicio.toLocalDate();
        this.datePickeRStartDate.setValue(start);
        Date fin = promo.getEndDate();
        LocalDate end = fin.toLocalDate();
        this.datePickerEndDate.setValue(end);
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
        int id = employee.getSedeEmployee(userHolder.getUser().getEmail());
        promo.setId(id);
        return promo;
    }
}
