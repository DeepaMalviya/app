/*
package online.masterji.honchiSolution.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.BookingTailor;
import online.masterji.honchiSolution.util.SnackBarUtil;
import online.masterji.honchiSolution.util.WaitDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class BookTailorActivity1 extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private static final String TAG = "BookTailorActivity";
    FirebaseFirestore db;
    RelativeLayout parentLayoutt;
    WaitDialog waitDialog;
    BookingTailor bookingTailor;
    private static final int ADDRESS_UPDATE_REQUEST = 2;
    static final int REQUEST_CURRENT_LOCATION = 3;
    EditText etAddresss;
    Button btnCurrent, btnBookk;
    EditText etTime, etDtae, etMobile, etName;
    LinearLayout linearAlter, linearStich;
    RadioButton rbAlt, rbStitch, rbMalee, rbFemalee;
    boolean type = false;
    RadioGroup rgType, rgGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tailor1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bookingTailor = new BookingTailor();
        db = FirebaseFirestore.getInstance();
        waitDialog = new WaitDialog(this);

        parentLayoutt = findViewById(R.id.parentLayoutt);
        linearStich = findViewById(R.id.linearStich);
        linearAlter = findViewById(R.id.linearAlter);
        linearAlter.setVisibility(View.VISIBLE);

        rgType = (RadioGroup) findViewById(R.id.rgType);
        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rbAlt = (RadioButton) group.findViewById(checkedId);
                rbStitch = (RadioButton) group.findViewById(checkedId);
                if (rbAlt.isChecked()) {
                    if (rbAlt.getText().equals("Alter")) {
                        linearAlter.setVisibility(View.VISIBLE);
                        linearStich.setVisibility(View.GONE);
                    } else {
                        linearAlter.setVisibility(View.GONE);
                        linearStich.setVisibility(View.VISIBLE);
                    }
                    // Toast.makeText(BookTailorActivity.this, rbAlt.getText(), Toast.LENGTH_SHORT).show();
                }


            }
        });
        etTime = findViewById(R.id.etTime);
        etDtae = findViewById(R.id.etDtae);
        etAddresss = findViewById(R.id.etAddresss);
        etMobile = findViewById(R.id.etMobile);
        etName = findViewById(R.id.etName);

        btnCurrent = findViewById(R.id.btnCurrent);
        btnBookk = findViewById(R.id.btnBookk);
        btnCurrent.setOnClickListener(this);
        btnBookk.setOnClickListener(this);
        etDtae.setOnClickListener(this);
        etTime.setOnClickListener(this);

        rbAlt = findViewById(R.id.rbAlt);
        rbStitch = findViewById(R.id.rbStitch);
        rbMalee = findViewById(R.id.rbMalee);
        rbFemalee = findViewById(R.id.rbFemalee);


       */
