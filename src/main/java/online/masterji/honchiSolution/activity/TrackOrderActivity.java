package online.masterji.honchiSolution.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.BookingTailor;
import online.masterji.honchiSolution.domain.Order;

public class TrackOrderActivity extends AppCompatActivity {
    private static final String TAG = "TrackOrderActivity";
    TextView tvEstiDeli, tvTime1, tvTime2, tvTime3, tvTime4, tvTime5, tvStatus1, tvStatus2, tvStatus3, tvStatus4, tvStatus5, tvDate1, tvDate2, tvDate3, tvDate4, tvDate5, TVOrderNum;
    ImageView imageCircle1, imageCircle2, imageCircle3, imageCircle4, imageCircle5, imageLine2, imageLine1, imageLine3, imageLine4, imageLine5;
    Intent intent;
    String Title, DispatchedDate, getTransitDate, getOrderId, Name, Design, Date, Category, Address, BookingDate, BookingTimeSlot, getUserId, DeliveredDate, OrderDate, order_Id, ProductId, Price, Id, Photo, SubCategory, SuperCategory;
    LinearLayout linear1, linear2, linear3, linear4;
    boolean Delivered, Dispatched, OrderPlaced, Transit;
    List<Order> orderList = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    String dateString, Track, dateStringDispatchedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent = getIntent();
        Track = intent.getStringExtra("Track");
        if (Track.equals("Track")) {
            Log.e(TAG, "onCreate:Track  " + Track);
            getBookingData();

        } else {
            Log.e(TAG, "onCreate:Track  " + Track);

            getData();

        }

        findViewById();
        if (Track.equals("Track")) {
            Log.e(TAG, "onCreate: ");
            if (getOrderId != null && !getOrderId.isEmpty()) {
                getOderDetailsBookingData(getOrderId);
            } else if (order_Id != null && !order_Id.isEmpty()) {
                getOderDetailsBookingData(order_Id);
            }

        } else {
            if (getOrderId != null && !getOrderId.isEmpty()) {
                getOderDetailsData(getOrderId);
            } else if (order_Id != null && !order_Id.isEmpty()) {
                getOderDetailsData(order_Id);
            }

        }

