package online.masterji.honchiSolution.constant;

import android.Manifest;

import java.util.ArrayList;
import java.util.List;

import online.masterji.honchiSolution.domain.Cart;


public class Constants {

    public static final String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.ACCESS_COARSE_LOCATION
            , Manifest.permission.CAMERA
            , Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.CALL_PHONE};


    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME = "com.google.android.gms.location.sample.locationaddress";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";


    public static String tailorMobile;
    public static String userMobile;
    public static String Uid;
    public static String TOKEN;
    public static String TOKEN_new;
    public static String NAME;
    public static String WITH_NAME;
    public static String PHOTO;


    public static List<Cart> cartList = new ArrayList<>();
    public static List<String> orderrList = new ArrayList<>();
    public static List<String> orderrList1 = new ArrayList<>();


    public class Role {
        public static final int USER = 1;
        public static final int TAILOR = 2;
    }

    public class OrderStatus {
        public static final int REJECTED = 1;
        public static final int WAITING_FOR_CONFIRMATION = 2;
        public static final int CONFIRMED = 3;
        public static final int IN_PROCESS = 4;
        public static final int OUT_FOR_DELIVERY = 5;
        public static final int DELIVERED = 6;
        public static final int CANCELED = 7;
        public static final int REFUND = 8;
        public static final int RETRUN = 9;

    }

    public class OrderStatusString {
        public static final String REJECTED = "REJECTED";
        public static final String WAITING_FOR_CONFIRMATION = "WAITING FOR CONFIRMATION";
        public static final String CONFIRMED = "CONFIRMED";
        public static final String IN_PROCESS = "IN_PROCESS";
        public static final String OUT_FOR_DELIVERY = "OUT FOR DELIVERY";
        public static final String DELIVERED = "DELIVERED";
        public static final String CANCELED = "CANCELED";
        public static final String REFUND = "REFUND";
        public static final String RETRUN = "RETRUN";

    }


}
