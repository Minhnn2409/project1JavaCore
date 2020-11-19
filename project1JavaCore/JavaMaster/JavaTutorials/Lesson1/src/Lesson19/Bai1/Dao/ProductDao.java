package Lesson19.Bai1.Dao;

import Lesson19.Bai1.Model.Product;

import java.util.List;

public interface ProductDao {
    void createProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(int productId);
    Product getProduct(int productId);
    void showProducts();
    List<Product> getProductByName(String productName);
    List<Product> getProductByProductPrice(double productPrice);
}
