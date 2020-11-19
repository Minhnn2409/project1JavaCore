package Lesson19.Bai1.Service;

import Lesson19.Bai1.Dao.CategoryDao;
import Lesson19.Bai1.Dao.CategoryDaoImpl;
import Lesson19.Bai1.Model.Category;
import Lesson19.Bai1.Model.MyException;
import Lesson19.Bai1.Model.Product;
import Lesson19.Bai2.Model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class InputServiceImpl implements InputService{

    @Override
    public int inputInt(String msg, String err) {
        int num;
        while (true){
            try{
                Scanner scanner1 = new Scanner(System.in);
                System.out.println(msg);
                num = scanner1.nextInt();
                num = checkIntNum(num, err);
                break;
            }catch (MyException e){
                System.out.println(err);
            }catch (Exception e){
                System.out.println(err);
            }
        }
        return num;
    }

    @Override
    public double inputDouble(String msg, String err) {
        double num;
        while (true){
            try{
                Scanner scanner1 = new Scanner(System.in);
                System.out.println(msg);
                num = scanner1.nextDouble();
                num = checkDoubleNum(num, err);
                break;
            } catch (MyException e) {
                System.out.println(err);
            } catch (Exception e){
                System.out.println(err);
            }
        }
        return num;
    }

    @Override
    public String inputString(String msg) {
        Scanner scanner1 = new Scanner(System.in);
        System.out.println(msg);
        String str = scanner1.nextLine();
        return str;
    }

    @Override
    public Date inputDate(String msg) {
        Date newDate;
        while (true){
            String date = inputString(msg + " <- theo dạng năm/tháng/ngày ->");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try{
                newDate = dateFormat.parse(date);
                break;
            }catch (ParseException e){
                System.out.println("Sai định dạng ngày, mời nhập lại");
            }
        }
        return newDate;
    }

    @Override
    public String standardlizeString(String unEdittedString) {
        unEdittedString = unEdittedString.trim().toLowerCase();
        while (unEdittedString.contains(" ")){
            unEdittedString = unEdittedString.replace(" ", "");
        }
        return unEdittedString;
    }

    @Override
    public int checkIntNum(int num, String err) throws MyException{
        if(num < 0) {
            throw new MyException(err);
        }
        else {
            return num;
        }
    }

    @Override
    public double checkDoubleNum(double num, String err) throws MyException {
        if(num < 0){
            throw new MyException(err);
        }
        else{
            return num;
        }
    }

    @Override
    public Product inputProduct() {
        CategoryDao categoryDao = new CategoryDaoImpl();
        Product product = new Product();
        Category category = new Category();

        int categoryId = inputInt("Mời nhập mã hạng mục hàng hoá: ", "Hạng mục hàng hoá phải là số nguyên dương");
        category.setCategoryId(categoryId);

        while(categoryDao.getCategory(categoryId) == null){
            System.out.println("Không tìm thấy mã hạng mục. Bạn có muốn tạo hạng mục mới với mã hạng mục số " + categoryId
                                + "\n-> Nhấn 'yes' nếu muốn tạo hạng mục mới\n-> Nhấn 'enter' để nhập lại mã hạng mục");
            String option = inputString("Mời nhập lựa chọn của bạn: ");
            if(standardlizeString(option).equals("yes")){
                category.setCategoryName(inputString("Nhập tên hạng mục mới: "));
                categoryDao.createCategory(category, "tạo hạng mục không thành công");
                break;
            }
            else{
                categoryId = inputInt("Mời nhập lại mã hạng mục hàng hoá: ", "Hạng mục hàng hoá phải là số nguyên dương");
                category.setCategoryId(categoryId);
            }
        }
        product.setCategory(category);
        product.setProductName(inputString("Nhập tên sản phẩm: "));
        product.setProductPrice(inputDouble("Nhập giá sản phẩm: ","Giá tiền là số nguyên dương"));
        product.setProductQuantity(inputInt("Nhập số lượng sản phẩm: ","Số lượng sản phẩm là số nguyên dương"));

        return product;
    }

    @Override
    public User inputUser() {
        User user = new User();
        user.setUserName(inputString("Mời nhập tên người mua: "));
        user.setUserAddress(inputString("Mời nhập địa chỉ người mua: "));
        return user;
    }

}
