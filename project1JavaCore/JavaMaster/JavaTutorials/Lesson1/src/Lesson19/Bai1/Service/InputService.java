package Lesson19.Bai1.Service;

import Lesson19.Bai1.Model.MyException;
import Lesson19.Bai1.Model.Product;
import Lesson19.Bai2.Model.User;

import java.util.Date;

public interface InputService {
    int inputInt(String msg, String err);
    double inputDouble(String msg, String err);
    String inputString(String msg);
    Date inputDate(String msg);
    String standardlizeString(String unEdittedString);
    int checkIntNum(int num, String err) throws MyException;
    double checkDoubleNum(double num, String err) throws MyException;
    Product inputProduct();
    User inputUser();
}
