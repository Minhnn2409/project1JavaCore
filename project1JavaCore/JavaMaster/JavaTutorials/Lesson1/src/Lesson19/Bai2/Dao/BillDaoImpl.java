package Lesson19.Bai2.Dao;

import Lesson19.Bai1.Dao.JDBCConnection;
import Lesson19.Bai1.Dao.ProductDao;
import Lesson19.Bai1.Dao.ProductDaoImpl;
import Lesson19.Bai1.Model.Product;
import Lesson19.Bai1.Service.InfoService;
import Lesson19.Bai1.Service.InfoServiceImpl;
import Lesson19.Bai2.Model.Bill;
import Lesson19.Bai2.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BillDaoImpl extends JDBCConnection implements BillDao{
    @Override
    public void createBill(Bill bill) {
        try {
            String sql = "INSERT INTO bill(billQuantity, billPrice, billProductId, billBuyDate, billUserId) VALUES (?, ?, ?, ?, ?)";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bill.getBillQuantity());
            preparedStatement.setDouble(2, bill.getBillPrice());
            preparedStatement.setInt(3, bill.getBillProduct().getProductId());
            preparedStatement.setDate(4, (Date) bill.getBillBuyDate());
            preparedStatement.setInt(5, bill.getBillUser().getUserId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                bill.setBillId(id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateBill(Bill bill) {
        try {
            String sql = "UPDATE bill SET billQuantity = ?, billPrice = ?, billProductId = ?, billBuyDate = ?, billUserId = ? " +
                    "WHERE billId = ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, bill.getBillQuantity());
            preparedStatement.setDouble(2, bill.getBillPrice());
            preparedStatement.setInt(3, bill.getBillProduct().getProductId());
            preparedStatement.setDate(4, (Date) bill.getBillBuyDate());
            preparedStatement.setInt(5, bill.getBillUser().getUserId());
            preparedStatement.setInt(6, bill.getBillId());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteBill(int billId) {
        try {
            String sql = "DELETE FROM bill WHERE billId = ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, billId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Bill getBill(int billId) {
        try {
            String sql = "SELECT bill.*, product.*, user.* FROM bill " +
                    "INNER JOIN product ON product.productId = bill.billProductId " +
                    "INNER JOIN user ON user.userId = bill.billUserId " +
                    "WHERE billId = ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, billId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Bill bill = billRoadMapper(resultSet);
                return bill;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void showBill() {
        InfoService infoService = new InfoServiceImpl();
        try {
            String sql = "SELECT bill.* FROM bill";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Bill bill = billRoadMapper(resultSet);
                infoService.showBill(bill);
                System.out.println("-------------------------------");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Bill> getBillsById(int getBillId) {
        List<Bill> bills = new ArrayList<>();
        try {
            String sql = "SELECT bill.* FROM bill WHERE billId = ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, getBillId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Bill bill = billRoadMapper(resultSet);
                bills.add(bill);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bills;
    }

    @Override
    public List<Bill> getBillsByUserId(int getBillUserId) {
        List<Bill> bills = new ArrayList<>();

        try {
            String sql = "SELECT bill.* FROM bill WHERE billUserId = ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, getBillUserId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Bill bill = billRoadMapper(resultSet);
                bills.add(bill);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bills;
    }

    @Override
    public List<Bill> getBillsByUserName(String getBillName) {
        List<Bill> bills = new ArrayList<>();

        try {
            String sql = "SELECT bill.* FROM bill " +
                    "INNER JOIN user ON user.userId = bill.billUserId " +
                    "WHERE user.userName LIKE ?";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "%" + getBillName + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Bill bill = billRoadMapper(resultSet);
                bills.add(bill);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bills;
    }

    @Override
    public List<Bill> getBillsByDate(Date startDate, Date endDate) {
        List<Bill> bills = new ArrayList<>();
        try {
            String sql ="SELECT bill.* FROM bill WHERE bill.billBuyDate " +
                    "BETWEEN ? AND DATE_ADD(?, INTERVAL 1 DAY)";
            Connection connection = super.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Bill bill = billRoadMapper(resultSet);
                bills.add(bill);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bills;
    }

    private Bill billRoadMapper (ResultSet resultSet) throws SQLException {
        int billId = resultSet.getInt(1);
        int billQuantity = resultSet.getInt(2);
        double billPrice = resultSet.getDouble(3);
        int billProductId = resultSet.getInt(4);
        Date billBuyDate = resultSet.getDate(5);
        int billUserId = resultSet.getInt(6);

        Bill newBill = new Bill();
        newBill.setBillId(billId);
        newBill.setBillQuantity(billQuantity);
        newBill.setBillPrice(billPrice);
        newBill.setBillBuyDate(billBuyDate);

        ProductDao productDao = new ProductDaoImpl();
        Product product = productDao.getProduct(billProductId);
        newBill.setBillProduct(product);

        UserDao userDao = new UserDaoImpl();
        User user = userDao.getUser(billUserId);
        newBill.setBillUser(user);

        return newBill;
    }
}
