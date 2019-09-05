package online.masterji.honchiSolution.domain;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.List;

public class Order {
    private String userId;
    private boolean delivered;
    private Timestamp deliveredDate;
    private List<Cart> products;
    private String address;
    private Timestamp bookingDate;
    private Timestamp bookingTimeSlot;
    private Date orderDate;
    private String orderId;
    private Timestamp dispatchedDate;
    private Timestamp transitDate;
    private boolean orderPlaced;
    private boolean isDispatched;
    private boolean isTransit;
    private String febric;
    private String design;
    private Timestamp timestamp;
    int status;
    String gender;
    String type;
    String name;
    String mobile;
    String email;
    String febricImage;
    String work, category;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /* static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    public Date getDateFromString(String datetoSaved){

        try {
            Date date = format.parse(datetoSaved);

            return date ;
        } catch (ParseException e){
            return null ;
        }

    }*/

    public Timestamp getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Timestamp deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Timestamp getBookingTimeSlot() {
        return bookingTimeSlot;
    }

    public void setBookingTimeSlot(Timestamp bookingTimeSlot) {
        this.bookingTimeSlot = bookingTimeSlot;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<Cart> getProducts() {
        return products;
    }

    public void setProducts(List<Cart> products) {
        this.products = products;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



}
