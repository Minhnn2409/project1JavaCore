package Lesson19.Bai2.Dao;

import Lesson19.Bai2.Model.Bill;

import java.sql.Date;
import java.util.List;

public interface BillDao {
    void createBill(Bill bill);
    void updateBill(Bill bill);
    void deleteBill(int billId);
    Bill getBill(int billId);
    void showBill();
    List<Bill> getBillsById(int getBillId);
    List<Bill> getBillsByUserId(int getBillUserId);
    List<Bill> getBillsByUserName(String getBillName);
    List<Bill> getBillsByDate(Date startDate, Date endDate);
}
