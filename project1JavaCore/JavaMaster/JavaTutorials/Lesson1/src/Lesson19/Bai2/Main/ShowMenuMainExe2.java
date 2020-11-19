package Lesson19.Bai2.Main;

import Lesson19.Bai1.Dao.ProductDao;
import Lesson19.Bai1.Dao.ProductDaoImpl;
import Lesson19.Bai1.Model.Product;
import Lesson19.Bai1.Service.InfoService;
import Lesson19.Bai1.Service.InfoServiceImpl;
import Lesson19.Bai1.Service.InputService;
import Lesson19.Bai1.Service.InputServiceImpl;
import Lesson19.Bai2.Dao.BillDao;
import Lesson19.Bai2.Dao.BillDaoImpl;
import Lesson19.Bai2.Dao.UserDao;
import Lesson19.Bai2.Dao.UserDaoImpl;
import Lesson19.Bai2.Model.Bill;
import Lesson19.Bai2.Model.User;

import java.sql.Date;
import java.util.List;


public class ShowMenuMainExe2 {

    private static Product checkProduct(){
        ProductDao productDao = new ProductDaoImpl();
        InputService inputService = new InputServiceImpl();

        Product purchaseProduct;
        while (true){
            int checkCodeProduct = inputService.inputInt("Mời nhập mã sản phẩm muốn mua: ",
                    "Mã sản phẩm phải là số nguyên dương");
            purchaseProduct = productDao.getProduct(checkCodeProduct);
            if(purchaseProduct != null){
                System.out.println(" ******* Bạn đã chọn sản phẩm thành công *******");
                break;
            }
            else{
                System.out.println("Không có sản phẩm phù hợp");
            }
        }
        return purchaseProduct;
    }

    private static User checkUser() {
        InputService inputService = new InputServiceImpl();
        UserDao userDao = new UserDaoImpl();

        User purchaseUser;
        while (true){
            int checkUser = inputService.inputInt("Mời bạn nhập mã khách hàng: ", "Mã khách hàng phải là số nguyên dương");
            purchaseUser = userDao.getUser(checkUser);

            if(purchaseUser != null){
                System.out.println("****** Tìm người dùng thành công ******");
                break;
            }
            else {
                System.out.println("Không tìm thấy người dùng! Bạn có muốn tạo người mua mới? Nhập 'yes' nếu tạo khách hàng mới hoặc " +
                        "'exit' để huỷ việc mua hàng");
                String option = inputService.inputString("Mời nhập lựa chọn: ");
                if(inputService.standardlizeString(option).equals("exit")){
                    System.out.println("Cảm ơn đã sử dụng dịch vụ");
                    purchaseUser = null;
                    break;
                }
                else if(inputService.standardlizeString(option).equals("yes")){
                    purchaseUser = inputService.inputUser();
                    userDao.createUser(purchaseUser);
                    break;
                }
                else {

                }
            }
        }
        return purchaseUser;
    }

    private static Bill setNewBill(Product purchaseProduct, User purchaseUser){
        InputService inputService = new InputServiceImpl();
        Bill bill = new Bill();

        if(purchaseUser != null){
            //chú ý thay đổi tuỳ chọn nhập ngày
            java.util.Date date = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            bill.setBillBuyDate(sqlDate);

            int yourQuantity;
            while (true){
                yourQuantity = inputService.inputInt("Mời bạn nhập số lượng hàng hoá muốn mua: ",
                        "Số lượng cần là số nguyên dương");
                if(yourQuantity < purchaseProduct.getProductQuantity()){
                    break;
                }
                System.out.println("Số lượng sản phẩm không đủ");
            }
            bill.setBillQuantity(yourQuantity);
            bill.setBillPrice(purchaseProduct.getProductPrice()*yourQuantity);
            bill.setBillUser(purchaseUser);
            bill.setBillProduct(purchaseProduct);
        }
        return bill;
    }

