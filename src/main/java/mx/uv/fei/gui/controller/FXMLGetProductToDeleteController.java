/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.gui.controller;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import mx.uv.fei.logic.DAOException;
import mx.uv.fei.logic.Product;
import mx.uv.fei.logic.ProductDAO;
import mx.uv.fei.logic.SiteDAO;
import mx.uv.fei.logic.Status;

/**
 * FXML Controller class
 *
 * @author alexs
 */
public class FXMLGetProductToDeleteController implements Initializable {

    @FXML
    private TableView<Product> tableProducts;

    @FXML
    private TableColumn<Product, Integer> columnAmount;

    @FXML
    private TableColumn<Product, String> columnAvailable;

    @FXML
    private TableColumn<Product, Date> columnExpirationDate;

    @FXML
    private TableColumn<Product, String> columnNameProduct;

    @FXML
    private TableColumn<Product, String> columnPresentation;

    @FXML
    private TableColumn<Product, String> columnProductType;

    @FXML
    private TableColumn<Product, String> columnSite;

    @FXML
    private TableColumn<Product, String> columnPrice;

    @FXML
    private TableColumn<Product, String> columnSize;

    @FXML
    private TableColumn<Product, String> columnSupplier;

    @FXML
    private Button btnDeleteProduct;

    private ObservableList<Product> observableListProduct;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnNameProduct.setCellValueFactory(new PropertyValueFactory<>("productName"));
        columnExpirationDate.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        columnPresentation.setCellValueFactory(new PropertyValueFactory<>("presentation"));
        columnSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        columnProductType.setCellValueFactory(new PropertyValueFactory<>("typeProduct"));
        columnAvailable.setCellValueFactory(new PropertyValueFactory<>("available"));
        columnSupplier.setCellValueFactory(new PropertyValueFactory<>("nameSupplier"));
        columnSite.setCellValueFactory(new PropertyValueFactory<>("nameSite"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        observableListProduct = getTableViewProduct();
        tableProducts.setItems(observableListProduct);
    }

    @FXML
    void deleteProductOnAction(ActionEvent event) {
        ProductDAO productDAO = new ProductDAO();
        SiteDAO siteDAO = new SiteDAO();
        try {
            String productName = tableProducts.getSelectionModel().getSelectedItem().getProductName();
            int idProduct = productDAO.getIdProduct(productName);
            if (siteDAO.deleteSite(idProduct) > 0) {
                if (productDAO.deleteProduct(idProduct) > 0) {
                    DialogGenerator.getDialog("Producto eliminado", Status.SUCCESS);
                    observableListProduct = getTableViewProduct();
                    tableProducts.setItems(observableListProduct);
                }
            }
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
    }

    @FXML
    void setSelection(MouseEvent event) {
        btnDeleteProduct.setDisable(false);
    }

    public ObservableList<Product> getTableViewProduct() {
        ProductDAO productDAO = new ProductDAO();
        List<Product> listProducts = new ArrayList();
        try {
            listProducts = productDAO.getProductToModify();
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
        ObservableList<Product> getAllProduct = FXCollections.observableArrayList(listProducts);
        return getAllProduct;
    }
}
