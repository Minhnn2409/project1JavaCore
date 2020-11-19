package Lesson19.Bai1.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
    public Connection getConnection(){
        final String url = "jdbc:mysql://127.0.0.1:3306/projectjvcore1";
        final String user = "root";
        final String password = "12345678";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        JDBCConnection jdbcConnection = new JDBCConnection();
        Connection connection = jdbcConnection.getConnection();

        if(connection != null){
            System.out.println("Thanh cong");
        }
    }
}
