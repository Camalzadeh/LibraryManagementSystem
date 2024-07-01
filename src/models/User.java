package models;

import enums.UserRole;

import java.sql.Array;
import java.util.ArrayList;

public class User {

    public User(){}

    public User(String userId, String name, String phone, String address, String email, String password, UserRole userRole) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }
    public String userId;
    public String name;
    public String phone;
    public String address;
    public String email;
    public String password;
    public UserRole userRole;
    public ArrayList<Book> favoriteBooks = new ArrayList<>();
    public ArrayList<Book> reservedBooks = new ArrayList<>();

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public UserRole getUserRole() { return userRole; }
    public void setUserRole(UserRole userRole) { this.userRole = userRole; }
    public ArrayList<Book> getFavoriteBooks() { return favoriteBooks; }
    public void setFavoriteBooks(ArrayList<Book> favoriteBooks) { this.favoriteBooks = favoriteBooks; }
    public ArrayList<Book> getReservedBooks() { return reservedBooks; }
    public void setReservedBooks(ArrayList<Book> reservedBooks) { this.reservedBooks = reservedBooks; }
    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
