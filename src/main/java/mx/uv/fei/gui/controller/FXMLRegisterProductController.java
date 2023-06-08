/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.gui.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import mx.uv.fei.logic.DAOException;
import mx.uv.fei.logic.Product;
import mx.uv.fei.logic.ProductDAO;
import mx.uv.fei.logic.Site;
import mx.uv.fei.logic.SiteDAO;
import mx.uv.fei.logic.Status;
import mx.uv.fei.logic.Supplier;
import mx.uv.fei.logic.SupplierDAO;

/**
 * FXML Controller class
 *
 * @author alexs
 */
public class FXMLRegisterProductController implements Initializable {

    @FXML
    private CheckBox boxAvailable;

    @FXML
    private ComboBox<String> comboBoxPresentation;

    @FXML
    private ComboBox<Site> comboBoxSite;

    @FXML
    private ComboBox<Supplier> comboBoxSupplier;

    @FXML
    private ComboBox<String> comboBoxTypeProduct;

    @FXML
    private DatePicker expirationDate;

    @FXML
    private Button registerProduct;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtOther;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtSize;

    private final int AVAILABLE = 1;
    private final int NOT_AVAILABLE = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComboBoxSite();
        initComboBoxSupplier();
        initComboBoxTypeProduct();
        initComboBoxPresentation();
    }

    @FXML
    void registerProductOnAction(ActionEvent event) {
        try {
            invokeProductRegistration(getDataFromFormProduct(), getDataFromFormSite());
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        } catch (IllegalArgumentException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
    }

    @FXML
    void showTtxtOther(ActionEvent event) {
        String selectedTypeProduct = comboBoxTypeProduct.getSelectionModel().getSelectedItem();
        if (!selectedTypeProduct.equals("Otro")) {
            txtOther.setVisible(false);
        } else {
            txtOther.setVisible(true);
        }
    }

    private void invokeProductRegistration(Product product, Site site) {
        ProductDAO productDAO = new ProductDAO();
        SiteDAO siteDAO = new SiteDAO();
        try {
            if (productDAO.addProduct(product) > 0) {
                int idProduct = productDAO.getIdProduct(txtProductName.getText());
                product.setIdProduct(idProduct);
                if (siteDAO.insertProductSite(site, product) > 0) {
                    DialogGenerator.getDialog("Producto registrado", Status.SUCCESS);
                }
            } else {
                DialogGenerator.getDialog("No fue posible registrar el producto", Status.WARNING);
            }
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
    }

    public void initComboBoxSite() {
        SiteDAO siteDAO = new SiteDAO();
        List<Site> listOfSite = new ArrayList<>();
        try {
            listOfSite = siteDAO.getAllSite();
        } catch (DAOException ex) {
            DialogGenerator.getDialog("No fue posible mostrar la lista de sitios", Status.WARNING);
        }
        ObservableList<Site> getAllSiteObservableList = FXCollections.observableArrayList(listOfSite);
        comboBoxSite.setItems(getAllSiteObservableList);
    }

    public void initComboBoxSupplier() {
        SupplierDAO supplierDAO = new SupplierDAO();
        List<Supplier> listOfSupplier = new ArrayList<>();
        try {
            listOfSupplier = supplierDAO.getAllSupplier();
        } catch (DAOException ex) {
            DialogGenerator.getDialog("No fue posible mostrar la lista de proveedores", Status.WARNING);
        }
        ObservableList<Supplier> getAllSupplierObservableList = FXCollections.observableArrayList(listOfSupplier);
        comboBoxSupplier.setItems(getAllSupplierObservableList);
    }

    public void initComboBoxTypeProduct() {
        ObservableList<String> typeProduct = FXCollections.observableArrayList("Medicamento",
                "Suplemento", "Higiene", "Protección", "Accesorio", "Otro");
        comboBoxTypeProduct.setItems(typeProduct);
    }

    public void initComboBoxPresentation() {
        ObservableList<String> presentation = FXCollections.observableArrayList("Tabletas",
                "Cápsulas", "Botella", "Barra", "Caja", "Unidad");
        comboBoxPresentation.setItems(presentation);
    }

    public Product getDataFromFormProduct() throws DAOException {
        Product product = new Product();
        SupplierDAO supplierDAO = new SupplierDAO();
        product.setProductName(txtProductName.getText());

        LocalDate localExpirationDate = expirationDate.getValue();
        if (localExpirationDate != null) {
            java.sql.Date expirationDateSelected = java.sql.Date.valueOf(localExpirationDate);
            product.setExpirationDate(expirationDateSelected);
        }

        product.setPresentation(comboBoxPresentation.getSelectionModel().getSelectedItem());
        product.setSize(txtSize.getText());

        if (txtOther.isVisible()) {
            product.setTypeProduct(txtOther.getText());
        } else {
            product.setTypeProduct(comboBoxTypeProduct.getSelectionModel().getSelectedItem());
        }

        if (boxAvailable.isSelected()) {
            product.setAvailable(AVAILABLE);
        } else {
            product.setAvailable(NOT_AVAILABLE);
        }

        product.setIdSupplier(supplierDAO.getIdSupplier(comboBoxSupplier.getSelectionModel().getSelectedItem().toString()));
        product.setAmount(Integer.parseInt(txtAmount.getText()));
        product.setPrice(Integer.parseInt(txtPrice.getText()));

        return product;
    }

    public Site getDataFromFormSite() throws DAOException {
        Site site = new Site();
        SiteDAO siteDAO = new SiteDAO();
        site.setIdSite(siteDAO.getIdSite(comboBoxSite.getSelectionModel().getSelectedItem().toString()));

        return site;
    }
}
