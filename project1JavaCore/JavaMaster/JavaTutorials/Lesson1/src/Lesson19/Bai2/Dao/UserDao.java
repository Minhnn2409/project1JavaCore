package Lesson19.Bai2.Dao;

import Lesson19.Bai2.Model.User;

import java.util.List;

public interface UserDao {
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(int userId);
    List<User> getUserByName(String userName);
    List<User> getUserById (int userId);
    User getUser(int userId);
}
