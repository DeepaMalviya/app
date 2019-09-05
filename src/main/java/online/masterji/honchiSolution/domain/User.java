package online.masterji.honchiSolution.domain;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String fullname;
    private String mobile;
    private String email;
    private String photo;
    private String gender;
    private String dob;
    private String address;
    private String Uid;
    private String token;
    private String date;
    private String febric;
    private String design;
    private int roleId;
    //private List<String> Order;

    public User(String fullname, String email) {
        this.fullname = fullname;
        this.email = email;
    }

    public User() {
    }

    public String getFebric() {
        return febric;
    }

    public void setFebric(String febric) {
        this.febric = febric;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    /*public List<String> getOrder() {
        return Order;
    }

    public void setOrder(List<String> order) {
        Order = order;
    }
*/
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
