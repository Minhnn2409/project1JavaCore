package Lesson19.Bai1.Service;

import Lesson19.Bai1.Model.Category;
import Lesson19.Bai1.Model.Product;
import Lesson19.Bai2.Model.Bill;
import Lesson19.Bai2.Model.User;

import java.sql.Date;

public interface InfoService {
    void infoIntNum(String msg, int num);
    void infoDoubleNum(String msg, double num);
    void infoString(String msg, String str);
    void showCategory(Category category);
    void showProduct(Product product);
    void showBill(Bill bill);
}