    public static void createBill() {
        BillDao billDao = new BillDaoImpl();
        InfoService infoService = new InfoServiceImpl();
        ProductDao productDao = new ProductDaoImpl();

        System.out.println("======= Bảng sản phẩm hiện tại =======");
        productDao.showProducts();
        System.out.println("------------------------------------");

        Product purchaseProduct = checkProduct();
        User purchaseUser = checkUser();

        Bill bill = setNewBill(purchaseProduct, purchaseUser);
        purchaseProduct.setProductQuantity(purchaseProduct.getProductQuantity() - bill.getBillQuantity());
        productDao.updateProduct(purchaseProduct);
        billDao.createBill(bill);
        System.out.println("Tạo hoá đơn thành công!");
        System.out.println("----------------------------");
        infoService.showBill(bill);
    }

    public static void updateBill(){
        InputService inputService = new InputServiceImpl();
        InfoService infoService = new InfoServiceImpl();
        BillDao billDao = new BillDaoImpl();
        ProductDao productDao = new ProductDaoImpl();

        System.out.println("======= Bảng hoá đơn hiện tại =======");
        billDao.showBill();
        System.out.println("------------------------------------");

        while (true){
            int checkBillId = inputService.inputInt("Mời nhập mã hoá đơn cần sửa: ",
                    "Mã hoá đơn phải là số dương");

            Bill bill = billDao.getBill(checkBillId);
            if(bill == null){
                System.out.println("Không tìm thấy bill!");
            }
            else {
                Bill oldBill = bill;
                int preBillQuantity = oldBill.getBillQuantity();
                Product purchaseProduct = checkProduct();
                User purchaseUser = checkUser();
                bill = setNewBill(purchaseProduct, purchaseUser);

                if(oldBill.getBillProduct().getProductId() == bill.getBillProduct().getProductId()){
                    if(preBillQuantity > bill.getBillQuantity()){
                        purchaseProduct.setProductQuantity(purchaseProduct.getProductQuantity() +
                                preBillQuantity - bill.getBillQuantity());
                    }
                    else if(preBillQuantity < bill.getBillQuantity()){
                        purchaseProduct.setProductQuantity(purchaseProduct.getProductQuantity() -
                                bill.getBillQuantity() + preBillQuantity);
                    }
                }
                else {
                    Product returnProduct = productDao.getProduct(oldBill.getBillProduct().getProductId());
                    int updateQuantity = oldBill.getBillQuantity() + returnProduct.getProductQuantity();
                    returnProduct.setProductQuantity(updateQuantity);
                    productDao.updateProduct(returnProduct);

                    int inputProductQuantity = purchaseProduct.getProductQuantity();
                    purchaseProduct.setProductQuantity(inputProductQuantity - bill.getBillQuantity());
                }
                productDao.updateProduct(purchaseProduct);
                bill.setBillId(checkBillId);
                billDao.updateBill(bill);
                System.out.println();
                System.out.println("=> Hoá đơn mới của bạn: ");
                infoService.showBill(bill);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                System.out.println();
                System.out.println("Cập nhật thành công");
                break;
            }
        }
    }

    public static void deleteBill(){
        BillDao billDao = new BillDaoImpl();
        InputService inputService = new InputServiceImpl();

        System.out.println("======= Bảng hoá đơn hiện tại =======");
        billDao.showBill();
        System.out.println("------------------------------------");
        System.out.println();

        billDao.deleteBill(inputService.inputInt("Mời nhập mã đơn muốn xoá: ", "Mã đơn phải là số nguyên dương"));
        System.out.println("Xoá sản phẩm thành công");
    }

    public static void getBillById(){
        InputService inputService = new InputServiceImpl();
        InfoService infoService = new InfoServiceImpl();
        BillDao billDao = new BillDaoImpl();

        int getBillId = inputService.inputInt("Mời nhập mã hoá đơn cần tìm: ", "Mã hoá đơn cần là số nguyên dương");
        List<Bill>bills = billDao.getBillsById(getBillId);
        System.out.println("Danh sách hoá đơn theo mã " + getBillId);
        if(bills.isEmpty()){
            System.out.println();
            System.out.println("***** Danh sách trống *****");
            System.out.println();
        }
        else{
            for (Bill bill : bills){
                infoService.showBill(bill);
                System.out.println("---------------------------");
            }
        }
    }

