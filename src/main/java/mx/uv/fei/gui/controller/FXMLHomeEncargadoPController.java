/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.gui.controller;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.uv.fei.logic.DAOException;
import mx.uv.fei.logic.EmployeeDAO;
import mx.uv.fei.logic.Product;
import mx.uv.fei.logic.ProductDAO;
import mx.uv.fei.logic.Promo;
import mx.uv.fei.logic.PromoDAO;
import mx.uv.fei.logic.Status;
import mx.uv.fei.logic.UserHolder;

/**
 * FXML Controller class
 *
 * @author Palom
 */
public class FXMLHomeEncargadoPController implements Initializable {

    @FXML
    private TableView<Product> tableViewProduct;
    @FXML
    private TableColumn<String, Product> columnProduct;
    @FXML
    private TableColumn<Integer, Product> columnPrice;
    @FXML
    private TableView<Promo> tableViewPromos;
    @FXML
    private TableColumn<String, Promo> columnPromo;

    UserHolder userHolder = UserHolder.getInstance();

    private ObservableList<Product> products;
    private ObservableList<Product> filterProducts;
    @FXML
    private Button btnDetails;
    @FXML
    private TextField txtFiltro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        products = FXCollections.observableArrayList();
        filterProducts = FXCollections.observableArrayList();

        columnPromo.setCellValueFactory(new PropertyValueFactory<>("namePromo"));
        tableViewPromos.setItems(getTableViewPromo());
        columnProduct.setCellValueFactory(new PropertyValueFactory<>("productName"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableViewProduct.setItems(getTableViewProduct());
    }

    @FXML
    private void showPromo(ActionEvent event) {
        try {
            String name = this.tableViewPromos.getSelectionModel().getSelectedItem().getNamePromo();
            Promo promo = new Promo();
            PromoDAO promoDAO = new PromoDAO();
            promo = promoDAO.getInformation(name);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXMLDetailsPromo.fxml"));
            Parent root = loader.load();
            FXMLDetailsPromoController controlador = loader.getController();
            controlador.initAttributtes(promo);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        } catch (IOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
    }

    public ObservableList<Promo> getTableViewPromo() {
        PromoDAO promoDAO = new PromoDAO();
        List<Promo> listPromo = new ArrayList();
        EmployeeDAO employee = new EmployeeDAO();
        try {
            int id = employee.getSedeEmployee(userHolder.getUser().getEmail());
            listPromo = promoDAO.allPromosCurrent(id, getCurrentDate());
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
        ObservableList<Promo> getAllPromos = (ObservableList<Promo>) FXCollections.observableArrayList(listPromo);
        return getAllPromos;
    }

    public ObservableList<Product> getTableViewProduct() {
        ProductDAO productDAO = new ProductDAO();
        List<Product> listProduct = new ArrayList();
        EmployeeDAO employee = new EmployeeDAO();
        try {
            int id = employee.getSedeEmployee(userHolder.getUser().getEmail());
            listProduct = productDAO.getProductWithPrice(id);
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
        ObservableList<Product> getAllProducts = (ObservableList<Product>) FXCollections.observableArrayList(listProduct);
        return getAllProducts;
    }

    public Date getCurrentDate() {
        java.sql.Date date = java.sql.Date.valueOf(LocalDate.now());
        return date;
    }

    @FXML
    private void filtrarProduct(KeyEvent event) {
        String filterProduct = this.txtFiltro.getText();
        this.products = getTableViewProduct();
        if (filterProduct.isEmpty()) {
            this.tableViewProduct.setItems(products);
        } else {
            this.filterProducts.clear();
            for (Product student : this.products) {
                if (student.getProductName().toLowerCase().contains(filterProduct.toLowerCase())) {
                    this.filterProducts.add(student);
                }
            }
            this.tableViewProduct.setItems(filterProducts);
        }
    }

}
