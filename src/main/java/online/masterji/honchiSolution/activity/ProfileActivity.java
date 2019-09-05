package online.masterji.honchiSolution.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.Recycler.Main2Activity;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.User;
import online.masterji.honchiSolution.util.SnackBarUtil;
import online.masterji.honchiSolution.util.Utility;
import online.masterji.honchiSolution.util.WaitDialog;
import online.masterji.honchiSolution.util.easyphotoupload.core.ImageCompressTask;
import online.masterji.honchiSolution.util.easyphotoupload.listeners.IImageCompressTaskListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private static final String TAG = "ProfileActivity";
    //FirebaseAuth.getInstance().signOut();
    private static final int EMAIL_UPDATE_REQUEST = 1;
    private static final int ADDRESS_UPDATE_REQUEST = 2;
    static final int REQUEST_CURRENT_LOCATION = 3;
    static final int REQUEST_IMAGE_CAPTURE_OWNER = 2;
    LinearLayout parentLayout, linearFamilty, linearMeasure;
    TextView tvName, tvPhone, tvEmail, tvDOB, tvAddress;
    RadioButton rbMale, rbFemale;
    ImageView ivPofileImage;
    Button btnCurrentLocation1;
    WaitDialog waitDialog;
    String userChoosenTask;
    public static final int CAMERA_REQUEST = 9999;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    String pathPhotoOwner, Photo;
    //create a single thread pool to our image compression class.
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(1);
    User user;
    String mobile;
    boolean isLoggedIn;

    AccessToken accessToken;
    String field;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = new User();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();
        Log.e(TAG, "onCreate:isLoggedIn " + isLoggedIn);

        initView();
        getUser();
        waitDialog = new WaitDialog(this);
        Log.e(TAG, "onCreate: before signout");
        // FirebaseAuth.getInstance().signOut();


    }


    private void initView() {
        Intent intent = getIntent();
        field = intent.getStringExtra("field_value");
        Log.e(TAG, "initView: field" + field);
        linearFamilty = findViewById(R.id.linearFamilty);
        linearMeasure = findViewById(R.id.linearMeasure);
        parentLayout = findViewById(R.id.parentLayout);
        ivPofileImage = findViewById(R.id.ivPofileImage);
        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvEmail = findViewById(R.id.tvEmail);
        tvDOB = findViewById(R.id.tvDOB);
        tvAddress = findViewById(R.id.tvAddress);
        try {
            if (!field.equals("")) {
                tvAddress.setText("" + field);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        btnCurrentLocation1 = findViewById(R.id.btnCurrentLocation1);

        ImageView ivEditEmail = findViewById(R.id.ivEditEmail);
        ImageView ivEditDOB = findViewById(R.id.ivEditDOB);
        ImageView ivEditAddress = findViewById(R.id.ivEditAddress);

        linearFamilty.setOnClickListener(this);
        linearMeasure.setOnClickListener(this);
        ivPofileImage.setOnClickListener(this);
        ivEditEmail.setOnClickListener(this);
        ivEditDOB.setOnClickListener(this);
        ivEditAddress.setOnClickListener(this);
        btnCurrentLocation1.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_done:
                saveUserInfo();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivPofileImage:
               /* if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA
                        , Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    selectImage();
                } else {
                    EasyPermissions.requestPermissions(this, "We need permissions",
                            123, Manifest.permission.CAMERA
                            , Manifest.permission.READ_EXTERNAL_STORAGE);
                }*/
                break;
            case R.id.ivEditEmail:
                Intent intentEmail = new Intent(this, UpdateFieldActivity.class);
                intentEmail.putExtra("field_name", "Email");
                startActivityForResult(intentEmail, EMAIL_UPDATE_REQUEST);
                break;
            case R.id.linearMeasure:
                Intent intent = new Intent(this, MeasurementActivity.class);

                startActivity(intent);
                break;
            case R.id.linearFamilty:
                Intent intent1 = new Intent(this, Main2Activity.class);

                startActivity(intent1);
                break;
            case R.id.ivEditDOB:
                showDataPicker();
                break;
            case R.id.ivEditAddress:

                // btnCurrentLocation1.setVisibility(View.VISIBLE);
                Intent intentAddress = new Intent(this, UpdateFieldActivity.class);
                intentAddress.putExtra("field_name", "Address");
                startActivityForResult(intentAddress, ADDRESS_UPDATE_REQUEST);
                break;
            case R.id.btnCurrentLocation1:
                if (isLoggedIn) {

                    Log.e(TAG, "logout: isLoggedIn" + isLoggedIn);
                } else {

                    FirebaseAuth.getInstance().signOut();

                    startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                    Log.e(TAG, "logout:isLoggedIn false " + isLoggedIn);
                }
              /*  if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //Toast.makeText(this, "Opening camera", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(this, MapActivity.class), REQUEST_CURRENT_LOCATION);

                } else {
                    EasyPermissions.requestPermissions(this, "We need permissions",
                            123, Manifest.permission.ACCESS_FINE_LOCATION
                            , Manifest.permission.ACCESS_COARSE_LOCATION);
                }*/

                break;
        }

    }

    private void selectImage() {


        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(ProfileActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result) capturePhotoOwner();
                    cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    private void capturePhotoOwner() {
        try {
            /*String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";*/
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File photoFile = File.createTempFile(
                    "OwnerPhoto",  /* prefix */
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

    private void galleryIntent() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private void cameraIntent() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == ADDRESS_UPDATE_REQUEST) {
            tvAddress.setText(data.getStringExtra("field_value"));
        } else if (resultCode == RESULT_OK && requestCode == EMAIL_UPDATE_REQUEST) {
            tvEmail.setText(data.getStringExtra("field_value"));
        } else if (resultCode == RESULT_OK && requestCode == ADDRESS_UPDATE_REQUEST) {
            tvAddress.setText(data.getStringExtra("field_value"));
            Log.e(TAG, "onActivityResult: ===========" + data.getStringExtra("field_value"));
            //tvAddress.setText(field);
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
            tvAddress.setText(address + "," + city + "," + state);
            mobile = tvPhone.getText().toString().trim();
            Log.e(TAG, "onActivityResult:tvAddress.getText().toString().trim() " + tvAddress.getText().toString().trim());
            user.setFullname(tvName.getText().toString().trim());
            user.setMobile(mobile);
            user.setEmail(tvEmail.getText().toString().trim());
            user.setAddress(tvAddress.getText().toString().trim());
            user.setGender(rbMale.isChecked() ? "Male" : "Female");
            user.setDob(tvDOB.getText().toString().trim());
            user.setRoleId(Constants.Role.USER);
            user.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
            user.setToken(FirebaseInstanceId.getInstance().getToken());
            user.setDate(String.valueOf(Timestamp.now().toDate().getTime()));
            user.setPhoto(Photo);
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("MJ_Users").document(Constants.Uid)
                    .set(user)

                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                            waitDialog.dismiss();

                            Snackbar snackbar = SnackBarUtil.showSuccess(ProfileActivity.this, parentLayout, "Saved Successfully");
                            snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                @Override
                                public void onDismissed(Snackbar transientBottomBar, int event) {
                                    super.onDismissed(transientBottomBar, event);
                                    //Constants.NAME=tvName.getText().toString().trim();
                                    finish();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                            waitDialog.dismiss();
                            SnackBarUtil.showError(ProfileActivity.this, parentLayout, "Failed,Please Try Again!");
                        }
                    });


        } else if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            try {
                imageUri = data.getData();
                ivPofileImage.setImageURI(imageUri);
                compressOwnerPhoto(pathPhotoOwner);
                pathPhotoOwner = "";
                //  uploadOwnerPhoto();

                uploadData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (CAMERA_REQUEST == requestCode) {
            try {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ivPofileImage.setImageBitmap(bitmap);

                Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                Log.e(TAG, "onActivityResult: tempUri" + tempUri);
                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));

                System.out.println(finalFile.getAbsolutePath());

                // galleryAddPic(this, finalFile.getAbsolutePath());
                compressOwnerPhoto(pathPhotoOwner);
                pathPhotoOwner = "";
                //uploadOwnerPhoto();


                uploadData();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE_OWNER && resultCode == RESULT_OK) {
            try {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ivPofileImage.setImageBitmap(imageBitmap);
                compressOwnerPhoto(pathPhotoOwner);
                pathPhotoOwner = "";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void uploadData() {
        user.setFullname(tvName.getText().toString().trim());
        user.setMobile(mobile);
        user.setEmail(tvEmail.getText().toString().trim());
        user.setAddress(tvAddress.getText().toString().trim());
        user.setGender(rbMale.isChecked() ? "Male" : "Female");
        user.setDob(tvDOB.getText().toString().trim());
        user.setRoleId(Constants.Role.USER);
        user.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        user.setToken(FirebaseInstanceId.getInstance().getToken());
        user.setDate(String.valueOf(Timestamp.now().toDate().getTime()));
        user.setPhoto(String.valueOf(imageUri));
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("MJ_Users").document(Constants.Uid)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        waitDialog.dismiss();
                        Snackbar snackbar = SnackBarUtil.showSuccess(ProfileActivity.this, parentLayout, "Data Saved Successfully!");
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
                        SnackBarUtil.showWarning(ProfileActivity.this, parentLayout, "Data Not Saved ,Please Try Again");

                    }
                });
    }

    private void compressOwnerPhoto(String path) {
        //image compress task callback
        IImageCompressTaskListener iImageCompressTaskListener = new IImageCompressTaskListener() {
            @Override
            public void onComplete(List<File> compressed) {
                File file = compressed.get(0);
                Log.d("ImageCompressor", "New photo size ==> " + file.length()); //log new file size.
                Log.d(TAG, "Compressed Image Path: " + file.getAbsolutePath());
                ivPofileImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
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

    public static void galleryAddPic(Context context, String pictureFilePath) {
        Log.e(TAG, "galleryAddPic: ");
        Intent galleryIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(pictureFilePath);
        Uri picUri = Uri.fromFile(f);
        galleryIntent.setData(picUri);
        context.sendBroadcast(galleryIntent);
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

        tvDOB.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
    }


    private void getUser() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //  Constants.userMobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().replace("+91", "").replace("-", "").trim();
        try {
            Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            DocumentReference docRef = db.collection("MJ_Users").document(Constants.Uid);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    try {
                        tvName.setText(user.getFullname());
                        Constants.NAME=user.getFullname();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        tvEmail.setText(user.getEmail());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        tvAddress.setText(user.getAddress());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        tvDOB.setText(user.getDob());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (user.getGender() != null) {
                            if (user.getGender().equals("Male")) {
                                rbMale.setChecked(true);
                            } else if (user.getGender().equals("Female")) {
                                rbFemale.setChecked(true);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Photo = user.getPhoto();
                        Log.e(TAG, "onSuccess:user.getPhoto() " + user.getPhoto());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (!TextUtils.isEmpty(user.getPhoto())) {
                            //Photo = user.getPhoto();
                            Constants.PHOTO=user.getPhoto();
                            Glide.with(ProfileActivity.this)
                                    .load(user.getPhoto())
                                    .centerInside()
                                    .placeholder(R.drawable.ic_menu_help)
                                    .into(ivPofileImage);
                        }
                        if (!TextUtils.isEmpty(user.getMobile())) {
                            user.setMobile(user.getMobile().replace("+91", "").replace("-", "").trim());
                            tvPhone.setText("+91-" + user.getMobile());

                        }
                        if (!TextUtils.isEmpty(user.getAddress())) {
                            tvAddress.setText(user.getAddress());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void saveUserInfo() {

        if (!rbMale.isChecked() && !rbFemale.isChecked()) {
            SnackBarUtil.showWarning(this, parentLayout, "Select Gender");
            return;
        }

        waitDialog.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        String mobile = tvPhone.getText().toString().trim();

        User user = new User();
        user.setFullname(tvName.getText().toString().trim());
        user.setMobile(mobile);
        user.setEmail(tvEmail.getText().toString().trim());
        user.setAddress(tvAddress.getText().toString().trim());
        user.setGender(rbMale.isChecked() ? "Male" : "Female");
        user.setDob(tvDOB.getText().toString().trim());
        user.setRoleId(Constants.Role.USER);
        Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        try {
            user.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setToken(FirebaseInstanceId.getInstance().getToken());
        user.setDate(String.valueOf(Timestamp.now().toDate().getTime()));
        user.setPhoto(Photo);
        // uploadOwnerPhoto();
        db.collection("MJ_Users").document(Constants.Uid)
                .set(user)

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        waitDialog.dismiss();

                        Snackbar snackbar = SnackBarUtil.showSuccess(ProfileActivity.this, parentLayout, "Saved Successfully");
                        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                finish();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        waitDialog.dismiss();
                        SnackBarUtil.showError(ProfileActivity.this, parentLayout, "Failed,Please Try Again!");
                    }
                });


    }

    private void uploadOwnerPhoto() {
        try {
            File file = new File(pathPhotoOwner);
            Uri uri = Uri.fromFile(file);
            final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("masterji/user/owner/" + file.getName());
            StorageMetadata storageMetadata = new StorageMetadata.Builder().setContentType("image/webp").build();
            UploadTask uploadTask = storageReference.putFile(uri, storageMetadata);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    waitDialog.dismiss();
                    SnackBarUtil.showWarning(ProfileActivity.this, parentLayout, "Data Not Saved ,Please Try Again");


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
                        user.setPhoto(downloadUri + "");
                        uploadData();
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


    public void logout(View view) {
        if (isLoggedIn) {
            Log.e(TAG, "logout: isLoggedIn" + isLoggedIn);
        } else {
            Log.e(TAG, "logout:isLoggedIn false " + isLoggedIn);
        }
    }
}
