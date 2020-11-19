package Lesson19.Bai1.Dao;

import Lesson19.Bai1.Model.Category;

import java.sql.ResultSet;
import java.util.List;

public interface CategoryDao {
    void createCategory(Category category, String errCreate);
    void updateCategory(Category category, String errUpdate);
    void deleteCategory(int categoryId, String errDelete);
    List<Category> searchCategory(String findingName);
    Category getCategory(int categoryId);
}
