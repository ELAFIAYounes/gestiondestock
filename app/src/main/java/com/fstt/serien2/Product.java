package com.fstt.serien2;

import java.io.Serializable;

public class Product implements Serializable {

    private int prodId;
    private String prodName;
    private double prodPrice;
    private int prodQte;

    public Product(String prodName, double prodPrice, int prodQte) {
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodQte = prodQte;
    }

    public Product(int prodId, String prodName, double prodPrice, int prodQte) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodQte = prodQte;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public int getProdId() {
        return prodId;
    }
    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public int getProdQte() {
        return prodQte;
    }

    public void setProdQte(int prodQte) {
        this.prodQte = prodQte;
    }
}

