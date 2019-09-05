package online.masterji.honchiSolution.domain;

import com.google.firebase.Timestamp;

public class BookingTailor {
    Timestamp date;
    String time;
    String gender;
    String address;
    String id;
    String type;
    String name;
    String mobile;
    String email, orderId;
    String febricImage;
    String designImage;
    String work, febric, category, design, price, price1;
    private String cancelReason;
    private boolean cancelStatus;
    private boolean delivered;
    private Timestamp deliveredDate;
    private Timestamp dispatchedDate;
    private Timestamp transitDate;
    private boolean orderPlaced;
    private boolean isDispatched;
    private boolean isTransit;
    private Timestamp bookingDate;

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public Timestamp getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Timestamp deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public Timestamp getDispatchedDate() {
        return dispatchedDate;
    }

    public void setDispatchedDate(Timestamp dispatchedDate) {
        this.dispatchedDate = dispatchedDate;
    }

    public Timestamp getTransitDate() {
        return transitDate;
    }

    public void setTransitDate(Timestamp transitDate) {
        this.transitDate = transitDate;
    }

    public boolean isOrderPlaced() {
        return orderPlaced;
    }

    public void setOrderPlaced(boolean orderPlaced) {
        this.orderPlaced = orderPlaced;
    }

    public boolean isDispatched() {
        return isDispatched;
    }

    public void setDispatched(boolean dispatched) {
        isDispatched = dispatched;
    }

    public boolean isTransit() {
        return isTransit;
    }

    public void setTransit(boolean transit) {
        isTransit = transit;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public boolean isCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(boolean cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public String getDesignImage() {
        return designImage;
    }

    public void setDesignImage(String designImages) {
        designImage = designImages;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFebricImage() {
        return febricImage;
    }

    public void setFebricImage(String febricImage) {
        this.febricImage = febricImage;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getFebric() {
        return febric;
    }

    public void setFebric(String febric) {
        this.febric = febric;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
