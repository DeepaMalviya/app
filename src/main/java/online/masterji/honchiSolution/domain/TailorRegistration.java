package online.masterji.honchiSolution.domain;

import java.io.Serializable;
import java.util.List;

public class TailorRegistration implements Serializable {

    private String shopName;
    private String registrationNumber;
    private String ownerName;
    private String emailId;
    private String mobile1;
    private String mobile2;
    private String shopNo;
    private String street;
    private String colony;
    private String area;
    private String city;
    private String pincode;
    private String landmark;
    private double lattitude;
    private double longitude;
    private List<String> gentsOutfits;
    private List<String> ladiesOutfits;

    private String shopPhoto;
    private String ownerPhoto;

    private String tailorType;/*Gents/Ladies /Gents and Ladies*/

    private String createDate;/*dd_MM_yyyy*/
    private String createTime;/*hh:mm:ss*/


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getColony() {
        return colony;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<String> getGentsOutfits() {
        return gentsOutfits;
    }

    public void setGentsOutfits(List<String> gentsOutfits) {
        this.gentsOutfits = gentsOutfits;
    }

    public List<String> getLadiesOutfits() {
        return ladiesOutfits;
    }

    public void setLadiesOutfits(List<String> ladiesOutfits) {
        this.ladiesOutfits = ladiesOutfits;
    }


    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getShopPhoto() {
        return shopPhoto;
    }

    public void setShopPhoto(String shopPhoto) {
        this.shopPhoto = shopPhoto;
    }

    public String getOwnerPhoto() {
        return ownerPhoto;
    }

    public void setOwnerPhoto(String ownerPhoto) {
        this.ownerPhoto = ownerPhoto;
    }

    public String getTailorType() {
        return tailorType;
    }

    public void setTailorType(String tailorType) {
        this.tailorType = tailorType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
