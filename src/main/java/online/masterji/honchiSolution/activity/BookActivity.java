package online.masterji.honchiSolution.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.others.AppLocationService;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.BookingTailor;
import online.masterji.honchiSolution.util.SnackBarUtil;
import online.masterji.honchiSolution.util.WaitDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.view.View.GONE;

public class BookActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private static final String TAG = "BookActivity";
    private TextView btnCurrentBook, tvPlaceOrder, tvTime, TvDate;
    FirebaseFirestore db;
    WaitDialog waitDialog;
    EditText ETemail, etAddresss, ETmobile, ETFullname;
    String quantity, pathPhotoFebric, pathPhotoDesign, gender, work, febric, category, design, price, price1;
    LinearLayout container;
    BookingTailor bookingTailor;
    String city;
    AppLocationService appLocationService;
    String id;
    Date date;
    Date date2;
    String ts;
    String getId;
    String code;
    CheckBox cbTermConditions1;
    static final int REQUEST_CURRENT_LOCATION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appLocationService = new AppLocationService(
                BookActivity.this);
        Intent intent = getIntent();
        bookingTailor = new BookingTailor();
        db = FirebaseFirestore.getInstance();
        waitDialog = new WaitDialog(this);

        pathPhotoDesign = intent.getStringExtra("pathPhotoDesign");
        pathPhotoFebric = intent.getStringExtra("pathPhotoFebric");
        gender = intent.getStringExtra("gender");
        quantity = intent.getStringExtra("quantity");
        work = intent.getStringExtra("work");
        febric = intent.getStringExtra("febric");
        category = intent.getStringExtra("category");
        design = intent.getStringExtra("design");
        price = intent.getStringExtra("price");
        price1 = intent.getStringExtra("price1");
        Log.e(TAG, "onCreate: pathPhotoDesign" + pathPhotoDesign);
        Location nwLocation = null;
        try {
            nwLocation = appLocationService
                    .getLocation(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (nwLocation != null) {
            double latitude = nwLocation.getLatitude();
            double longitude = nwLocation.getLongitude();


            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            }

            // String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality();
            Log.e(TAG, "onCreate: city" + city);
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            // Only if available else return NULL
        } else {
            //showSettingsAlert("NETWORK");
        }


        cbTermConditions1 = findViewById(R.id.cbTermConditions1);

        btnCurrentBook = findViewById(R.id.btnCurrentBook);
        container = findViewById(R.id.container);
        TvDate = findViewById(R.id.TvDate);
        tvTime = findViewById(R.id.tvTime);
        ETFullname = findViewById(R.id.ETFullname);
        etAddresss = findViewById(R.id.etAddresss);
        ETemail = findViewById(R.id.ETemail);
        ETmobile = findViewById(R.id.ETmobile);
        tvPlaceOrder = findViewById(R.id.tvPlaceOrder);
        btnCurrentBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EasyPermissions.hasPermissions(BookActivity.this, Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //Toast.makeText(this, "Opening camera", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(BookActivity.this, MapActivity.class), REQUEST_CURRENT_LOCATION);

                } else {
                    EasyPermissions.requestPermissions(BookActivity.this, "We need permissions",
                            123, Manifest.permission.ACCESS_FINE_LOCATION
                            , Manifest.permission.ACCESS_COARSE_LOCATION);
                }
            }
        });


        cbTermConditions1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ShowPopup1();
                    tvPlaceOrder.setVisibility(View.VISIBLE);
                } else {
                    tvPlaceOrder.setVisibility(GONE);
                }
            }
        });


        TvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDataPicker();
            }
        });
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });
        tvPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Log.e(TAG, "onClick: BookingDate" + TvDate.getText().toString().trim());
                    Log.e(TAG, "onClick: BookingTime" + tvTime.getText().toString().trim());
                    Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Log.e(TAG, "upload: Constants.Uid " + Constants.Uid);

                    String email = ETemail.getText().toString().trim();
                    String address = etAddresss.getText().toString().trim();
                    String fullname = ETFullname.getText().toString().trim();
                    String date = TvDate.getText().toString().trim();
                    String time = tvTime.getText().toString().trim();
                    // String Name = etName.getText().toString().trim();
                    String Mobile = ETmobile.getText().toString().trim();
                    if (address.isEmpty()) {
                        SnackBarUtil.showWarning(BookActivity.this, container, "Enter Address");
                        return;
                    } else if (date.isEmpty()) {
                        SnackBarUtil.showWarning(BookActivity.this, container, "Enter Booking Date");
                        return;
                    } else if (time.isEmpty()) {
                        SnackBarUtil.showWarning(BookActivity.this, container, "Enter Booking Time ");
                        return;
                    } else if (!isValidMail(email)) {
                        SnackBarUtil.showWarning(BookActivity.this, container, "Enter Booking email ");
                        return;
                    } else if (!isValid(Mobile)) {
                        SnackBarUtil.showWarning(BookActivity.this, container, "Enter Booking Mobile Number ");
                        return;
                    } else if (fullname.isEmpty()) {
                        SnackBarUtil.showWarning(BookActivity.this, container, "Enter Booking fullname ");
                        return;
                    }

                    //waitDialog.show();
                    long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
                    Log.e(TAG, "placeOrder:============ number====" + number);

                    try {
                        code = city.substring(0, 3).toUpperCase();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if (!TextUtils.isEmpty(code)) {
                        id = "MJ" + code + "" + number;
                        Log.e(TAG, "placeOrder:============ id====" + id);
                    } else {
                        code = "BHO";
                        id = "MJ" + code + "" + number;

                    }


                    final String orderId = id;
                    Long tsLong = System.currentTimeMillis() / 1000;
                    ts = tsLong.toString();

                    String str_date = TvDate.getText().toString().trim();
                    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                    Date date2 = null;
                    try {
                        date2 = (Date) formatter.parse(str_date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "onClick:date " + date2.getDate());
                    System.out.println("Today is " + date2.getTime());


                    bookingTailor.setOrderId(orderId);
                    Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    bookingTailor.setId(Constants.Uid);
                    bookingTailor.setAddress(etAddresss.getText().toString().trim());
                    bookingTailor.setGender(gender);
                    bookingTailor.setType(work);
                    bookingTailor.setDate(new Timestamp(date2));

                    String DeliveredDate = toDate(Timestamp.now().getSeconds());
                    Log.e(TAG, "getDeliveredDate: ===" + DeliveredDate);
// TODO: 19/7/19
                    String timee = DateUtils.formatDateTime(BookActivity.this, Timestamp.now().getSeconds(), DateUtils.FORMAT_SHOW_TIME);
                    Log.e(TAG, "-==========timee ===" + timee);


                    Calendar calendar = Calendar.getInstance();
                    TimeZone tz = TimeZone.getDefault();
                    calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    java.util.Date currenTimeZone = new java.util.Date((long) Timestamp.now().getSeconds() * 1000);
                    //Toast.makeText(BookActivity.this, sdf.format(currenTimeZone), Toast.LENGTH_SHORT).show();


                    Log.e(TAG, "onClick: " + sdf.format(currenTimeZone));


                    // bookingTailor.setTime(sdf.format(currenTimeZone));
                    bookingTailor.setTime(tvTime.getText().toString().trim());

                    bookingTailor.setName(ETFullname.getText().toString().trim());
                    bookingTailor.setMobile(ETmobile.getText().toString().trim());
                    bookingTailor.setAddress(etAddresss.getText().toString().trim());


            /*    Log.e(TAG, "onClick: "+new Timestamp(getDateFromString(TvDate.getText().toString().trim())) );
                Log.e(TAG, "onClick: "+new Timestamp(getDateFromString(tvTime.getText().toString().trim())) );
*/

                    ///  bookingTailor.setBookingDate(new Timestamp(getDateFromString(TvDate.getText().toString().trim())));
                    //  bookingTailor.setBookingTimeSlot(new Timestamp(getDateFromString(tvTime.getText().toString().trim())));

                    String dtStart = TvDate.getText().toString().trim();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    try {
                        Date date1 = format.parse(dtStart);
                        Log.e(TAG, "onClick: date" + date1);
                        System.out.println(date1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    bookingTailor.setEmail(ETemail.getText().toString().trim());
                    bookingTailor.setName(ETFullname.getText().toString().trim());
                    bookingTailor.setGender(gender);
                    bookingTailor.setWork(work);
                    bookingTailor.setFebric(febric);
                    bookingTailor.setCategory(category);
                    bookingTailor.setDesign(design);
                    bookingTailor.setMobile(ETmobile.getText().toString().trim());
                    Log.e(TAG, "onClick: pathPhotoDesign" + pathPhotoDesign);
                    Log.e(TAG, "onClick: pathPhotoDesign" + pathPhotoFebric);
                    // uploadImage();

                    if (!TextUtils.isEmpty(pathPhotoFebric)) {

                        Log.i(TAG, "onClick:pathPhotoFebric ");
                        if (!TextUtils.isEmpty(pathPhotoDesign)) {
                            Log.e(TAG, "onClick: pathPhotoDesign");
                            uploadImageDesign();
                        }
                        uploadImageFebric();
                    } else {
                        upload();
                    }


               /* if (!TextUtils.isEmpty(pathPhotoDesign)) {
                    Log.e(TAG, "onClick: pathPhotoDesign");
                    uploadImageDesign();
                } else {
                    upload();
                }
                if (!TextUtils.isEmpty(pathPhotoFebric) && !TextUtils.isEmpty(pathPhotoDesign)) {
                    Log.e(TAG, "onClick: !TextUtils.isEmpty(pathPhotoFebric) && !TextUtils.isEmpty(pathPhotoDesign)");
                    uploadImage();
                } else {
                    upload();
                }*/
                    //  uploadImage();


                /*db.collection("BookingTailor").document()
                        .set(bookingTailor)

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                waitDialog.dismiss();
                                SnackBarUtil.showSuccess(BookActivity.this, container, "Booking Tailor Successfully ");
                                showOrderSuccessDialog(orderId);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                                waitDialog.dismiss();
                                SnackBarUtil.showError(BookActivity.this, container, "Booking Failed,Please Try Again!");
                            }
                        });
*/

               /* Intent intent1 = new Intent(BookActivity.this, MainActivity.class);
                startActivity(intent1);*/
                } else {
                    startActivity(new Intent(BookActivity.this, LoginActivity.class));
                    finish();
                }


            }
        });

    }

    private void ShowPopup1() {
        final Dialog dialog = new Dialog(BookActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialoge_term_condiion);

       /* TextView text = (TextView) dialog.findViewById(R.id.text_dialog_feedback);
        text.setText(msg);*/

        Button confirm1 = (Button) dialog.findViewById(R.id.confirm1);

        confirm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


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
        /*cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });*/

        dialog.show();

    }

    private String toDate(long timestamp) {
        Date date = new Date(timestamp * 1000);
        Log.e(TAG, "toDate: " + timestamp * 1000);
        Log.e(TAG, "toDate: " + date);
        Log.e(TAG, "toDate: " + new SimpleDateFormat("yyyy-MM-dd").format(date));
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public Date getDateFromString(String datetoSaved) {

        try {
            Date date = format.parse(datetoSaved);

            return date;
        } catch (ParseException e) {
            return null;
        }

    }

    private void uploadImageFebric() {
        Log.e(TAG, "uploadImageFebric: ");
        try {
            waitDialog.show();

            Uri uri = Uri.fromFile(new File(pathPhotoFebric));
            final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Masterji/Tailors/fabric/" + uri.getLastPathSegment());
            StorageMetadata storageMetadata = new StorageMetadata.Builder().setContentType("image/webp").build();
            UploadTask uploadTask = storageReference.putFile(uri, storageMetadata);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    waitDialog.dismiss();
                    SnackBarUtil.showWarning(BookActivity.this, container, "Data Not Saved ,Please Try Again");
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
                        bookingTailor.setFebricImage(downloadUri + "");
                        upload();
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

    private void uploadImageDesign() {
        Log.e(TAG, "uploadImageDesign: ");
        try {
            waitDialog.show();

            Uri uri = Uri.fromFile(new File(pathPhotoDesign));
            final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Masterji/Tailors/design/" + uri.getLastPathSegment());
            StorageMetadata storageMetadata = new StorageMetadata.Builder().setContentType("image/webp").build();
            UploadTask uploadTask = storageReference.putFile(uri, storageMetadata);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    waitDialog.dismiss();
                    SnackBarUtil.showWarning(BookActivity.this, container, "Data Not Saved ,Please Try Again");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Log.d(TAG, "Design Photo uploaded Successfully");
                    Log.e(TAG, "Design Photo UPLOADED 1");

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
                        Log.e(TAG, "uploaded DesignImage Url: " + downloadUri);
                        bookingTailor.setDesignImage(downloadUri + "");
                        upload();
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

    private void uploadImage() {
        Log.e(TAG, "uploadImage: ");
        try {
            waitDialog.show();

            Uri uri = Uri.fromFile(new File(pathPhotoFebric));
            final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Masterji/Tailors/fabric/" + uri.getLastPathSegment());
            StorageMetadata storageMetadata = new StorageMetadata.Builder().setContentType("image/webp").build();
            UploadTask uploadTask = storageReference.putFile(uri, storageMetadata);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    waitDialog.dismiss();
                    SnackBarUtil.showWarning(BookActivity.this, container, "Data Not Saved ,Please Try Again");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Log.d(TAG, "Febric Photo uploaded Successfully");
                    Log.e(TAG, "Febric Photo UPLOADED 1");

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
                        Log.d(TAG, "uploaded FebricImage Url: " + downloadUri);
                        bookingTailor.setFebricImage(downloadUri + "");
                        uploadImageDesign();
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

    private void upload() {
        Log.e(TAG, "upload: ");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        waitDialog.show();

     /*   Map<String,Object> addAnimal = new HashMap<>();
        addAnimal.put("bookingDate",getDateFromString(TvDate.getText().toString().trim()));
        addAnimal.put("bookingTimeSlot",getDateFromString(tvTime.getText().toString().trim()));

      */
        getId = db.collection("BookingTailor").document().getId();

        db.collection("BookingTailor").document(id)
                .set(bookingTailor)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        waitDialog.dismiss();
                        SnackBarUtil.showSuccess(BookActivity.this, container, "Order Placed ");

                        //  Constants.cartList.add(orderr);
                        // Constants.orderrList.add(id);


                        Log.e(TAG, "onSuccess: placeOrder 320 Constants.orderrList===" + Constants.orderrList);
                        showOrderSuccessDialog(id);


                       /* if (buyNowCart == null) {
                            for (Cart cart : Constants.cartList) {
                                removeProduct(cart.getId());
                            }
                            Constants.cartList.clear();
                        }*/

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        waitDialog.dismiss();
                        SnackBarUtil.showError(BookActivity.this, container, "Failed ,Please Try Again!");
                    }
                });
    }

    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                BookActivity.this);

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog
                .setMessage(provider + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        BookActivity.this.startActivity(intent);
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
                        Intent intent = new Intent(BookActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("RATE US", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("market://details?id=" + BookActivity.this.getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        Intent intent = new Intent(BookActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        try {
                            startActivity(goToMarket);
                            finish();
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + BookActivity.this.getPackageName())));
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
        new TimePickerDialog(this, TimePickerDialog.THEME_HOLO_DARK, this, hour, minute, false).show();
    }


    private void showDataPicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this, TimePickerDialog.THEME_HOLO_DARK, this, year, month, day).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d(TAG, "year: " + year);
        Log.d(TAG, "month: " + month);
        Log.d(TAG, "dayOfMonth: " + dayOfMonth);
        try {
            date2 = new SimpleDateFormat("dd-MM-yyyy").parse((dayOfMonth + 1) + "-" + (month + 1) + "-" + year);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TvDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
    }

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

        tvTime.setText(strHrsToShow + ":" + datetime.get(Calendar.MINUTE) + " " + am_pm);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private boolean isValidMail(String email) {

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(EMAIL_STRING).matcher(email).matches();

    }

    public static boolean isValid(String s) {
        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // 1) Begins with 0 or 91
        // 2) Then contains 7 or 8 or 9.
        // 3) Then contains 9 digits
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
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
        if (requestCode == REQUEST_CURRENT_LOCATION && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            String message = extras.getString(Constants.RESULT_DATA_KEY);
            Log.e(TAG, "onActivityResult: " + message);
            double lattitude = extras.getDouble("lattitude");
            double longitude = extras.getDouble("longitude");

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
            etAddresss.setText(address + "," + city + "," + state);


        }
    }

}