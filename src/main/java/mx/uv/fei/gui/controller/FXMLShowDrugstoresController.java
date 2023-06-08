/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.uv.fei.logic.DAOException;
import mx.uv.fei.logic.Site;
import mx.uv.fei.logic.SiteDAO;
import mx.uv.fei.logic.Status;
import mx.uv.fei.logic.UserHolder;
/**
 * FXML Controller class
 *
 * @author aresj
 */
public class FXMLShowDrugstoresController implements Initializable {


    @FXML
    private TableColumn<Site, String> columnID;
    @FXML
    private TableColumn<Site, String> columnName;
    @FXML
    private TableColumn<Site, String> columnCity;
    
    private ObservableList<Site> observableSite;
    
    @FXML
    private TableView<Site> tableViewDrogstore;
    
    UserHolder userHolder = UserHolder.getInstance();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnID.setCellValueFactory(new PropertyValueFactory<>("idSite"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnCity.setCellValueFactory(new PropertyValueFactory<>("NameCity"));
        tableViewDrogstore.setItems(getTableViewSites());
    }    

    private ObservableList<Site> getTableViewSites() {
        SiteDAO siteDAO = new SiteDAO();
        List<Site> listAcademicBodys = new ArrayList();
        try{
            listAcademicBodys = siteDAO.getDataSite();
        }catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
        ObservableList<Site> getAllAcademicBodys = FXCollections.observableArrayList(listAcademicBodys);
        return getAllAcademicBodys;
    }
    
    @FXML
    private void selected(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Site site = new Site();
            site = tableViewDrogstore.getSelectionModel().getSelectedItem();
            if (site != null) {
                userHolder.setSite(site);
                try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLModifyEmployee.fxml"));
                Parent root=loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
                } catch (IOException ex) {
                    DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
                }
            }
        }
    }  
}