    public static void getBillByUserId(){
        InputService inputService = new InputServiceImpl();
        InfoService infoService = new InfoServiceImpl();
        BillDao billDao = new BillDaoImpl();

        int getBillUserId = inputService.inputInt("Mời nhập mã người dùng để tìm đơn: ",
                "Mã người dùng cần là số nguyên dương");
        List<Bill> bills = billDao.getBillsByUserId(getBillUserId);
        System.out.println("Danh sách hoá đơn theo mã người dùng " + getBillUserId);
        if(bills.isEmpty()){
            System.out.println();
            System.out.println("***** Danh sách trống *****");
            System.out.println();
        }
        else{
            for (Bill bill : bills){
                infoService.showBill(bill);
                System.out.println("---------------------------");
            }
        }
    }

    public static void getBillByUserName(){
        InputService inputService = new InputServiceImpl();
        InfoService infoService = new InfoServiceImpl();
        BillDao billDao = new BillDaoImpl();

        String userName = inputService.inputString("Mời nhập tên người dùng để tìm hoá đơn: ");
        List<Bill> bills = billDao.getBillsByUserName(inputService.standardlizeString(userName));
        System.out.println("Danh sách hoá đơn theo tên người dùng " + "'" + userName + "'");
        if(bills.isEmpty()){
            System.out.println();
            System.out.println("***** Danh sách trống *****");
            System.out.println();
        }
        else{
            for (Bill bill : bills){
                infoService.showBill(bill);
                System.out.println("---------------------------");
            }
        }
    }

    public static void getBillByDate(){
        InputService inputService = new InputServiceImpl();
        InfoService infoService = new InfoServiceImpl();
        BillDao billDao = new BillDaoImpl();

        java.util.Date startDate = inputService.inputDate("Mời nhập ngày bắt đầu");
        java.util.Date endDate = inputService.inputDate("Mời nhập ngày kết thúc");

        Date sqlStartDate = new Date(startDate.getTime());
        Date sqlEndDate = new Date(endDate.getTime());

        List<Bill> bills = billDao.getBillsByDate(sqlStartDate, sqlEndDate);
        System.out.println("Danh sách hoá đơn bắt đầu từ ngày " + startDate + " đến ngày " + endDate);
        if(bills.isEmpty()){
            System.out.println();
            System.out.println("***** Danh sách trống *****");
            System.out.println();
        }
        else{
            for (Bill bill : bills){
                infoService.showBill(bill);
                System.out.println("---------------------------");
            }
        }
    }

    public static void showMenu(){
        while (true){
            InputService inputService = new InputServiceImpl();

            System.out.println("=============== Menu công việc ===============");
            System.out.println("1. Mua đơn hàng mới");
            System.out.println("2. Sửa đơn hàng");
            System.out.println("3. Xoá đơn hàng");
            System.out.println("4. Tìm kiếm đơn hàng theo mã hoá đơn");
            System.out.println("5. Tìm kiếm đơn hàng theo mã người dùng");
            System.out.println("6. Tìm kiếm đơn hàng theo tên người dùng");
            System.out.println("7. Tìm kiếm đơn hàng theo ngày");
            System.out.println("8. Thoát");

            int yourOption = inputService.inputInt("Mời bạn nhập lựa chọn: ", "Lựa chọn cần là số nguyên dương");
            if(yourOption == 1){
                createBill();
            }
            else if(yourOption == 2){
                updateBill();
            }
            else if(yourOption == 3){
                deleteBill();
            }
            else if(yourOption == 4){
                getBillById();
            }
            else if(yourOption == 5){
                getBillByUserId();
            }
            else if(yourOption == 6){
                getBillByUserName();
            }
            else if(yourOption == 7){
                getBillByDate();
            }
            else if(yourOption == 8){
                String checkExit = inputService.inputString("Bạn có chắc muốn thoát? Nhấn 'Yes' để thoát");
                if(inputService.standardlizeString(checkExit).equals("yes")){
                    break;
                }
            }
            else {
                System.out.println("Lựa chọn không phù hợp, mời nhập lại");
            }
        }
    }
}
