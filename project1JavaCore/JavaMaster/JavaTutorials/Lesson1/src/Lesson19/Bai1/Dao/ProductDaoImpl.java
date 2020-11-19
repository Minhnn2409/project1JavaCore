package Lesson19.Bai1.Dao;
import Lesson19.Bai1.Model.Category;
import Lesson19.Bai1.Model.Product;
import Lesson19.Bai1.Service.InfoService;
import Lesson19.Bai1.Service.InfoServiceImpl;
import Lesson19.Bai1.Service.InputService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl extends JDBCConnection implements ProductDao{

    @Override
    public void createProduct(Product product) {
        try {
            String sql = "INSERT INTO product(productName, productPrice, productQuantity, categoryId) VALUES (?, ?, ?, ?)";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setDouble(2, product.getProductPrice());
            preparedStatement.setInt(3, product.getProductQuantity());
            preparedStatement.setInt(4, product.getCategory().getCategoryId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int id = resultSet.getInt(1);
                product.setProductId(id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateProduct(Product product) {
        try {
            String sql = "UPDATE product SET productName = ?, productPrice = ?, productQuantity = ?, categoryId = ? " +
                    "WHERE productId = ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setDouble(2, product.getProductPrice());
            preparedStatement.setInt(3, product.getProductQuantity());
            preparedStatement.setInt(4, product.getCategory().getCategoryId());
            preparedStatement.setInt(5, product.getProductId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(int productId) {
        try {
            String sql = "DELETE FROM product WHERE productId = ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Product getProduct(int productId) {
        try {
            String sql = "SELECT product.*, category.categoryName AS CategoryName FROM product " +
                    "INNER JOIN category ON category.categoryId = product.categoryId "+
                    "WHERE productId = ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = productRoadMap(resultSet);
                return product;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void showProducts() {
        List<Product> productList = new ArrayList<Product>();
        InfoService infoService = new InfoServiceImpl();
        try {
            String sql = "SELECT product.*, category.categoryName FROM product " +
                    "INNER JOIN category where category.categoryId = product.categoryId";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = productRoadMap(resultSet);
                productList.add(product);
            }
            for (Product product : productList){
                infoService.showProduct(product);
                System.out.println("********************************");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Product> getProductByName(String productName) {
        List<Product> productList = new ArrayList<Product>();
        try {
            String sql = " SELECT product.*, category.categoryName AS CategoryName FROM product " +
                    "INNER JOIN category ON category.categoryId = product.categoryId "+
                    "WHERE productName LIKE ? ";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + productName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = productRoadMap(resultSet);
                productList.add(product);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productList;
    }

    @Override
    public List<Product> getProductByProductPrice(double productPrice) {
        List<Product> productsGetByPrice = new ArrayList<Product>();

        try {
            String sql = "SELECT product.*, category.categoryName AS CategoryName from product " +
                    "INNER JOIN category ON category.categoryId = product.categoryId " +
                    "WHERE productPrice = ? ";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, productPrice);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = productRoadMap(resultSet);
                productsGetByPrice.add(product);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productsGetByPrice;
    }

    private Product productRoadMap(ResultSet resultSet) throws SQLException {
        int productId = resultSet.getInt("productId");
        String productName = resultSet.getString("productName");
        double productPrice = resultSet.getDouble("productPrice");
        int productQuantity = resultSet.getInt("productQuantity");
        int categoryId = resultSet.getInt("categoryId");
        String categoryName = resultSet.getString("categoryName");

        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setProductQuantity(productQuantity);

        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName(categoryName);

        product.setCategory(category);
        return product;
    }
}
