package online.masterji.honchiSolution.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.User;

public class BillingActivity extends AppCompatActivity {
    private static final String TAG = "BillingActivity";
    Intent intent;
    String name, getId, Title, Address, BookingDate, BookingTimeSlot, getUserId, DeliveredDate, OrderDate, ProductId, Price, Id, Photo, SubCategory, SuperCategory;
    LinearLayout linreBack;
    TextView tvDescr, tvPriceB, tvTotalPrice, tvTotalQty, tvOrdreB, tvAmount, tvQty, tvCustAdress, tvDatwe, tvCustomerName;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent = getIntent();
        data = intent.getStringExtra("Billing");
        Log.e(TAG, "onCreate:data  " + data);
        if (!TextUtils.isEmpty(Constants.Uid)) {
            getUser();
            //  ConvertUriToDrawable(Photo);

        }

        tvTotalQty = findViewById(R.id.tvTotalQty);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);

        tvOrdreB = findViewById(R.id.tvOrdreB);
        tvAmount = findViewById(R.id.tvAmount);
        tvPriceB = findViewById(R.id.tvPriceB);
        tvQty = findViewById(R.id.tvQty);
        tvDescr = findViewById(R.id.tvDescr);
        tvCustAdress = findViewById(R.id.tvCustAdress);
        tvCustomerName = findViewById(R.id.tvCustomerName);

        tvDatwe = findViewById(R.id.tvDatwe);

        linreBack = findViewById(R.id.linreBack);
        if (data.equals("Booking")) {
            getIntentDataBooking();

        } else {
            getIntentDataOrder();

        }

        Log.e(TAG, "onCreate: " + Address);

        tvQty.setText("1");
        tvTotalQty.setText("1");
        // tvDescr.setText(Title);
        // tvCustAdress.setText(Address);
        if (Price != null && !Price.isEmpty()) {
            tvTotalPrice.setText("Rs " + Price);
            tvAmount.setText(Price);
            tvPriceB.setText(Price);

        }
        if (getId != null && !getId.isEmpty()) {
            Log.e(TAG, "onCreate: getId" + getId);
            tvOrdreB.setText(" Order Id: " + getId);
        }

        if (Title != null && !Title.isEmpty()) {
            tvDescr.setText(Title);

        }


        Calendar c = Calendar.getInstance();

        int seconds = c.get(Calendar.SECOND);
        int minutes = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR);
        String time = hour + ":" + minutes + ":" + seconds;


        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = day + "." + month + "." + year;

        // tvDatwe.setText("Date : " + date);
        ;
        // txt_time.setText(time);
        // layoutToImage(linreBack);
    }

    private void getIntentDataOrder() {
        getId = intent.getStringExtra("getId");
        ProductId = intent.getStringExtra("ProductId");
        Title = intent.getStringExtra("Title");
        getSupportActionBar().setTitle(Title);
        Price = intent.getStringExtra("Price");
        Id = intent.getStringExtra("Id");
        Photo = intent.getStringExtra("Photo");
        SubCategory = intent.getStringExtra("SubCategory");
        SuperCategory = intent.getStringExtra("SuperCategory");

        Address = intent.getStringExtra("Address");
        BookingDate = intent.getStringExtra("BookingDate");
        BookingTimeSlot = intent.getStringExtra("BookingTimeSlot");
        OrderDate = intent.getStringExtra("OrderDate");
        DeliveredDate = intent.getStringExtra("DeliveredDate");
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

        if (OrderId != null && !OrderId.isEmpty()) {
            tvOrdreB.setText(" Order Id: " + OrderId);

        }
        if (Address != null && !Address.isEmpty()) {
            tvCustAdress.setText(Address);

        }

    }

    String Type, Name, Email, Febric, Date, Category, OrderId, FebricImage, Gender, Mobile, Time, Work, DesignImage, Design;

    private void getIntentDataBooking() {

        Price = intent.getStringExtra("Price");
        Design = intent.getStringExtra("Design");
        DesignImage = intent.getStringExtra("DesignImage");
        Work = intent.getStringExtra("Work");
        Type = intent.getStringExtra("Type");
        Time = intent.getStringExtra("Time");
        Name = intent.getStringExtra("Name");
        Mobile = intent.getStringExtra("Mobile");
        Gender = intent.getStringExtra("Gender");
        FebricImage = intent.getStringExtra("FebricImage");
        Febric = intent.getStringExtra("Febric");
        Email = intent.getStringExtra("Email");
        Date = intent.getStringExtra("Date");
        Category = intent.getStringExtra("Category");
        Address = intent.getStringExtra("Address");
        OrderId = intent.getStringExtra("OrderId");
        Id = intent.getStringExtra("Id");
        Log.e(TAG, "getIntentDataBooking:OrderId " + OrderId);
        if (Name != null && !Name.isEmpty()) {
            tvCustomerName.setText(Name);
        }
        if (Address != null && !Address.isEmpty()) {
            tvCustAdress.setText(Address);

        }
        if (Price != null && !Price.isEmpty()) {
            tvTotalPrice.setText("Rs " + Price);
            tvAmount.setText(Price);
            tvPriceB.setText(Price);


        }
        if (OrderId != null && !OrderId.isEmpty()) {
            tvOrdreB.setText(" Order Id: " + OrderId);


        }
        if (Category != null && !Category.isEmpty()) {
            tvDescr.setText(Category);
        }
        if (Date != null && !Date.isEmpty()) {
            tvDatwe.setText("Date : " + Date);

        }
        if (OrderDate != null && !OrderDate.isEmpty()) {
            tvDatwe.setText("Date : " + OrderDate);

        }


        tvQty.setText("1");
        tvTotalQty.setText("1");



        Calendar c = Calendar.getInstance();

        int seconds = c.get(Calendar.SECOND);
        int minutes = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR);
        String time = hour + ":" + minutes + ":" + seconds;


        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = day + "." + month + "." + year;


    }

    private void layoutToImage(View view) {

        // get view group using reference
        linreBack = view.findViewById(R.id.linreBack);
        // convert view group to bitmap
        linreBack.setDrawingCacheEnabled(true);
        linreBack.buildDrawingCache();
        Bitmap bm = linreBack.getDrawingCache();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                    Address = user.getAddress();
                    // Email = user.getEmail();
                    tvCustomerName.setText(name);
                    /* tvCustAdress.setText(Address);
                     */
                 /*   try {
                        if (user.getPhoto() != null) {
                            if (!TextUtils.isEmpty(user.getPhoto())) {
                                Glide.with(BillingActivity.this)
                                        .load(user.getPhoto())
                                        .centerInside()
                                        .placeholder(R.drawable.ic_menu_help)
                                        .into(civUser);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/


                }


            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void ConvertUriToDrawable(final String photo3) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bitmap myImage = getBitmapFromURL(photo3);
        Drawable dr = new BitmapDrawable(myImage);
        linreBack.setBackgroundDrawable(dr);


    }

    public Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
