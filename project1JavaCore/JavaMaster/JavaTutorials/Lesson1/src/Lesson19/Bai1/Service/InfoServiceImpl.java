package Lesson19.Bai1.Service;

import Lesson19.Bai1.Model.Category;
import Lesson19.Bai1.Model.Product;
import Lesson19.Bai2.Model.Bill;
import Lesson19.Bai2.Model.User;

import java.sql.Date;

public class InfoServiceImpl implements InfoService{

    @Override
    public void infoIntNum(String msg, int num) {
        System.out.println(msg + num);
    }

    @Override
    public void infoDoubleNum(String msg, double num) {
        System.out.println(msg + num);
    }

    @Override
    public void infoString(String msg, String str) {
        System.out.println(msg + str);
    }

    @Override
    public void showCategory(Category category) {
        infoString("Tên hạng mục: ", category.getCategoryName());
    }

    @Override
    public void showProduct(Product product) {
        infoString("Tên sản phẩm: ", product.getProductName());
        infoDoubleNum("Giá sản phẩm: ", product.getProductPrice());
        infoIntNum("Số lượng sản phẩm: " , product.getProductQuantity());
        showCategory(product.getCategory());
    }

    @Override
    public void showBill(Bill bill) {
        System.out.println("=== Đơn hàng của bạn ===");
        infoString("Tên người mua: ", bill.getBillUser().getUserName());
        infoString("Địa chỉ người mua: ", bill.getBillUser().getUserAddress());
        infoString("Tên sản phẩm: ", bill.getBillProduct().getProductName());
        infoString("Hạng mục sản phẩm: ", bill.getBillProduct().getCategory().getCategoryName());
        System.out.println("Ngày mua hàng: " + bill.getBillBuyDate());
        infoIntNum("Số lượng hàng đã mua: ", bill.getBillQuantity());
        infoDoubleNum("Giá tiền bạn cần thanh toán: ", bill.getBillPrice());
    }
}
