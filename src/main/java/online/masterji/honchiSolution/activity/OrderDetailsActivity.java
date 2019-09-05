package online.masterji.honchiSolution.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.Cart;
import online.masterji.honchiSolution.domain.Order;
import online.masterji.honchiSolution.domain.User;


public class OrderDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final String TAG = "OrderDetailsActivity";
    Intent intent;
    String Title;
    String Address;
    String BookingDate;
    String BookingTimeSlot;
    String getUserId;
    String DeliveredDate;
    String OrderDate;
    Date Date;
    int Date1;
    int Date2;
    int Date3;
    String ProductId;
    String Price;
    Boolean isCancelStatus;
    String Id, order_Id, data, getTransitDate, DispatchedDate, orderDat, getDesign, getFebric, getOrderId, OrderId;
    String getFebricImage, Photo;
    String SubCategory;
    String SuperCategory;
    ImageView imagrOrder, imageDesignO, imageFebricO;
    TextView TVCancel, tvorderDate, TVEmail, tvNameO, tvorderId, tvAddressss, tvPriceP, tvPricePP, tvPriceOD, tvTitleOD, tvDeliver;
    String date;
    String Email, item, name;
    CircleImageView civUser;
    LinearLayout linearDesignO, linearFebricO, linearCancelREas, linearCancel, linearEmail;
    Spinner spinnerCancel;
    EditText etCancelReason;
    Button btnSubmit;
    private Serializable orderData;
    CardView cvEmai;
    int i = 0;
    List<Order> orderList;
    boolean Delivered, Dispatched, OrderPlaced, Transit;
    String TO, SUBJECT, MESSAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        String venName = bundle.getString("VENUE_NAME");
        Log.e(TAG, "onCreate: venName" + venName);
        initData();
        getIntentData();
        getUser();
        getOrderDetailsUser();
        setData();
        spinnerDataCancellation();

    }

    private void setData() {
        if (!TextUtils.isEmpty(Title)) {
            tvTitleOD.setText("" + Title);
        }
       /* if (!TextUtils.isEmpty(OrderDate)) {
            tvorderDate.setText("" + OrderDate);
        }*/
        if (!TextUtils.isEmpty(Price)) {
            String habitnumber = "COD :  " + "<b>" + "Rs " + Price + "</b> ";
            String habitnumber1 = " " + "<b>" + "Rs " + Price + "</b> ";
            tvPriceOD.setText(Html.fromHtml(habitnumber));
            tvPricePP.setText(Html.fromHtml(habitnumber1));
            tvPriceP.setText(Price);

            // tvPriceOD.setText("COD : Rs " + Price);

        }
        if (!TextUtils.isEmpty(getOrderId)) {
            Log.e(TAG, "onCreate: " + getOrderId);
            String habitnumber = "Order :  " + "<b>" + " " + getOrderId + "</b> ";


            tvorderId.setText(Html.fromHtml(habitnumber));


        }
        if (!TextUtils.isEmpty(getOrderId)) {
            Log.e(TAG, "onCreate: " + getOrderId);
            String habitnumber = "Order :  " + "<b>" + " " + getOrderId + "</b> ";


            tvorderId.setText(Html.fromHtml(habitnumber));


        }
        if (!TextUtils.isEmpty(Address)) {

            String[] items = Address.split(",");
            for (int i = 0; i < items.length; i++) {
                // tvAddressss.setText(" \n " + items[i] );
                try {
                    tvAddressss.setText(" " + items[0] + " \n " + items[1] + " \n " + items[2] + " \n " + items[3] + " \n " + items[4] + " \n " + items[5] + " \n " + items[6]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }
        if (!TextUtils.isEmpty(Address)) {

            String[] items = Address.split(",");
            for (int i = 0; i < items.length; i++) {
                // tvAddressss.setText(" \n " + items[i] );
                try {
                    tvAddressss.setText(" " + items[0] + " \n " + items[1] + " \n " + items[2] + " \n " + items[3] + " \n " + items[4] + " \n " + items[5] + " \n " + items[6]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }
        // getDate(Long.parseLong(OrderDate));
        if (!TextUtils.isEmpty(OrderDate)) {
            // tvDeliver.setText("Expected Delivery by " + date);
            try {
                String sampleString = OrderDate;
                String[] names = sampleString.split(" ");

                for (int i = 0; i < names.length; i++) {
                    String habitnumber = "Expected Delivery by " + "<b>" + names[0] + " " + names[1] + " " + names[2] + "</b> ";
                    tvDeliver.setText(Html.fromHtml(habitnumber));
                    // tvDeliver.setTypeface(null, Typeface.BOLD);
                    // tvDeliver.setText("Expected Delivery by " + names[0] + " " + names[1] + " " + names[2]+"</B>");
                }
            } catch (Exception e) {
                // tvUserName.setText(name);
                e.printStackTrace();
            }

        }


        if (!TextUtils.isEmpty(Photo)) {
            Glide.with(OrderDetailsActivity.this)
                    .load(Photo)
                    .centerInside()
                    .placeholder(R.drawable.ic_add_photo)
                    .into(imagrOrder);
        }
        if (!(getDesign == null)) {
            Glide.with(OrderDetailsActivity.this)
                    .load(getDesign)
                    .centerInside()
                    .placeholder(R.drawable.ic_add_photo)
                    .into(imageDesignO);
        } else {
            linearDesignO.setVisibility(View.GONE);
        }
        if (!(getFebric == null)) {
            Glide.with(OrderDetailsActivity.this)
                    .load(getFebric)
                    .centerInside()
                    .placeholder(R.drawable.ic_add_photo)
                    .into(imageFebricO);
        } else {
            linearFebricO.setVisibility(View.GONE);
        }
    }

    private void spinnerDataCancellation() {
        List<String> categories = new ArrayList<String>();
        categories.add("Cancel Reason(optional)");
        categories.add("Order created by mistake");
        categories.add("Items(s) would not arrive on time");
        categories.add("Shipping cost too high");
        categories.add("Item price too high");
        categories.add("Found cheaper somewhere else");
        categories.add("Need to change shipping address ");
        categories.add("Need to change billing address");
        categories.add("Need to change  payment Method");
        categories.add("Need to change shipping speed");
        categories.add("other");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerCancel.setAdapter(dataAdapter);

    }

    private void initData() {

        linearDesignO = findViewById(R.id.linearDesignO);
        linearFebricO = findViewById(R.id.linearFebricO);
        imageFebricO = findViewById(R.id.imageFebricO);
        imageDesignO = findViewById(R.id.imageDesignO);
        spinnerCancel = findViewById(R.id.spinnerCancel);
        etCancelReason = findViewById(R.id.etCancelReason);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvNameO = findViewById(R.id.tvNameO);
        tvPriceP = findViewById(R.id.tvPriceP);
        tvPricePP = findViewById(R.id.tvPricePP);
        imagrOrder = findViewById(R.id.imagrOrder);
        tvDeliver = findViewById(R.id.tvDeliver);
        tvorderId = findViewById(R.id.tvorderId);
        tvorderDate = findViewById(R.id.tvorderDate);
        tvPriceOD = findViewById(R.id.tvPriceOD);
        tvTitleOD = findViewById(R.id.tvTitleOD);
        tvAddressss = findViewById(R.id.tvAddressss);
        civUser = findViewById(R.id.civUser);
        linearEmail = findViewById(R.id.linearEmail);
        TVEmail = findViewById(R.id.TVEmail);
        linearCancel = findViewById(R.id.linearCancel);
        linearCancelREas = findViewById(R.id.linearCancelREas);
        TVCancel = findViewById(R.id.TVCancel);
        cvEmai = findViewById(R.id.cvEmai);

        btnSubmit.setOnClickListener(this);
        linearEmail.setOnClickListener(this);
        TVEmail.setOnClickListener(this);
        linearCancel.setOnClickListener(this);
        TVCancel.setOnClickListener(this);
        linearCancelREas.setOnClickListener(this);
        cvEmai.setOnClickListener(this);
        spinnerCancel.setOnItemSelectedListener(this);

    }

    private void getIntentData() {
        intent = getIntent();
        isCancelStatus = intent.getBooleanExtra("isCancelStatus", false);
        Log.e(TAG, "getIntentData:isCancelStatus=================== " + isCancelStatus);
        if (isCancelStatus) {
            linearCancel.setVisibility(View.GONE);
        } else {
            linearCancel.setVisibility(View.VISIBLE);
        }
        getOrderId = intent.getStringExtra("getOrderId");
        getFebricImage = intent.getStringExtra("getFebricImage");
        orderDat = intent.getStringExtra("orderDate()");
        getDesign = intent.getStringExtra("getDesign");
        getFebric = intent.getStringExtra("getFebric");
        getOrderId = intent.getStringExtra("getOrderId");
        order_Id = intent.getStringExtra("order_Id");
        data = intent.getStringExtra("data");
        ProductId = intent.getStringExtra("ProductId");
        Title = intent.getStringExtra("Title");
        OrderId = intent.getStringExtra("OrderId");
        orderData = intent.getSerializableExtra("new");
        getSupportActionBar().setTitle(Title);
        Price = intent.getStringExtra("Price");
        Id = intent.getStringExtra("Id");
        Photo = intent.getStringExtra("Photo");
        SubCategory = intent.getStringExtra("SubCategory");
        SuperCategory = intent.getStringExtra("SuperCategory");

        Address = intent.getStringExtra("Address");
       /* BookingDate = intent.getStringExtra("BookingDate");
        BookingTimeSlot = intent.getStringExtra("BookingTimeSlot");
        OrderDate = intent.getStringExtra("OrderDate");
        DeliveredDate = intent.getStringExtra("DeliveredDate");*/
        getUserId = intent.getStringExtra("getUserId");

        Log.e(TAG, "onCreate:orderDat=== " + orderDat);
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
        Log.e(TAG, "onCreate:OrderId=== " + OrderId);
        Log.e(TAG, "onCreate:getOrderId=== " + getOrderId);
        Log.e(TAG, "onCreate:data=== " + data);
        Log.e(TAG, "onCreate:orderData=== " + orderData);
        Log.e(TAG, "onCreate:order_Id=== " + order_Id);
        Log.e(TAG, "onCreate:getDesign=== " + getDesign);
        Log.e(TAG, "onCreate:getFebric=== " + getFebric);

    }

    private String toDate(long timestamp) {
        Date date = new Date(timestamp * 1000);
        Log.e(TAG, "toDate: " + timestamp * 1000);
        Log.e(TAG, "toDate: " + date);
        Log.e(TAG, "toDate: " + new SimpleDateFormat("yyyy-MM-dd").format(date));
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    private void getOrderDetailsUser() {
        Log.e(TAG, "getOderDetailsData: ");
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
                    Collections.reverse(orderList);
                  /*  Log.e(TAG, "onComplete: getUserId===" + orderList.get(0).getUserId());
                    Log.e(TAG, "onComplete:getTransitDate ===" + orderList.get(0).getTransitDate());
                    Log.e(TAG, "onComplete: getDispatchedDate===" + orderList.get(0).getDispatchedDate());
                    Log.e(TAG, "onComplete:getFebric ===" + orderList.get(0).getFebric());
                    Log.e(TAG, "onComplete: getDesign===" + orderList.get(0).getDesign());
                    Log.e(TAG, "onComplete: getUserId===" + orderList.get(0).getUserId());
                    Log.e(TAG, "onComplete:getDeliveredDate ===" + orderList.get(0).getDeliveredDate());
                    Log.e(TAG, "onComplete:getOrderDate ===" + orderList.get(0).getOrderDate());
                    Log.e(TAG, "onComplete: getBookingTimeSlot===" + orderList.get(0).getBookingTimeSlot());
                    Log.e(TAG, "onComplete: getBookingDate===" + orderList.get(0).getBookingDate());
                    Log.e(TAG, "onComplete:getAddress ===" + orderList.get(0).getAddress());
                    Log.e(TAG, "onComplete: getStatus===" + orderList.get(0).getStatus());

                 */

                    try {
                        Log.e(TAG, "onComplete: getCancelReason===" + orderList.get(0).getProducts().get(0).getCancelReason());
                        Log.e(TAG, "onComplete: isCancelStatus===" + orderList.get(0).getProducts().get(0).isCancelStatus());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getFebricImage = orderList.get(0).getFebric();
                    Address = orderList.get(0).getAddress();
                    tvAddressss.setText(Address);
                    if (!TextUtils.isEmpty(getFebricImage)) {
                        Glide.with(OrderDetailsActivity.this)
                                .load(getFebricImage)
                                .centerInside()
                                .placeholder(R.drawable.ic_add_photo)
                                .into(imageFebricO);
                    }
                    if (DeliveredDate != null) {
                        DeliveredDate = toDate(orderList.get(0).getDeliveredDate().getSeconds());
                        Log.e(TAG, "getDeliveredDate: ===" + toDate(orderList.get(0).getDeliveredDate().getSeconds()));

                    }
                    if (getTransitDate != null) {
                        getTransitDate = toDate(orderList.get(0).getTransitDate().getSeconds());
                        Log.e(TAG, "getTransitDate: ===" + toDate(orderList.get(0).getTransitDate().getSeconds()));

                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String dateString = formatter.format(new Date(String.valueOf((orderList.get(0).getOrderDate()))));
                    tvorderDate.setText("Order Date : " + dateString);

                    if (Date != null) {
                        OrderDate = toDate(orderList.get(0).getOrderDate().getSeconds());
                        tvorderDate.setText("Order Date : " + Date);
                        Log.e(TAG, "getOrderDate: ===" + toDate(orderList.get(0).getOrderDate().getSeconds()));

                    }
                    if (BookingDate != null) {
                        BookingDate = toDate(orderList.get(0).getBookingDate().getSeconds());
                    }
                    if (BookingTimeSlot != null) {
                        BookingTimeSlot = toDate(orderList.get(0).getBookingTimeSlot().getSeconds());


                    }
                    if (DispatchedDate != null) {
                        DispatchedDate = toDate(orderList.get(0).getDispatchedDate().getSeconds());
                        Log.e(TAG, "getDispatchedDate: ===" + getDate(orderList.get(0).getDispatchedDate().getSeconds()));

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

    private void sendEmail() {

        String message = "Your Email Id " + Email + System.getProperty("line.separator") + "Invoice Sent to Your Email id";


        new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.green_700)
                .setButtonsColorRes(R.color.black)
                .setIcon(R.drawable.ic_done)
                .setTitle("Email Send Successfully!")
                .setMessage(message)
                .setCancelable(false)

                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OrderDetailsActivity.this, OrderDetailsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })

                .show();

    }

    private void getUser() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("MJ_Users").document(Constants.Uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                if (user != null) {
                    name = user.getFullname();
                    Email = user.getEmail();
                    Email = user.getEmail();
                    tvNameO.setText(name);

                    try {
                        if (user.getPhoto() != null) {
                            if (!TextUtils.isEmpty(user.getPhoto())) {
                                Glide.with(OrderDetailsActivity.this)
                                        .load(user.getPhoto())
                                        .centerInside()
                                        .placeholder(R.drawable.ic_add_photo)
                                        .into(civUser);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
        if (item.equals("other")) {

            etCancelReason.setVisibility(View.VISIBLE);
        } else {
            etCancelReason.setVisibility(View.GONE);
        }
        // Showing selected spinner item
        // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                Log.e(TAG, "onClick:item " + item);
                if (item != null) {
                    cancelOrder(item);
                } else {
                    cancelOrder(etCancelReason.getText().toString().trim());
                    Log.e(TAG, "onClick:item " + etCancelReason.getText().toString().trim());

                }

                break;
            case R.id.linearEmail:
               // sendEmail();
                break;
            case R.id.TVEmail:
               // sendEmail();
                break;
            case R.id.cvEmai:
               // sendEmail();
                break;
            case R.id.TVCancel:
                i = 1;
                if (i == 1) {
                    linearCancelREas.setVisibility(View.VISIBLE);

                    i = 0;
                } else {
                    linearCancelREas.setVisibility(View.INVISIBLE);
                    i = 1;
                }

                break;
            case R.id.linearCancel:

                i = 1;
                if (i == 1) {
                    linearCancelREas.setVisibility(View.VISIBLE);
                    cancelOrder(item);
                    i = 0;
                } else {
                    linearCancelREas.setVisibility(View.INVISIBLE);
                    i = 1;
                }

                break;
        }
    }

    Cart cart;
    List<String> productList = new ArrayList<>();
    List<Cart> buyNowList = new ArrayList<>();

    private void cancelOrder(final String item) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        /* CollectionReference collectionReference = db.collection("Order");
         */
        Log.e(TAG, "getOderDetailsData: ");

        cart = new Cart();
        cart.setId(Id);
        // cart.setProductId(product.getId());
        cart.setPhoto(Photo);
        cart.setPrice(Price);
        cart.setTitle(Title);
        cart.setCancelStatus(true);
        cart.setCancelReason(item);
        cart.setSuperCategory(SuperCategory);
        cart.setSubCategory(SubCategory);
        buyNowList.add(cart);

        db.collection("Order").document(getOrderId).update("products", buyNowList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.e(TAG, "onComplete: " + task.getResult());


                dialoge();
            }
        });


    }

    private void dialoge() {
        String message = "Your Order Id " + getOrderId +
                System.getProperty("line.separator")
                + "your order is cancelled";


        new LovelyStandardDialog(OrderDetailsActivity.this, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.green_700)
                .setButtonsColorRes(R.color.black)
                .setIcon(R.drawable.ic_done)
                .setTitle("Order cancelled !")
                .setMessage(message)
                .setCancelable(false)

                .setPositiveButton("Done", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OrderDetailsActivity.this, MainActivity.class);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).show();
        ;

    }

   /* public void sendEmail(View view) {
        Log.e(TAG, "sendEmailccc: ");
        String message = "Your Email Id " + Email +
                System.getProperty("line.separator")
                + "Invoice Sent to Your Email id";


        new LovelyStandardDialog(OrderDetailsActivity.this, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.green_700)
                .setButtonsColorRes(R.color.black)
                .setIcon(R.drawable.ic_done)
                .setTitle("Email Send Successfully!")
                .setMessage(message)
                .setCancelable(false)

                .setPositiveButton("Done", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OrderDetailsActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                });


    }*/
}
