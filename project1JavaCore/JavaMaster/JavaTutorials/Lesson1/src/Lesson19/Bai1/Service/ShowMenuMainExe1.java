package Lesson19.Bai1.Service;

import Lesson19.Bai1.Dao.ProductDao;
import Lesson19.Bai1.Dao.ProductDaoImpl;
import Lesson19.Bai1.Model.Product;

import java.util.List;

public class ShowMenuMainExe1 {
    public static void createProduct(){
        InputService inputService = new InputServiceImpl();
        ProductDao productDao = new ProductDaoImpl();

        Product product = inputService.inputProduct();
        productDao.createProduct(product);
        System.out.println("Tạo sản phẩm thành công");
    }

    public static void updateProduct(){
        InputService inputService = new InputServiceImpl();
        ProductDao productDao = new ProductDaoImpl();

        int newProductId = inputService.inputInt("Mời nhập mã sản phẩm cần sửa: ", "Mã sản phẩm cần là số nguyên dương");
        Product newProduct = inputService.inputProduct();
        newProduct.setProductId(newProductId);
        productDao.updateProduct(newProduct);
        System.out.println("Cập nhật sản phẩm thành công");
    }

    public static void deleteProduct(){
        ProductDao productDao = new ProductDaoImpl();
        InputService inputService = new InputServiceImpl();

        productDao.deleteProduct(inputService.inputInt("Mời nhập mã sản phẩm muốn xoá: ", "Mã sản phẩm cần là số nguyên dương"));
        System.out.println("Xoá sản phẩm thành công");
    }

    public static void getProductByName(){
        ProductDao productDao = new ProductDaoImpl();
        InputService inputService = new InputServiceImpl();
        InfoService infoService = new InfoServiceImpl();

        List<Product> productListByName = productDao.getProductByName(inputService.inputString("Mời nhập tên sản phẩm muốn tìm: "));
        if(productListByName.isEmpty()){
            System.out.println("Không có kết quả phù hợp");
        }
        else {
            for (Product product : productListByName){
                System.out.println("-----------------------------------------");
                infoService.showProduct(product);
            }
        }
    }

    public static void getProductByPrice(){
        ProductDao productDao = new ProductDaoImpl();
        InputService inputService = new InputServiceImpl();
        InfoService infoService = new InfoServiceImpl();

        List<Product> productListByPrice = productDao.getProductByProductPrice(inputService.inputDouble("Mời nhập giá sản phẩm cần tìm: ", "giá sản phẩm cần là số nguyên dương"));
        if(productListByPrice.isEmpty()){
            System.out.println("Không có kết quả phù hợp");
        }
        else{
            for (Product product : productListByPrice){
                System.out.println("-----------------------------------");
                infoService.showProduct(product);
            }
        }
    }
    public static void showMenu(){
        while (true){
            InputService inputService = new InputServiceImpl();

            System.out.println("=============== Menu công việc ===============");
            System.out.println("1. Tạo sản phẩm");
            System.out.println("2. Sửa sản phẩm");
            System.out.println("3. Xoá sản phẩm");
            System.out.println("4. Tìm kiếm sản phẩm theo tên");
            System.out.println("5. Tìm kiếm sản phẩm theo giá");
            System.out.println("6. Thoát");

            int yourOption = inputService.inputInt("Mời bạn nhập lựa chọn: ", "Lựa chọn cần là số nguyên dương");
            if(yourOption == 1){
                createProduct();
            }
            else if(yourOption == 2){
                updateProduct();
            }
            else if(yourOption == 3){
                deleteProduct();
            }
            else if(yourOption == 4){
                getProductByName();
            }
            else if(yourOption == 5){
                getProductByPrice();
            }
            else if(yourOption == 6){
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
