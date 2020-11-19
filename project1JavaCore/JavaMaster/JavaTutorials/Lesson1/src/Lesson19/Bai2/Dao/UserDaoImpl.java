package Lesson19.Bai2.Dao;

import Lesson19.Bai1.Dao.JDBCConnection;
import Lesson19.Bai2.Model.User;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends JDBCConnection implements UserDao{

    @Override
    public void createUser(User user) {
        try {
            String sql = "INSERT INTO user(userName, userAddress) VALUES (?, ?)";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getUserAddress());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                int userId = resultSet.getInt(1);
                user.setUserId(userId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            String sql = "UPDATE user SET userName = ?, userAddress = ? WHERE userId = ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getUserAddress());
            preparedStatement.setInt(3, user.getUserId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int userId) {
        try {
            String sql = "DELETE FROM user WHERE userId = ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<User> getUserByName(String userName) {
        List<User> userListByName = new ArrayList<User>();
        try {
            String sql = "SELECT user.* FROM user WHERE userName = ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "%" + userName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                User user = userRoadMapper(resultSet);
                userListByName.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userListByName;
    }

    @Override
    public List<User> getUserById(int userId) {
        List<User> userListById = new ArrayList<User>();
        try {
            String sql = "SELECT user.* FROM user WHERE userId = ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = userRoadMapper(resultSet);
                userListById.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userListById;
    }

    @Override
    public User getUser(int userId) {
        try {
            String sql = "SELECT user.* FROM user WHERE userId = ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = userRoadMapper(resultSet);
                return user;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    private User userRoadMapper (ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt(1);
        String userName = resultSet.getString(2);
        String userAddress = resultSet.getString(3);

        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserAddress(userAddress);

        return user;
    }
}