/* boolean typerbAlt = false;
        if (rbAlt.isChecked()) {
            type = true;
            Log.e(TAG, "onCreate: rbAlt.isChecked()" + rbAlt.isChecked());
            linearAlter.setVisibility(View.VISIBLE);
            linearStich.setVisibility(View.GONE);
        } else {
            type = false;
            Log.e(TAG, "onCreate: rbAlt.isChecked()" + rbAlt.isChecked());

            linearAlter.setVisibility(View.GONE);
            linearStich.setVisibility(View.VISIBLE);
        }
        if (rbStitch.isChecked()) {
            type = true;
            Log.e(TAG, "onCreate: rbStitch.isChecked()" + rbStitch.isChecked());
            linearAlter.setVisibility(View.GONE);
            linearStich.setVisibility(View.VISIBLE);
        } else {
            type = false;
            Log.e(TAG, "onCreate: rbStitch.isChecked()" + rbStitch.isChecked());
            linearAlter.setVisibility(View.VISIBLE);

            linearStich.setVisibility(View.GONE);
        }
        if (type) {
            linearAlter.setVisibility(View.VISIBLE);
            linearStich.setVisibility(View.GONE);
            type = false;
        } else {
            linearAlter.setVisibility(View.GONE);
            linearStich.setVisibility(View.VISIBLE);
            type = true;
        }*//*

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ADDRESS_UPDATE_REQUEST) {
            etAddresss.setText(data.getStringExtra("field_value"));
        } else if (requestCode == REQUEST_CURRENT_LOCATION && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            String message = extras.getString(Constants.RESULT_DATA_KEY);
            Log.e(TAG, "onActivityResult: " + message);
            double lattitude = extras.getDouble("lattitude");
            double longitude = extras.getDouble("longitude");

            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(lattitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            }

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            //  btnCurrentLocation1.setText(lattitude + "," + longitude);
            etAddresss.setText(address + "," + city + "," + state);


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etTime:
                showTimePicker();

                break;
            case R.id.etDtae:
                showDataPicker();

                break;
            case R.id.btnBookk:
                BookinData();

                break;
            case R.id.btnCurrent:
                if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //Toast.makeText(this, "Opening camera", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(this, MapActivity.class), REQUEST_CURRENT_LOCATION);

                } else {
                    EasyPermissions.requestPermissions(this, "We need permissions",
                            123, Manifest.permission.ACCESS_FINE_LOCATION
                            , Manifest.permission.ACCESS_COARSE_LOCATION);
                }
                break;
        }
    }

    private void BookinData() {
        String address = etAddresss.getText().toString().trim();
        String date = etDtae.getText().toString().trim();
        String time = etTime.getText().toString().trim();
        String Name = etName.getText().toString().trim();
        String Mobile = etMobile.getText().toString().trim();
        if (!rbAlt.isChecked() && !rbStitch.isChecked()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Select Alter and Stich ");
            return;
        } else if (!rbMalee.isChecked() && !rbFemalee.isChecked()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Select Gender");
            return;
        } else if (address.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Address");
            return;
        } else if (date.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Booking Date");
            return;
        } else if (time.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Booking Time ");
            return;
        } else if (Name.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Booking Name ");
            return;
        } else if (Mobile.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Booking Mobile Number ");
            return;
        }

        waitDialog.show();

        final String orderId = Constants.Uid;

        bookingTailor.setId(orderId);
        bookingTailor.setAddress(etAddresss.getText().toString().trim());
        bookingTailor.setGender(rbMalee.isChecked() ? "Male" : "Female");
        bookingTailor.setType(rbAlt.isChecked() ? "Alt" : "Stich");
       // bookingTailor.setDate(etDtae.getText().toString().trim());
       // bookingTailor.setTime(etTime.getText().toString().trim());

        bookingTailor.setName(etName.getText().toString().trim());
        bookingTailor.setMobile(etMobile.getText().toString().trim());
        // setBookingTailorData();






        db.collection("BookingTailor").document()
                .set(bookingTailor)

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        waitDialog.dismiss();
                        SnackBarUtil.showSuccess(BookTailorActivity1.this, parentLayoutt, "Booking Tailor Successfully ");
                        showOrderSuccessDialog(orderId);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        waitDialog.dismiss();
                        SnackBarUtil.showError(BookTailorActivity1.this, parentLayoutt, "Booking Failed,Please Try Again!");
                    }
                });
    }

    private void showOrderSuccessDialog(String orderId) {
        String message = "Your Booking id " + orderId +
                System.getProperty("line.separator")
                + "Thank You for Booking Tailor ,Masterji representative will call you shortly to approve your request!";


        new LovelyStandardDialog(this, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                .setTopColorRes(R.color.green_700)
                .setButtonsColorRes(R.color.black)
                .setIcon(R.drawable.ic_done)
                .setTitle("Booking Tailor Successfully!")
                .setMessage(message)
                .setCancelable(false)

                .setPositiveButton("Go To Home", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(BookTailorActivity1.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("RATE US", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("market://details?id=" + BookTailorActivity1.this.getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        Intent intent = new Intent(BookTailorActivity1.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        try {
                            startActivity(goToMarket);
                            finish();
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + BookTailorActivity1.this.getPackageName())));
                            finish();
                        }
                    }
                })
                .show();


    }

    private void showTimePicker() {

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        new TimePickerDialog(this, this, hour, minute, false).show();
    }


    private void showDataPicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this, this, year, month, day).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d(TAG, "year: " + year);
        Log.d(TAG, "month: " + month);
        Log.d(TAG, "dayOfMonth: " + dayOfMonth);

        etDtae.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
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

        String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";

        etTime.setText(strHrsToShow + ":" + datetime.get(Calendar.MINUTE) + " " + am_pm);

    }
}
*/
