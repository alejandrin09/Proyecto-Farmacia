/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.gui.controller;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
public class FXMLGetProductToModifyController implements Initializable {

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
    private Button modifyProduct;

    @FXML
    private TableView<Product> tableProducts;

    @FXML
    private TableColumn<Product, Integer> columnAmount;

    @FXML
    private TableColumn<Product, String> columnAvailable;

    @FXML
    private TableColumn<Product, String> columnPrice;

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
    private TableColumn<Product, String> columnSize;

    @FXML
    private TableColumn<Product, String> columnSupplier;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtOther;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtSize;
    
    @FXML
    private TextField txtPrice;

    private ObservableList<Product> observableListProduct;
    private final int AVAILABLE = 1;
    private final int NOT_AVAILABLE = 0;

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

        initComboBoxSite();
        initComboBoxSupplier();
        initComboBoxTypeProduct();
        initComboBoxPresentation();
    }

    @FXML
    void modifyProductOnAction(ActionEvent event) {
        try {
            invokeProductUpdate(getDataFromFormProduct(), getDataFromFormSite());
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
    }

    private void invokeProductUpdate(Product product, Site site) {
        ProductDAO productDAO = new ProductDAO();
        SiteDAO siteDAO = new SiteDAO();
        int modify = 0;
        try {
            product = tableProducts.getSelectionModel().getSelectedItem();
            String productName = tableProducts.getSelectionModel().getSelectedItem().getProductName();
            product.setProductName(productName);
            if (productDAO.updateProduct(productName, getDataFromFormProduct()) > 0) {
                int idProduct = productDAO.getIdProduct(txtProductName.getText());
                modify = 1;
                if (siteDAO.updateSite(idProduct, site, product) > 0) {
                    modify = 1;
                }
            }
            if (modify == 1) {
                DialogGenerator.getDialog("Producto modificado", Status.SUCCESS);
                observableListProduct = getTableViewProduct();
                tableProducts.setItems(observableListProduct);
            }
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
    }

    @FXML
    void showTtxtOther(ActionEvent event) {
        Product product = new Product();
        String selectedTypeProduct = comboBoxTypeProduct.getSelectionModel().getSelectedItem();
        if (!selectedTypeProduct.equals("Otro")) {
            txtOther.setVisible(false);
        } else {
            txtOther.setVisible(true);
        }
    }

    @FXML
    void setSelection(MouseEvent event) {
        try {
            String productSelected = tableProducts.getSelectionModel().getSelectedItem().getProductName();
            Product product = new Product();
            ProductDAO productDAO = new ProductDAO();
            product = productDAO.getInfoToModifyProduct(productSelected);
            modifyProduct.setDisable(false);
            initAttributtes(product);
        } catch (DAOException ex) {
            DialogGenerator.getDialog(ex.getMessage(), Status.WARNING);
        }
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

    public void initAttributtes(Product product) {
        txtProductName.setText(product.getProductName());
        txtSize.setText(product.getSize());
        txtAmount.setText(String.valueOf(product.getAmount()));
        txtPrice.setText(String.valueOf(product.getPrice()));
        if (product.getAvailable() > 0) {
            boxAvailable.setSelected(true);
        } else {
            boxAvailable.setSelected(false);
        }
        String typeProduct[] = {"Medicamento",
            "Suplemento", "Higiene", "Protección", "Accesorio"};
        if (Arrays.asList(typeProduct).contains(product.getTypeProduct())) {
            comboBoxTypeProduct.setValue(product.getTypeProduct());
            txtOther.setVisible(false);
        } else {
            comboBoxTypeProduct.setValue("Otro");
            txtOther.setText(product.getTypeProduct());
            txtOther.setVisible(true);
        }
        comboBoxPresentation.setValue(product.getPresentation());
        comboBoxSite.setValue(product.toSite(product.getNameSite()));
        comboBoxSupplier.setValue(product.toSupplier(product.getNameSupplier()));
        Date dateExpiration = product.getExpirationDate();
        LocalDate localDate = dateExpiration.toLocalDate();
        expirationDate.setValue(localDate);
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