       /* if (getOrderId != null && !getOrderId.isEmpty()) {
            getOderDetailsData(getOrderId);
        } else if (order_Id != null && !order_Id.isEmpty()) {
            getOderDetailsData(order_Id);
        }*/
        setData();

    }

    List<BookingTailor> bookingTailors = null;

    private void getOderDetailsBookingData(String getOrderId) {
        Log.e(TAG, "getOderDetailsBookingData: " + getOrderId);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("BookingTailor");
        db.collection("BookingTailor").whereEqualTo("orderId", getOrderId).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    }
                });
        /*.orderBy("date", Query.Direction.ASCENDING).limit(10)*/
        collectionReference.whereEqualTo("orderId", getOrderId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());


                    }
                    try {
                        bookingTailors = task.getResult().toObjects(BookingTailor.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        if (bookingTailors.get(0).getDate() != null) {
                            dateString = formatter.format(new Date(String.valueOf((bookingTailors.get(0).getDate().getSeconds()))));
                            tvEstiDeli.setText("" + dateString);
                            tvDate1.setText("" + dateString);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        OrderDate = toDate(bookingTailors.get(0).getDate().getSeconds());
                        tvEstiDeli.setText("" + OrderDate);
                        tvDate1.setText("" + OrderDate);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                  /*  if (orderList.get(0).getOrderId() != null && !orderList.get(0).getOrderId().isEmpty()) {
                        String OrderId = orderList.get(0).getOrderId();
                        String habitnumber = "<b>" + OrderId + "</b> ";
                        TVOrderNum.setText(Html.fromHtml(habitnumber));

                    }*/
                    if (bookingTailors != null) {
                        BookingTimeSlot = toDate(bookingTailors.get(0).getDate().getSeconds());

                    }

                    if (bookingTailors.get(0).getDispatchedDate() != null) {
                        DispatchedDate = toDate(bookingTailors.get(0).getDispatchedDate().getSeconds());
                        Log.e(TAG, "getDispatchedDate: ===" + getDate(bookingTailors.get(0).getDispatchedDate().getSeconds()));
                        Log.e(TAG, "getDispatchedDate: ===" + getDate(bookingTailors.get(0).getDispatchedDate().getSeconds() + 1));
                        tvDate2.setText(DispatchedDate);


                    }
                    if (bookingTailors.get(0).getTransitDate() != null) {
                        getTransitDate = toDate(bookingTailors.get(0).getTransitDate().getSeconds());
                        Log.e(TAG, "getTransitDate: ===" + getDate(bookingTailors.get(0).getTransitDate().getSeconds()));
                        tvDate3.setText(getTransitDate);

                    }
                    if (bookingTailors.get(0).getDeliveredDate() != null) {
                        DeliveredDate = toDate(bookingTailors.get(0).getDeliveredDate().getSeconds());
                        Log.e(TAG, "getDispatchedDate: ===" + getDate(bookingTailors.get(0).getTransitDate().getSeconds()));
                        tvDate4.setText(DeliveredDate);

                    }

                    try {
                        Transit = bookingTailors.get(0).isTransit();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        OrderPlaced = bookingTailors.get(0).isOrderPlaced();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Dispatched = bookingTailors.get(0).isDispatched();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Delivered = bookingTailors.get(0).isDelivered();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String UID = Constants.Uid;


                } else {
                    Log.e(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void getBookingData() {

        intent = getIntent();

        // DispatchedDate = intent.getStringExtra("DispatchedDate");

        Delivered = intent.getBooleanExtra("Delivered", false);
        Dispatched = intent.getBooleanExtra("Dispatched", false);
        OrderPlaced = intent.getBooleanExtra("OrderPlaced", false);
        Transit = intent.getBooleanExtra("Transit", false);
        Log.e(TAG, "Delivered:======= " + Delivered);
        Log.e(TAG, "Dispatched:===== " + Dispatched);
        Log.e(TAG, "OrderPlaced: =========" + OrderPlaced);
        Log.e(TAG, "Transit:========= " + Transit);
        //getTransitDate = intent.getStringExtra("TransitDate");


        order_Id = intent.getStringExtra("order_Id");
        ProductId = intent.getStringExtra("ProductId");
        Title = intent.getStringExtra("Title");
        getSupportActionBar().setTitle(Title);
        Price = intent.getStringExtra("Price");
        Id = intent.getStringExtra("Id");
        Photo = intent.getStringExtra("Photo");
        SubCategory = intent.getStringExtra("SubCategory");
        SuperCategory = intent.getStringExtra("SuperCategory");

        getOrderId = intent.getStringExtra("getOrderId");
        Address = intent.getStringExtra("Address");
        Category = intent.getStringExtra("Category");
        Date = intent.getStringExtra("Date");
        Design = intent.getStringExtra("Design");
        Name = intent.getStringExtra("Name");
        // BookingDate = intent.getStringExtra("BookingDate");
        /// BookingTimeSlot = intent.getStringExtra("BookingTimeSlot");
        //  OrderDate = intent.getStringExtra("OrderDate");
        // DeliveredDate = intent.getStringExtra("DeliveredDate");
        getUserId = intent.getStringExtra("getUserId");
        Log.e(TAG, "onCreate:ProductId=== " + ProductId);
        Log.e(TAG, "onCreate:Id=== " + Id);
        Log.e(TAG, "onCreate:Title=== " + Title);
        Log.e(TAG, "onCreate:Price=== " + Price);
        Log.e(TAG, "onCreate:Photo=== " + Photo);
        Log.e(TAG, "onCreate:Address=== " + Address);
        Log.e(TAG, "onCreate:BookingDate=== " + BookingDate);
        Log.e(TAG, "onCreate:BookingTimeSlot=== " + BookingTimeSlot);
        Log.e(TAG, "onCreate:OrderDate=== " + OrderDate);
        Log.e(TAG, "onCreate:DeliveredDate=== " + DeliveredDate);
        Log.e(TAG, "onCreate:getUserId=== " + getUserId);
        Log.e(TAG, "onCreate:getOrderId=== " + getOrderId);

        if (!TextUtils.isEmpty(Name)) {
            getSupportActionBar().setTitle(Name);

           /* tvNameO.setText("" + Name);
            tvNameBooking.setText("Name : " + Name);
          */
            Log.e(TAG, "onCreate: Name" + Name);
        }

    }

    private void getOderDetailsData(String getOrderId) {
        Log.e(TAG, "getOderDetailsData:order_Id " + getOrderId);
        Log.e(TAG, "getOderDetailsData:getOrderId " + getOrderId);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Order");
        db.collection("Order").whereEqualTo("orderId", getOrderId).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //  Toast.makeText(getContext(), "Order", Toast.LENGTH_SHORT).show();

                    }
                });

        collectionReference.whereEqualTo("orderId", getOrderId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.e(TAG, document.getId() + " => " + document.getData());

                    }
                    orderList = task.getResult().toObjects(Order.class);

                    try {
                        if (orderList.get(0).getOrderDate() != null) {
                            dateString = formatter.format(new Date(String.valueOf((orderList.get(0).getOrderDate()))));
                            tvEstiDeli.setText("" + dateString);
                            tvDate1.setText("" + dateString);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    /*try {
                        OrderDate = toDate(orderList.get(0).getOrderDate().getSeconds());
                        tvEstiDeli.setText("" + OrderDate);
                        tvDate1.setText("" + OrderDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/

                    if (orderList.get(0).getOrderId() != null && !orderList.get(0).getOrderId().isEmpty()) {
                        String OrderId = orderList.get(0).getOrderId();
                        String habitnumber = "<b>" + OrderId + "</b> ";
                        TVOrderNum.setText(Html.fromHtml(habitnumber));

                    }
                    if (BookingTimeSlot != null) {
                        BookingTimeSlot = toDate(orderList.get(0).getBookingTimeSlot().getSeconds());

                    }

                    if (orderList.get(0).getDispatchedDate() != null) {
                        DispatchedDate = toDate(orderList.get(0).getDispatchedDate().getSeconds());
                        Log.e(TAG, "getDispatchedDate: ===" + getDate(orderList.get(0).getDispatchedDate().getSeconds()));
                        tvDate2.setText(DispatchedDate);

                    }
                    if (orderList.get(0).getTransitDate() != null) {
                        getTransitDate = toDate(orderList.get(0).getTransitDate().getSeconds());
                        Log.e(TAG, "getTransitDate: ===" + getDate(orderList.get(0).getTransitDate().getSeconds()));
                        tvDate3.setText(getTransitDate);

                    }
                    if (orderList.get(0).getDeliveredDate() != null) {
                        DeliveredDate = toDate(orderList.get(0).getDeliveredDate().getSeconds());
                        Log.e(TAG, "getDispatchedDate: ===" + getDate(orderList.get(0).getTransitDate().getSeconds()));
                        tvDate4.setText(DeliveredDate);

                    }

                    Transit = orderList.get(0).isTransit();
                    OrderPlaced = orderList.get(0).isOrderPlaced();
                    Dispatched = orderList.get(0).isDispatched();
                    Delivered = orderList.get(0).isDelivered();
                    String UID = Constants.Uid;


                } else {
                    Log.e(TAG, "Error getting documents: ", task.getException());
                }

            }
        });
    }


    private String toDate(long timestamp) {
        Date date = new Date(timestamp * 1000);
        Log.e(TAG, "toDate: " + timestamp * 1000);
        Log.e(TAG, "toDate: " + date);
        Log.e(TAG, "toDate: " + new SimpleDateFormat("dd-MM-yyyy").format(date));
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    private void setData() {
        if (!TextUtils.isEmpty(order_Id)) {

            String habitnumber = "<b>" + order_Id + "</b> ";
            TVOrderNum.setText(Html.fromHtml(habitnumber));


        }

       /* SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(String.valueOf(OrderDate)));
        tvEstiDeli.setText("Order Date : " +dateString);
*/
        Log.e(TAG, "setData: OrderDate" + OrderDate);
        /*if (!TextUtils.isEmpty(OrderDate)) {
            try {
                String sampleString = OrderDate;
                String[] names = sampleString.split(" ");

                for (int i = 0; i < names.length; i++) {
                    String habitnumber = "<b>" + names[2] + " " + names[1] + "</b> ";
                    //tvEstiDeli.setText(Html.fromHtml(habitnumber));
                    tvEstiDeli.setText(OrderDate);
                }
            } catch (Exception e) {
                // tvUserName.setText(name);
                e.printStackTrace();
            }

        }*/

        if (OrderPlaced) {
            //  String dateString = formatter.format(new Date(dateString));
            // tvEstiDeli.setText("" + dateString);

            if (!TextUtils.isEmpty(dateString)) {
                tvEstiDeli.setText("" + dateString);

            } else {
                tvEstiDeli.setText("");
            }
            if (!TextUtils.isEmpty(dateString)) {
                tvDate1.setText("" + dateString);

            } else {
                tvDate1.setText("");
            }
          /*  tvDate1.setText(OrderDate);
            tvTime1.setText(OrderDate);
           */
            String habitnumber = "<b>" + "Order Placed" + "</b> ";

            tvStatus1.setText(Html.fromHtml(habitnumber));

            linear1.setBackground(getResources().getDrawable(R.drawable.circle));
            imageCircle1.setImageResource(R.drawable.ic_shopping_cart_white);
            imageLine1.setImageResource(R.drawable.ic_line_green);

            linear2.setBackground(getResources().getDrawable(R.drawable.circle_white));
            imageCircle2.setImageResource(R.drawable.ic_store_yellow_24dp);
            imageLine2.setImageResource(R.drawable.ic_line);

            linear3.setBackground(getResources().getDrawable(R.drawable.circle_white));
            imageCircle3.setImageResource(R.drawable.ic_local_shipping_yello_24dp);
            imageLine3.setImageResource(R.drawable.ic_line);

            linear4.setBackground(getResources().getDrawable(R.drawable.circle_white));
            imageCircle4.setImageResource(R.drawable.ic_tick);
            imageLine4.setImageResource(R.drawable.ic_line);


        }
        if (Dispatched) {
         /*   tvDate1.setText(OrderDate);
            tvTime1.setText(OrderDate);
          */
            String habitnumber1 = "<b>" + "Order Placed" + "</b> ";
            tvStatus1.setText(Html.fromHtml(habitnumber1));
            //tvEstiDeli.setText("" + dateString);

            if (!TextUtils.isEmpty(dateString)) {
                tvEstiDeli.setText("" + dateString);

            }
            if (!TextUtils.isEmpty(dateString)) {
                tvDate1.setText("" + dateString);

            }
            if (DispatchedDate != null) {
                tvDate2.setText(getDate(Long.parseLong(DispatchedDate)));

            }

          /*  tvDate2.setText(OrderDate);
            tvTime2.setText(OrderDate);
           */
            String habitnumber = "<b>" + "Order Dispatch" + "</b> ";
            tvStatus2.setText(Html.fromHtml(habitnumber));

            linear1.setBackground(getResources().getDrawable(R.drawable.circle));
            imageCircle1.setImageResource(R.drawable.ic_shopping_cart_white);
            imageLine1.setImageResource(R.drawable.ic_line_green);

            linear2.setBackground(getResources().getDrawable(R.drawable.circle));
            imageCircle2.setImageResource(R.drawable.ic_store_white_24dp);
            imageLine2.setImageResource(R.drawable.ic_line_green);

            linear3.setBackground(getResources().getDrawable(R.drawable.circle_white));
            imageCircle3.setImageResource(R.drawable.ic_local_shipping_yello_24dp);
            imageLine3.setImageResource(R.drawable.ic_line);


            linear4.setBackground(getResources().getDrawable(R.drawable.circle_white));
            imageCircle4.setImageResource(R.drawable.ic_tick);
            imageLine4.setImageResource(R.drawable.ic_line);

        }
        if (Transit) {
            String habitnumber1 = "<b>" + "Order Placed" + "</b> ";
            tvStatus1.setText(Html.fromHtml(habitnumber1));

            if (!TextUtils.isEmpty(dateString)) {
                tvDate1.setText("" + dateString);

            }
            if (DispatchedDate != null) {
                tvDate2.setText(getDate(orderList.get(0).getDispatchedDate().getSeconds()));

            }

            if (getTransitDate != null) {
                tvDate3.setText(getTransitDate);

            }


            // tvDate1.setText(OrderDate);
            // tvTime1.setText(OrderDate);
            String habitnumber11 = "<b>" + "Order Placed" + "</b> ";
            tvStatus1.setText(Html.fromHtml(habitnumber11));


            //  tvDate2.setText(OrderDate);
            //  tvTime2.setText(OrderDate);
            String habitnumber2 = "<b>" + "Order Dispatch" + "</b> ";
            tvStatus2.setText(Html.fromHtml(habitnumber2));

           /* tvDate3.setText(OrderDate);
            tvTime3.setText(OrderDate);
            */
            String habitnumber = "<b>" + "Order in transit" + "</b> ";
            tvStatus3.setText(Html.fromHtml(habitnumber));

            linear1.setBackground(getResources().getDrawable(R.drawable.circle));
            imageCircle1.setImageResource(R.drawable.ic_shopping_cart_white);
            imageLine1.setImageResource(R.drawable.ic_line_green);

            linear2.setBackground(getResources().getDrawable(R.drawable.circle));
            imageCircle2.setImageResource(R.drawable.ic_store_white_24dp);
            imageLine2.setImageResource(R.drawable.ic_line_green);

            linear3.setBackground(getResources().getDrawable(R.drawable.circle));
            imageCircle3.setImageResource(R.drawable.ic_local_shipping_black_24dp);
            imageLine3.setImageResource(R.drawable.ic_line_green);

            linear4.setBackground(getResources().getDrawable(R.drawable.circle_white));
            imageCircle4.setImageResource(R.drawable.ic_tick);
            imageLine4.setImageResource(R.drawable.ic_line);

        }
        if (Delivered) {
            String habitnumber1 = "<b>" + "Order Placed" + "</b> ";
            tvStatus1.setText(Html.fromHtml(habitnumber1));

            if (!TextUtils.isEmpty(dateString)) {
                tvDate1.setText("" + dateString);

            }
           /* if (DispatchedDate != null) {
                tvDate2.setText(getDate(Long.parseLong(DispatchedDate)));

            }*/
            if (!TextUtils.isEmpty(DispatchedDate)) {
                tvDate2.setText(getDate(Long.parseLong(DispatchedDate)));

            }
            if (!TextUtils.isEmpty(getTransitDate)) {
                tvDate3.setText(getDate(Long.parseLong(getTransitDate)));

            }
            if (!TextUtils.isEmpty(DeliveredDate)) {
                tvDate4.setText(getDate(Long.parseLong(DeliveredDate)));

            }

           /* if (getTransitDate != null) {
                tvDate3.setText(getTransitDate);

            }

            if (DeliveredDate != null) {
                tvDate4.setText(DeliveredDate);

            }*/
            // tvDate1.setText(OrderDate);

            /*if (!TextUtils.isEmpty(OrderDate)) {
                String Time = OrderDate.substring(11, 16);

                try {
                    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                    final Date dateObj = sdf.parse(Time);
                    String value = new SimpleDateFormat("K:mm").format(dateObj);
                    int val = Integer.parseInt(value.substring(0, 1));
                    String value2 = (val >= 12) ? "AM" : "PM";

                    tvTime1.setText(value + "\n" + value2);
                    tvTime1.setText(OrderDate);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            String habitnumber11 = "<b>" + "Order Placed" + "</b> ";

            tvStatus1.setText(Html.fromHtml(habitnumber11));
            if (!TextUtils.isEmpty(OrderDate)) {
                String Time2 = OrderDate.substring(11, 16);

                try {
                    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                    final Date dateObj = sdf.parse(Time2);
                    String value = new SimpleDateFormat("K:mm").format(dateObj);
                    int val = Integer.parseInt(value.substring(0, 1));
                    String value2 = (val >= 12) ? "AM" : "PM";

                    tvTime2.setText(value + "\n" + value2);
                    tvTime2.setText(OrderDate);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            String habitnumber2 = "<b>" + "Order Dispatch" + "</b> ";

            tvStatus2.setText(Html.fromHtml(habitnumber2));
            // tvDate3.setText(OrderDate);
            // tvTime3.setText(OrderDate);
            if (!TextUtils.isEmpty(OrderDate)) {
                String Time3 = OrderDate.substring(11, 16);

                try {
                    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                    final Date dateObj = sdf.parse(Time3);
                    String value = new SimpleDateFormat("K:mm").format(dateObj);
                    int val = Integer.parseInt(value.substring(0, 1));
                    String value2 = (val >= 12) ? "AM" : "PM";

                    //  tvTime3.setText(value + "\n" + value2);
                    SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    sfd.format(new Date(OrderDate));
                    Log.e(TAG, "setData:========== " + sfd.format(new Date(OrderDate)));
                    tvTime3.setText(sfd.format(new Date(OrderDate)));
                    // tvTime3.setText(OrderDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }*/
            String habitnumber3 = "<b>" + "Order in transit" + "</b> ";

            tvStatus3.setText(Html.fromHtml(habitnumber3));
            // tvDate4.setText(OrderDate);
            //tvTime4.setText(OrderDate);
          /*  if (!TextUtils.isEmpty(OrderDate)) {
                String Time4 = OrderDate.substring(11, 16);

                try {
                    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                    final Date dateObj = sdf.parse(Time4);
                    String value = new SimpleDateFormat("K:mm").format(dateObj);
                    int val = Integer.parseInt(value.substring(0, 1));
                    String value2 = (val >= 12) ? "AM" : "PM";

                    // tvTime4.setText(value + "\n" + value2);
                    tvTime4.setText(OrderDate);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }*/
            String habitnumber = "<b>" + "Delivered Successfully" + "</b> ";

            tvStatus4.setText(Html.fromHtml(habitnumber));
            linear4.setBackground(getResources().getDrawable(R.drawable.circle));
            imageCircle4.setImageResource(R.drawable.ic_thumb_up_white);
            imageLine4.setImageResource(R.drawable.ic_line_green);


            linear2.setBackground(getResources().getDrawable(R.drawable.circle));
            imageCircle2.setImageResource(R.drawable.ic_store_white_24dp);
            imageLine2.setImageResource(R.drawable.ic_line_green);

            linear3.setBackground(getResources().getDrawable(R.drawable.circle));
            imageCircle3.setImageResource(R.drawable.ic_local_shipping_black_24dp);
            imageLine3.setImageResource(R.drawable.ic_line_green);

            linear1.setBackground(getResources().getDrawable(R.drawable.circle));
            imageCircle1.setImageResource(R.drawable.ic_tick_white);
            imageLine1.setImageResource(R.drawable.ic_line_green);


        }

    }

    private void findViewById() {

        TVOrderNum = findViewById(R.id.TVOrderNum);
        tvEstiDeli = findViewById(R.id.tvEstiDeli);
        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        linear3 = findViewById(R.id.linear3);
        linear4 = findViewById(R.id.linear4);


        tvTime1 = findViewById(R.id.tvTime1);
        tvTime2 = findViewById(R.id.tvTime2);
        tvTime3 = findViewById(R.id.tvTime3);
        tvTime4 = findViewById(R.id.tvTime4);
        //  tvTime5 = findViewById(R.id.tvTime5);

        tvStatus1 = findViewById(R.id.tvStatus1);
        tvStatus2 = findViewById(R.id.tvStatus2);
        tvStatus3 = findViewById(R.id.tvStatus3);
        tvStatus4 = findViewById(R.id.tvStatus4);
        //  tvStatus5 = findViewById(R.id.tvStatus5);

        tvDate1 = findViewById(R.id.tvDate1);
        tvDate2 = findViewById(R.id.tvDate2);
        tvDate3 = findViewById(R.id.tvDate3);
        tvDate4 = findViewById(R.id.tvDate4);
        // tvDate5 = findViewById(R.id.tvDate5);

        imageCircle1 = findViewById(R.id.imageCircle1);
        imageCircle2 = findViewById(R.id.imageCircle2);
        imageCircle3 = findViewById(R.id.imageCircle3);
        imageCircle4 = findViewById(R.id.imageCircle4);
        // imageCircle5 = findViewById(R.id.imageCircle5);

        imageLine1 = findViewById(R.id.imageLine1);
        imageLine2 = findViewById(R.id.imageLine2);
        imageLine3 = findViewById(R.id.imageLine3);
        imageLine4 = findViewById(R.id.imageLine4);

    }

    private void getData() {

        // DispatchedDate = intent.getStringExtra("DispatchedDate");

        Delivered = intent.getBooleanExtra("Delivered", false);
        Dispatched = intent.getBooleanExtra("Dispatched", false);
        OrderPlaced = intent.getBooleanExtra("OrderPlaced", false);
        Transit = intent.getBooleanExtra("Transit", false);
        Log.e(TAG, "Delivered:======= " + Delivered);
        Log.e(TAG, "Dispatched:===== " + Dispatched);
        Log.e(TAG, "OrderPlaced: =========" + OrderPlaced);
        Log.e(TAG, "Transit:========= " + Transit);
        //getTransitDate = intent.getStringExtra("TransitDate");


        ProductId = intent.getStringExtra("ProductId");
        Title = intent.getStringExtra("Title");
        getSupportActionBar().setTitle(Title);
        Price = intent.getStringExtra("Price");
        Id = intent.getStringExtra("Id");
        Photo = intent.getStringExtra("Photo");
        SubCategory = intent.getStringExtra("SubCategory");
        SuperCategory = intent.getStringExtra("SuperCategory");

        getOrderId = intent.getStringExtra("getOrderId");
        Address = intent.getStringExtra("Address");
        // BookingDate = intent.getStringExtra("BookingDate");
        /// BookingTimeSlot = intent.getStringExtra("BookingTimeSlot");
        //  OrderDate = intent.getStringExtra("OrderDate");
        // DeliveredDate = intent.getStringExtra("DeliveredDate");
        getUserId = intent.getStringExtra("getUserId");
        Log.e(TAG, "onCreate:ProductId=== " + ProductId);
        Log.e(TAG, "onCreate:Id=== " + Id);
        Log.e(TAG, "onCreate:Title=== " + Title);
        Log.e(TAG, "onCreate:Price=== " + Price);
        Log.e(TAG, "onCreate:Photo=== " + Photo);
        Log.e(TAG, "onCreate:Address=== " + Address);
        Log.e(TAG, "onCreate:BookingDate=== " + BookingDate);
        Log.e(TAG, "onCreate:BookingTimeSlot=== " + BookingTimeSlot);
        Log.e(TAG, "onCreate:OrderDate=== " + OrderDate);
        Log.e(TAG, "onCreate:DeliveredDate=== " + DeliveredDate);
        Log.e(TAG, "onCreate:getUserId=== " + getUserId);
        Log.e(TAG, "onCreate:getOrderId=== " + getOrderId);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}
