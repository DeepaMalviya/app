/*
package online.masterji.honchiSolution.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.Cart;
import online.masterji.honchiSolution.domain.Order;
import online.masterji.honchiSolution.domain.User;
import online.masterji.honchiSolution.util.SnackBarUtil;
import online.masterji.honchiSolution.util.WaitDialog;

import static android.view.View.GONE;

public class PlaceOrderActivity1 extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "PlaceOrderActivity";

    RelativeLayout parentLayout;
    EditText etAddress, etBookingDate, etBookingTime;
    CheckBox cbTermConditions;
    ImageView ivTermConditionInfo;
    Button btnPlaceOrder;
    WaitDialog waitDialog;

    Cart buyNowCart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        waitDialog = new WaitDialog(this);
        getUser();

        if (getIntent().getExtras() != null) {
            buyNowCart = (Cart) getIntent().getExtras().getSerializable("buy_now");
        }
    }

    private void initView() {

        parentLayout = findViewById(R.id.parentLayout);
        etAddress = findViewById(R.id.etAddress);
        etBookingDate = findViewById(R.id.etBookingDate);
        etBookingTime = findViewById(R.id.etBookingTime);
        cbTermConditions = findViewById(R.id.cbTermConditions);
        ivTermConditionInfo = findViewById(R.id.ivTermConditionInfo);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(this);
        etBookingDate.setOnClickListener(this);
        etBookingTime.setOnClickListener(this);
        ivTermConditionInfo.setOnClickListener(this);


        btnPlaceOrder.setVisibility(GONE);

        cbTermConditions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnPlaceOrder.setVisibility(View.VISIBLE);
                } else {
                    btnPlaceOrder.setVisibility(GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivTermConditionInfo:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.btnPlaceOrder:
                placeOrder();
                break;
            case R.id.etBookingDate:
                showDataPicker();
                break;
            case R.id.etBookingTime:
                showTimePicker();
                break;
        }
    }


    private void getUser() {
        waitDialog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("MJ_Users").document(Constants.Uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                waitDialog.dismiss();
                User user = documentSnapshot.toObject(User.class);
                if (!TextUtils.isEmpty(user.getAddress()))
                    etAddress.setText(user.getAddress());

            }


        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        waitDialog.dismiss();
                    }
                });
    }

    private void placeOrder() {
        String address = etAddress.getText().toString().trim();
        String date = etBookingDate.getText().toString().trim();
        String time = etBookingTime.getText().toString().trim();

        if (address.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayout, "Enter Address");
            return;
        } else if (date.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayout, "Enter Booking Date");
            return;
        } else if (time.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayout, "Enter Booking Time ");
            return;
        }

        waitDialog.show();

        final String orderId = Constants.userMobile + "_" + Timestamp.now().toDate().getTime();
        Order order = new Order();
       // order.setId(orderId);
       // order.setDate(Timestamp.now().toDate().toString());
        order.setAddress(address);
        if (buyNowCart != null) {
            List<Cart> buyNowList = new ArrayList<>();
            buyNowList.add(buyNowCart);
            order.setProducts(buyNowList);
        } else {
            order.setProducts(Constants.cartList);
        }
        order.setStatus(Constants.OrderStatus.CONFIRMED);
       // order.setBookingDate(date);
       // order.setBookingTimeSlot(time);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("MJ_Users").document(Constants.Uid).collection("Order").document(orderId)
                .set(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        waitDialog.dismiss();
                        SnackBarUtil.showSuccess(PlaceOrderActivity1.this, parentLayout, "Order Placed ");
                        showOrderSuccessDialog(orderId);


                        if (buyNowCart == null) {
                            for (Cart cart : Constants.cartList) {
                                removeProduct(cart.getId());
                            }
                            Constants.cartList.clear();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        waitDialog.dismiss();
                        SnackBarUtil.showError(PlaceOrderActivity1.this, parentLayout, "Failed ,Please Try Again!");
                    }
                });


    }

    private void removeProduct(String cartId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("MJ_Users").document(Constants.Uid).collection("Cart").document(cartId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d(TAG, "DocumentSnapshot successfully deleted!");


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.w(TAG, "Error deleting document", e);

                    }
                });


    }

    private void showOrderSuccessDialog(String orderId) {
        String message = "Your Order Number " + orderId +
                System.getProperty("line.separator")
                + "Track your order please go to Home Menu";


        new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.green_700)
                .setButtonsColorRes(R.color.black)
                .setIcon(R.drawable.ic_done)
                .setTitle("Order Placed Successfully!")
                .setMessage(message)
                .setCancelable(false)

                .setPositiveButton("Go To Home", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PlaceOrderActivity1.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("RATE US", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("market://details?id=" + PlaceOrderActivity1.this.getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        Intent intent = new Intent(PlaceOrderActivity1.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        try {
                            startActivity(goToMarket);
                            finish();
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + PlaceOrderActivity1.this.getPackageName())));
                            finish();
                        }
                    }
                })
                .show();

    }

    private void showDataPicker() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this, this, year, month, day).show();
    }

    private void showTimePicker() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        new TimePickerDialog(this, this, hour, minute, false).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d(TAG, "year: " + year);
        Log.d(TAG, "month: " + month);
        Log.d(TAG, "dayOfMonth: " + dayOfMonth);

        etBookingDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String am_pm = "";

        Calendar datetime = Calendar.getInstance();
        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        datetime.set(Calendar.MINUTE, minute);

        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";

        String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ?"12":datetime.get(Calendar.HOUR)+"";

        etBookingTime.setText( strHrsToShow+":"+datetime.get(Calendar.MINUTE)+" "+am_pm );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
*/
