package Lesson19.Bai2.Model;

public class User {
    private int userId;
    private String userName;
    private String userAddress;

    public User() {
    }

    public User(int userId, String userName, String userAddress) {
        this.userId = userId;
        this.userName = userName;
        this.userAddress = userAddress;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
