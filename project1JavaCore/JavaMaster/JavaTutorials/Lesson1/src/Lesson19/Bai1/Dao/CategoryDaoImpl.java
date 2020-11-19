package Lesson19.Bai1.Dao;

import Lesson19.Bai1.Model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl extends JDBCConnection implements CategoryDao{

    @Override
    public void createCategory(Category category, String errCreate) {
        try {
            String sql = "INSERT INTO category(categoryName) VALUES(?)";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int id = resultSet.getInt(1);
                category.setCategoryId(id);
            }
        } catch (SQLException throwables) {
            System.out.println(errCreate);
        }
    }

    @Override
    public void updateCategory(Category category, String errUpdate) {
        try {
            String sql = "UPDATE category SET categoryName = ? WHERE categoryID = ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + category.getCategoryName() +"%");
            preparedStatement.setInt(2, category.getCategoryId());
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            System.out.println(errUpdate);
        }
    }

    @Override
    public void deleteCategory(int categoryId, String errDelete) {
        try {
            String sql = "DELETE FROM category where categoryId = ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println(errDelete);
        }
    }

    private Category categoryRoadMapper(ResultSet resultSet) throws SQLException {
        int categoryId = resultSet.getInt("categoryId");
        String categoryName = resultSet.getString("categoryName");

        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName(categoryName);
        return category;
    }

    @Override
    public List<Category> searchCategory(String findingName) {
        List<Category> categoryList = new ArrayList<Category>();
        try {
            String sql = "SELECT category.* WHERE category.name LIKE ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + findingName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Category category = categoryRoadMapper(resultSet);
                categoryList.add(category);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return categoryList;
    }

    @Override
    public Category getCategory(int categoryId) {

        try {
            String sql = "SELECT category.* FROM category WHERE categoryId = ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Category category = categoryRoadMapper(resultSet);
                return category;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
