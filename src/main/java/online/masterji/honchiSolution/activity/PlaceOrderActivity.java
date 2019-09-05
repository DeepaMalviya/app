package online.masterji.honchiSolution.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.others.AppLocationService;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.Cart;
import online.masterji.honchiSolution.domain.Order;
import online.masterji.honchiSolution.domain.Orderr;
import online.masterji.honchiSolution.domain.User;
import online.masterji.honchiSolution.util.SnackBarUtil;
import online.masterji.honchiSolution.util.WaitDialog;
import online.masterji.honchiSolution.util.easyphotoupload.core.ImageCompressTask;
import online.masterji.honchiSolution.util.easyphotoupload.listeners.IImageCompressTaskListener;
import pub.devrel.easypermissions.EasyPermissions;

import static android.view.View.GONE;

public class PlaceOrderActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    static final int REQUEST_IMAGE_CAPTURE_SHOP = 6;
    static final int REQUEST_IMAGE_CAPTURE_OWNER = 2;
    private static final String TAG = "PlaceOrderActivity";
    Calendar mCalendarOpeningTime;
    Calendar mCalendarClosingTime;
    RelativeLayout parentLayout;
    EditText etAddress, etBookingDate, etBookingTime;
    CheckBox cbTermConditions;
    ImageView ivTermConditionInfo, imageDesign, imageFebric;
    Button btnPlaceOrder;
    WaitDialog waitDialog;
    TextView tvClose;
    Cart buyNowCart;
    String category, productName;
    TextView btnCurrentPlace;
    private static final int ADDRESS_UPDATE_REQUEST = 2;
    static final int REQUEST_CURRENT_LOCATION = 3;
    List<Cart> buyNowList;
    String Address, Date, Dob, Email, Fullname, Gender, Mobile, Photo, Token, Uid;
    int RoleId;
    List<String> orderrList;
    List<String> orderrList1;
    Orderr orderr;
    AppLocationService appLocationService;
    // String city;
    static long randomNumber;
    String pathPhotoShop, pathPhotoOwner;
    User user;
    String id = null;
    String city;

    java.util.Date date;
    Order order;
    java.util.Date date2 = null;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(1);
    static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public Date getDateFromString(String datetoSaved) {

        try {
            Date date = format.parse(datetoSaved);

            return date;
        } catch (ParseException e) {
            return null;
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appLocationService = new AppLocationService(PlaceOrderActivity.this);
        order = new Order();
       /* if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_NETWORK_STATE
                )) {

        } else {
            EasyPermissions.requestPermissions(this, "We need permissions",
                    123, Manifest.permission.ACCESS_NETWORK_STATE
                    );
        }*/
        initView();

        waitDialog = new WaitDialog(this);
        getUser();

        if (getIntent().getExtras() != null) {
            buyNowCart = (Cart) getIntent().getExtras().getSerializable("buy_now");
            productName = getIntent().getExtras().getString("productName");
            category = getIntent().getExtras().getString("category");
        }
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // return TODO;
        }


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
       /* LocationManager locationManager =
                (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
       */
        Location nwLocation = appLocationService
                .getLocation(LocationManager.NETWORK_PROVIDER);

        if (nwLocation != null) {
            double latitude = nwLocation.getLatitude();
            double longitude = nwLocation.getLongitude();


            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                city = addresses.get(0).getLocality();
                Log.e(TAG, "onCreate: city" + city);
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
            } catch (IOException e) {
                e.printStackTrace();
            }


            // Only if available else return NULL
        } else {
            // showSettingsAlert("NETWORK");
        }
        setOpeningAndClosingTimes();
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    enableLocationSettings();
                    // showSettingsAlert("NETWORK");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
        // EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!gpsEnabled) {
            enableLocationSettings();
        }

    }

    private void enableLocationSettings() {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settingsIntent);
    }

    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                PlaceOrderActivity.this);

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog
                .setMessage(provider + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getApplicationContext().startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    private void initView() {
        orderrList = new ArrayList<>();
        orderrList1 = new ArrayList<>();
        orderr = new Orderr();
        buyNowList = new ArrayList<>();

        imageDesign = findViewById(R.id.imageDesign);
        imageFebric = findViewById(R.id.imageFebric);

        parentLayout = findViewById(R.id.parentLayouti);
        etAddress = findViewById(R.id.etAddress);
        etBookingDate = findViewById(R.id.etBookingDate);
        etBookingTime = findViewById(R.id.etBookingTime);
        cbTermConditions = findViewById(R.id.cbTermConditions);
        ivTermConditionInfo = findViewById(R.id.ivTermConditionInfo);
        btnCurrentPlace = findViewById(R.id.btnCurrentPlace);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(this);
        etBookingDate.setOnClickListener(this);
        etBookingTime.setOnClickListener(this);
        ivTermConditionInfo.setOnClickListener(this);
        btnCurrentPlace.setOnClickListener(this);

        imageDesign.setOnClickListener(this);
        imageFebric.setOnClickListener(this);


        btnPlaceOrder.setVisibility(GONE);

        cbTermConditions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ShowPopup();
                    btnPlaceOrder.setVisibility(View.VISIBLE);
                } else {
                    btnPlaceOrder.setVisibility(GONE);
                }
            }
        });
    }

    private void ShowPopup() {
        final Dialog dialog = new Dialog(PlaceOrderActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialoge_term_condiion);

       /* TextView text = (TextView) dialog.findViewById(R.id.text_dialog_feedback);
        text.setText(msg);*/

        ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
       /* Button cancleButton = (Button) dialog.findViewById(R.id.btn_dialog_cancle_feedback);
        final EditText edittext_tv = (EditText) dialog.findViewById(R.id.dialoge_alert_text_feedback);
*/
        ivClose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Perfome Action
                dialog.dismiss();
            }
        });
        Button confirm1 = (Button) dialog.findViewById(R.id.confirm1);

        confirm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivTermConditionInfo:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.imageFebric:
                if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA
                        , Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    selectFebricImage();
                } else {
                    EasyPermissions.requestPermissions(this, "We need permissions",
                            1, Manifest.permission.CAMERA
                            , Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                break;
          /*  case R.id.imageDesign:
                if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA
                        , Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    selectDesignImage();
                } else {
                    EasyPermissions.requestPermissions(this, "We need permissions",
                            123, Manifest.permission.CAMERA
                            , Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                break;*/
            case R.id.btnPlaceOrder:
                placeOrder();
                break;
            case R.id.etBookingDate:
                showDataPicker();
                //showDateDialog();
                break;
            case R.id.etBookingTime:
                showTimePicker();
                //showTimeDialog();
                break;
            case R.id.btnCurrentPlace:
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

    private void selectDesignImage() {
        try {
            /*String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";*/
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File photoFile = File.createTempFile(
                    "DesignPhoto",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            // Save a file: path for use with ACTION_VIEW intents
            pathPhotoOwner = photoFile.getAbsolutePath();

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, "online.masterji.honchiSolution.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_OWNER);
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "error", e);
        }
    }

    private void selectFebricImage() {
        try {
            /*String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";*/
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File photoFile = File.createTempFile(
                    "FebricPhoto",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            // Save a file: path for use with ACTION_VIEW intents
            pathPhotoShop = photoFile.getAbsolutePath();

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, "online.masterji.honchiSolution.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_SHOP);
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "error", e);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == REQUEST_IMAGE_CAPTURE_OWNER && resultCode == RESULT_OK) {
         *//*  Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageDesign.setImageBitmap(imageBitmap);*//*
            compressOwnerPhoto(pathPhotoOwner);
            pathPhotoOwner = "";

        } else*/
        if (resultCode == RESULT_OK && requestCode == ADDRESS_UPDATE_REQUEST) {
            try {
                etAddress.setText(data.getStringExtra("field_value"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CURRENT_LOCATION && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            String message = extras.getString(Constants.RESULT_DATA_KEY);
            Log.e(TAG, "onActivityResult: " + message);
            double lattitude = extras.getDouble("lattitude");
            double longitude = extras.getDouble("longitude");
             updateLocation(lattitude, longitude);
            Geocoder geocoder;
            List<android.location.Address> addresses = null;
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
            etAddress.setText(address + "," + city + "," + state);


        } else if (requestCode == REQUEST_IMAGE_CAPTURE_SHOP && resultCode == RESULT_OK) {
           /* Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageFebric.setImageBitmap(imageBitmap);*/
            compressShopPhoto(pathPhotoShop);
            pathPhotoShop = "";

        } /*else if (requestCode == REQUEST_IMAGE_CAPTURE_OWNER && resultCode == RESULT_OK) {
         *//*  Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageDesign.setImageBitmap(imageBitmap);*//*
            compressOwnerPhoto(pathPhotoOwner);
            pathPhotoOwner = "";

        }*//*else if (requestCode == 123 && resultCode == RESULT_OK) {
            Log.e(TAG, "onActivityResult: " );

        }*/
    }

    private void updateLocation(double lattitude, double longitude) {
        Log.e(TAG, "updateLocation:lattitude,longitude ==" + lattitude + "," + longitude);
        LatLng latLng1 = new LatLng(lattitude, longitude);
        // LatLng latLng2 = new LatLng(lattitude, longitude);
        // LatLng latLng2 = new LatLng( 23.2709743,77.4668497);
        LatLng latLng2 = new LatLng(22.7196, 75.8577);
        if (latLng1.equals(latLng2)) {

            Log.e(TAG, "onCreate: equal");
        } else {

            Log.e(TAG, "onCreate: elsre");
        }
    }

    private void compressOwnerPhoto(String path) {
        //image compress task callback
        IImageCompressTaskListener iImageCompressTaskListener = new IImageCompressTaskListener() {
            @Override
            public void onComplete(List<File> compressed) {
                File file = compressed.get(0);
                Log.d("ImageCompressor", "New photo size ==> " + file.length()); //log new file size.
                Log.d(TAG, "Compressed Image Path: " + file.getAbsolutePath());
                imageDesign.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                // imageFebric.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));

                pathPhotoOwner = file.getAbsolutePath();
            }

            @Override
            public void onError(Throwable error) {
                //very unlikely, but it might happen on a device with extremely low storage.
                //log it, log.WhatTheFuck?, or show a dialog asking the user to delete some files....etc, etc
                Log.wtf("ImageCompressor", "Error occurred", error);
            }
        };
        //Create ImageCompressTask and execute with Executor.
        ImageCompressTask imageCompressTask = new ImageCompressTask(this, path, iImageCompressTaskListener);

        mExecutorService.execute(imageCompressTask);

    }

    private void compressShopPhoto(String path) {
        //image compress task callback
        IImageCompressTaskListener iImageCompressTaskListener = new IImageCompressTaskListener() {
            @Override
            public void onComplete(List<File> compressed) {
                File file = compressed.get(0);
                Log.d("ImageCompressor", "New photo size ==> " + file.length()); //log new file size.
                Log.d(TAG, "Compressed Image Path: " + file.getAbsolutePath());
                imageFebric.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                //imageDesign.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                pathPhotoShop = file.getAbsolutePath();
            }

            @Override
            public void onError(Throwable error) {
                //very unlikely, but it might happen on a device with extremely low storage.
                //log it, log.WhatTheFuck?, or show a dialog asking the user to delete some files....etc, etc
                Log.wtf("ImageCompressor", "Error occurred", error);
            }
        };
        //Create ImageCompressTask and execute with Executor.
        ImageCompressTask imageCompressTask = new ImageCompressTask(this, path, iImageCompressTaskListener);

        mExecutorService.execute(imageCompressTask);
    }

    private void getUser() {
        waitDialog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference docRef = db.collection("MJ_Users").document(Constants.Uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                waitDialog.dismiss();
                User user = documentSnapshot.toObject(User.class);
                try {
                    if (!TextUtils.isEmpty(user.getAddress()))
                        etAddress.setText(user.getAddress());
                    Address = user.getAddress();
                    Date = user.getDate();
                    Dob = user.getDob();
                    Email = user.getEmail();
                    Fullname = user.getFullname();
                    Gender = user.getGender();
                    Mobile = user.getMobile();
                    Photo = user.getPhoto();
                    Token = user.getToken();
                    Uid = user.getUid();
                    RoleId = user.getRoleId();
                    // Constants.orderrList1 = user.getOrder();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        waitDialog.dismiss();
                    }
                });
    }

    Timestamp timestamp;

    private void placeOrder() {
        String address = etAddress.getText().toString().trim();
        try {
            timestamp = new Timestamp(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String time = etBookingTime.getText().toString().trim();

        if (address.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayout, "Enter Address");
            return;
        } else if (timestamp.toDate() == null) {

            SnackBarUtil.showWarning(this, parentLayout, "Enter Booking Date");
            return;
        } else if (time.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayout, "Enter Booking Time ");
            return;
        }else if (TextUtils.isEmpty(pathPhotoShop)) {
            SnackBarUtil.showWarning(this, parentLayout, "Capture Fabric Image ");
            return;
        }

        waitDialog.show();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final String idid = db.collection("Order").document().getId();
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        try {
            String code = city.substring(0, 3).toUpperCase();
            id = "MJ" + code + "" + number;
            Log.e(TAG, "placeOrder:============ id====" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String orderId = Constants.Uid;
        order.setOrderId(id);
        order.setTimestamp(Timestamp.now());
        order.setUserId(orderId);
        order.setOrderDate(timestamp.toDate());
        order.setAddress(address);
        order.setStatus(Constants.OrderStatus.CONFIRMED);

        Log.e(TAG, "placeOrder: " + new Timestamp(date2));
        Log.e(TAG, "placeOrder:===== " + Timestamp.now());
        String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        Log.e(TAG, "placeOrder:==timeStamp=== " + timeStamp);

        order.setBookingDate((new Timestamp(date2)));
        order.setBookingTimeSlot(Timestamp.now());
        order.setDelivered(false);
        order.setOrderPlaced(true);
        order.setTransit(false);
        order.setDispatched(false);
        order.setDispatchedDate(Timestamp.now());
        order.setTransitDate(Timestamp.now());
        order.setDeliveredDate(null);
        if (buyNowCart != null) {
            buyNowList.add(buyNowCart);
            order.setProducts(buyNowList);
        } else {
            order.setProducts(Constants.cartList);
        }


        Constants.orderrList.add(id);
        // setUserData(id);
        if (!TextUtils.isEmpty(pathPhotoShop)) {
            uploadIamge(id);
        } else {
            uploadData(id);
        }

        // uploadData(id);

/*
        db.collection("Order").document(id)
                .set(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        waitDialog.dismiss();
                        SnackBarUtil.showSuccess(PlaceOrderActivity.this, parentLayout, "Order Placed ");
                        //  Constants.cartList.add(orderr);
                        Constants.orderrList.add(id);


                       // Log.e(TAG, "onSuccess: placeOrder 320 Constants.orderrList===" + Constants.orderrList);
                        showOrderSuccessDialog(id);

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
                        SnackBarUtil.showError(PlaceOrderActivity.this, parentLayout, "Failed ,Please Try Again!");
                    }
                });*/

    }


    private void setUserData(final String id) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        user = new User();
        user.setAddress(Address);
        user.setDate(Date);
        user.setDob(Dob);
        user.setEmail(Email);
        user.setFullname(Fullname);
        user.setGender(Gender);
        user.setMobile(Mobile);
        user.setPhoto(Photo);
        user.setToken(Token);
        user.setUid(Uid);
        user.setRoleId(RoleId);
        // final String orderId = Constants.Uid;

        if (Constants.orderrList1 != null) {
            Constants.orderrList1.add(id);
            // user.setOrder(Constants.orderrList1);
        } else {
            //Constants.orderrList
            // user.setOrder(Constants.orderrList);
        }
        Log.e(TAG, "=======setUserData:====385 " + Constants.orderrList1);
        //  user.setOrder(Constants.orderrList1);
      /*  DocumentReference washingtonRef = db.collection("MJ_Users").document(Constants.Uid);

        // Atomically add a new region to the "regions" array field.
        washingtonRef.update("order", FieldValue.arrayUnion(id));
*/
        db.collection("MJ_Users").document(Constants.Uid)
                .set(user)
/*
  db.collection("MJ_Users").orderBy("mobile","asc").document(mobile)
                .set(user)
*/

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");

                        // db.collection("MJ_Users").document(Constants.Uid).update({"order":FieldValue.arrayUnion("id")});
                        //Log.e(TAG, "onSuccess: 397  Constants.orderrList1" + Constants.orderrList1);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);

                    }
                });


    }

    private void uploadIamge(final String id) {
        waitDialog.show();

        try {

            Uri uri = Uri.fromFile(new File(pathPhotoShop));
            final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Masterji/Tailors/fabric/" + uri.getLastPathSegment());
            StorageMetadata storageMetadata = new StorageMetadata.Builder().setContentType("image/webp").build();
            UploadTask uploadTask = storageReference.putFile(uri, storageMetadata);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    waitDialog.dismiss();
                    SnackBarUtil.showWarning(PlaceOrderActivity.this, parentLayout, "Data Not Saved ,Please Try Again");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Log.d(TAG, "Shop Photo uploaded Successfully");
                    Log.e(TAG, "Shop Photo UPLOADED 1");

                }
            });


            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Log.d(TAG, "uploaded Image Url: " + downloadUri);
                        //user.setFebric(downloadUri + "");
                        order.setFebric(downloadUri + "");
                        uploadData(id);
                        // uploadDesignPhoto(id);
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "error", e);
        }
    }

    /*  private void uploadDesignPhoto(final String id) {
          try {
              File file = new File(pathPhotoOwner);
              Uri uri = Uri.fromFile(file);
              final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Masterji/Tailors/owner/" + file.getName());
              StorageMetadata storageMetadata = new StorageMetadata.Builder().setContentType("image/webp").build();
              UploadTask uploadTask = storageReference.putFile(uri, storageMetadata);

              // Register observers to listen for when the download is done or if it fails
              uploadTask.addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception exception) {
                      // Handle unsuccessful uploads
                      waitDialog.dismiss();
                      SnackBarUtil.showWarning(PlaceOrderActivity.this, parentLayout, "Data Not Saved ,Please Try Again");


                  }
              }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                      // ...
                      Log.d(TAG, "Owner Photo uploaded Successfully");
                      Log.e(TAG, "Owner Photo UPLOADED 2");

                  }
              });


              Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                  @Override
                  public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                      if (!task.isSuccessful()) {
                          throw task.getException();
                      }


                      // Continue with the task to get the download URL

                      return storageReference.getDownloadUrl();
                  }
              }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                  @Override
                  public void onComplete(@NonNull Task<Uri> task) {
                      if (task.isSuccessful()) {
                          Uri downloadUri = task.getResult();
                          Log.d(TAG, "uploaded Image Url: " + downloadUri);
                         // user.setDesign(downloadUri + "");
                          order.setDesign(downloadUri + "");
                          uploadData(id);
                      } else {
                          // Handle failures
                          // ...
                      }
                  }
              });
          } catch (Exception e) {
              Log.e(TAG, "error", e);
          }
      }
  */
    private void uploadData(final String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID

        db.collection("Order").document(id)
                .set(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        waitDialog.dismiss();
                        SnackBarUtil.showSuccess(PlaceOrderActivity.this, parentLayout, "Order Placed ");
                        Constants.orderrList.add(id);


                        Log.e(TAG, "onSuccess: placeOrder 320 Constants.orderrList===" + Constants.orderrList);
                        showOrderSuccessDialog(id);

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
                        SnackBarUtil.showError(PlaceOrderActivity.this, parentLayout, "Failed ,Please Try Again!");
                    }
                });










       /* db.collection("Order").document(id)
                .set(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        waitDialog.dismiss();
                        Snackbar snackbar = SnackBarUtil.showSuccess(PlaceOrderActivity.this, parentLayout, "Data Saved Successfully!");
                        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                //TODO
                                Log.e(TAG, "DATA UPLOADED 3");
                                finish();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        waitDialog.dismiss();
                        SnackBarUtil.showWarning(PlaceOrderActivity.this, parentLayout, "Data Not Saved ,Please Try Again");

                    }
                });*/
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
                        Intent intent = new Intent(PlaceOrderActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("RATE US", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("market://details?id=" + PlaceOrderActivity.this.getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        Intent intent = new Intent(PlaceOrderActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        try {
                            startActivity(goToMarket);
                            finish();
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + PlaceOrderActivity.this.getPackageName())));
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
        new DatePickerDialog(this, TimePickerDialog.THEME_HOLO_DARK, this, year, month, day).show();
    }

    private void showTimePicker() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        new TimePickerDialog(this, TimePickerDialog.THEME_HOLO_DARK, this, hour, minute, true).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d(TAG, "year: " + year);
        Log.d(TAG, "month: " + month);
        Log.d(TAG, "dayOfMonth: " + dayOfMonth);
        Date d = new Date();
        d.getDate();
        try {
            date = new SimpleDateFormat("dd-MM-yyyy").parse((dayOfMonth) + "-" + (month + 1) + "-" + year);
            Log.e(TAG, "onDateSet:===== date====" + date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            date2 = new SimpleDateFormat("dd-MM-yyyy").parse((dayOfMonth + 1) + "-" + (month + 1) + "-" + year);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            date2 = new SimpleDateFormat("dd-MM-yyyy").parse((dayOfMonth + 1) + "-" + (month + 1) + "-" + year);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (d.before(date2)) {
            // datse = dayOfMonth + "-" + (month + 1) + "-" + year;
            new Timestamp(date);

            etBookingDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
        } else if (!d.equals(date2)) {
            etBookingDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
        } else {
            SnackBarUtil.showError(PlaceOrderActivity.this, parentLayout, "Select Correct Date");

        }

    }

    private String toDate(long timestamp) {
        Date date = new Date(timestamp * 1000);
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    String strHrsToShow;

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hourOfDay = hourOfDay + 1;
        String am_pm = "";
        Calendar datetime = Calendar.getInstance();
        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        datetime.set(Calendar.MINUTE, minute);
        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";
        String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";
        etBookingTime.setText(strHrsToShow + ":" + datetime.get(Calendar.MINUTE) + " " + am_pm);

        /*Log.e(TAG, "onTimeSet: strHrsToShow" + strHrsToShow);
        Log.e(TAG, "onTimeSet: datetime.get(Calendar.MINUTE)" + datetime.get(Calendar.MINUTE));


        if (Integer.parseInt(strHrsToShow) >= 10) {
            etBookingTime.setText(strHrsToShow + ":" + datetime.get(Calendar.MINUTE) + " " + am_pm);

        } else if (Integer.parseInt(strHrsToShow) <= 6) {
            etBookingTime.setText(strHrsToShow + ":" + datetime.get(Calendar.MINUTE) + " " + am_pm);

            *//*if (datetime.get(Calendar.MINUTE) <= 1) {
                etBookingTime.setText(strHrsToShow + ":" + datetime.get(Calendar.MINUTE) + " " + am_pm);

            } else {
                SnackBarUtil.showError(PlaceOrderActivity.this, parentLayout, "Select Correct Time");

            }*//*

        } else {
            SnackBarUtil.showError(PlaceOrderActivity.this, parentLayout, "Select Correct Time");

        }*/

    }

    private void setOpeningAndClosingTimes() {
        mCalendarOpeningTime = Calendar.getInstance();
        mCalendarOpeningTime.set(Calendar.HOUR, 10);
        mCalendarOpeningTime.set(Calendar.MINUTE, 00);
        mCalendarOpeningTime.set(Calendar.AM_PM, Calendar.AM);

        mCalendarClosingTime = Calendar.getInstance();
        mCalendarClosingTime.set(Calendar.HOUR, 6);
        mCalendarClosingTime.set(Calendar.MINUTE, 00);
        mCalendarClosingTime.set(Calendar.AM_PM, Calendar.PM);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //clean up!
        mExecutorService.shutdown();
        mExecutorService = null;

    }

}
