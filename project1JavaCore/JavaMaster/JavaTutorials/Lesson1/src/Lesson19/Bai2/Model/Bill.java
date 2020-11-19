package Lesson19.Bai2.Model;

import Lesson19.Bai1.Model.Product;

import java.util.Date;

public class Bill {
    private int billId;
    private int billQuantity;
    private double billPrice;
    private Product billProduct;
    private java.sql.Date billBuyDate;
    private User billUser;

    public Bill() {
    }

    public Bill(int billId, int billQuantity, double billPrice, Product billProduct, java.sql.Date billBuyDate, User billUser) {
        this.billId = billId;
        this.billQuantity = billQuantity;
        this.billPrice = billPrice;
        this.billProduct = billProduct;
        this.billBuyDate = billBuyDate;
        this.billUser = billUser;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getBillQuantity() {
        return billQuantity;
    }

    public void setBillQuantity(int billQuantity) {
        this.billQuantity = billQuantity;
    }

    public double getBillPrice() {
        return billPrice;
    }

    public void setBillPrice(double billPrice) {
        this.billPrice = billPrice;
    }

    public Product getBillProduct() {
        return billProduct;
    }

    public void setBillProduct(Product billProduct) {
        this.billProduct = billProduct;
    }

    public Date getBillBuyDate() {
        return billBuyDate;
    }

    public void setBillBuyDate(java.sql.Date billBuyDate) {
        this.billBuyDate = billBuyDate;
    }

    public User getBillUser() {
        return billUser;
    }

    public void setBillUser(User billUser) {
        this.billUser = billUser;
    }
}
