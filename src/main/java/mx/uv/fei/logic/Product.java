/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.logic;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author alexs
 */
public class Product {

    private int idProduct;
    private String productName;
    private Date expirationDate;
    private String presentation;
    private String size;
    private String typeProduct;
    private int available;
    private int idSupplier;
    private String nameSupplier;
    private String nameSite;
    private int amount;
    private int price;

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        checkNumbers(String.valueOf(amount));
        this.amount = amount;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        checkProduct(size);
        this.size = size;
    }

    public String getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(String typeProduct) {
        this.typeProduct = typeProduct;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getNameSupplier() {
        return nameSupplier;
    }

    public void setNameSupplier(String nameSupplier) {
        this.nameSupplier = nameSupplier;
    }

    public String getNameSite() {
        return nameSite;
    }

    public Site toSite(String site) {
        return new Site(site);
    }

    public Supplier toSupplier(String supplier) {
        return new Supplier(supplier);
    }

    public void setNameSite(String nameSite) {
        this.nameSite = nameSite;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        checkNumbers(String.valueOf(price));
        this.price = price;
    }

    private void checkProduct(String name) {
        Pattern pattern = Pattern.compile("^[0-9]+ [a-zA-Z]+$");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Formato invalido");
        }
    }

    private void checkNumbers(String value) {
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Solo se puede ingresar valores numericos");
        }
    }
}
