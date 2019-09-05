/*
package online.masterji.honchiSolution.activity;

import android.content.Intent;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.TailorRegistration;
import online.masterji.honchiSolution.util.SnackBarUtil;
import online.masterji.honchiSolution.util.WaitDialog;
import online.masterji.honchiSolution.util.easyphotoupload.core.ImageCompressTask;
import online.masterji.honchiSolution.util.easyphotoupload.listeners.IImageCompressTaskListener;


public class TailorRegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TailorRegistraionActivi";


    static final int REQUEST_IMAGE_CAPTURE_SHOP = 1;
    static final int REQUEST_IMAGE_CAPTURE_OWNER = 2;
    static final int REQUEST_CURRENT_LOCATION = 3;

    private static final SimpleDateFormat SDF_DATE = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private static final SimpleDateFormat SDF_TIME = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());

    LinearLayout parentLayout;
    ImageView ivPhotoShop, ivPhotoOwner;
    Button btnSave, btnCurrentLocation;

    String pathPhotoShop, pathPhotoOwner;

    TailorRegistration tailorRegistration = new TailorRegistration();

    WaitDialog waitDialog;


    //create a single thread pool to our image compression class.
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(1);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_registration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Constants.tailorMobile = "";
        initView();

        waitDialog = new WaitDialog(this);
    }


    private void initView() {
        parentLayout = findViewById(R.id.parentLayout);
        ivPhotoShop = findViewById(R.id.ivPhotoShop);
        ivPhotoOwner = findViewById(R.id.ivPhotoOwner);
        btnCurrentLocation = findViewById(R.id.btnCurrentLocation);
        btnSave = findViewById(R.id.btnSave);

        ivPhotoShop.setOnClickListener(this);
        ivPhotoOwner.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnCurrentLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivPhotoShop:
                String mobile = ((EditText) findViewById(R.id.etMobile1)).getText().toString().trim();
                Constants.tailorMobile = mobile;
                if (mobile.isEmpty()) {
                    SnackBarUtil.showWarning(this, parentLayout, "Enter Mobile Number 1");

                } else {
                    capturePhotoShop();
                }
                break;
            case R.id.ivPhotoOwner:
                String mobile1 = ((EditText) findViewById(R.id.etMobile1)).getText().toString().trim();
                Constants.tailorMobile = mobile1;
                if (mobile1.isEmpty()) {
                    SnackBarUtil.showWarning(this, parentLayout, "Enter Mobile Number 1");

                } else {
                    capturePhotoOwner();
                }
                break;
            case R.id.btnCurrentLocation:
                startActivityForResult(new Intent(this, MapActivity.class), REQUEST_CURRENT_LOCATION);
                break;
            case R.id.btnSave:
                saveData();
                break;
        }

    }


    private void capturePhotoShop() {
        try {
            */
/*String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";*//*

            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File photoFile = File.createTempFile(
                    "ShopPhoto",  */
/* prefix *//*

                    ".jpg",         */
/* suffix *//*

                    storageDir      */
/* directory *//*

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


    private void capturePhotoOwner() {
        try {
            */
/*String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";*//*

            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File photoFile = File.createTempFile(
                    "OwnerPhoto",  */
/* prefix *//*

                    ".jpg",         */
/* suffix *//*

                    storageDir      */
/* directory *//*

            );
            // Save a file: path for use with ACTION_VIEW intents
            pathPhotoOwner = photoFile.getAbsolutePath();

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, "online.masterji.honchi.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_OWNER);
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "error", e);
        }
    }

    private void galleryAddPic(String currentPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE_SHOP && resultCode == RESULT_OK) {
            */
/*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivPhotoShop.setImageBitmap(imageBitmap);*//*

            compressShopPhoto(pathPhotoShop);
            pathPhotoShop = "";

        } else if (requestCode == REQUEST_IMAGE_CAPTURE_OWNER && resultCode == RESULT_OK) {
            */
/*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivPhotoShop.setImageBitmap(imageBitmap);*//*

            compressOwnerPhoto(pathPhotoOwner);
            pathPhotoOwner = "";

        } else if (requestCode == REQUEST_CURRENT_LOCATION && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
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


            btnCurrentLocation.setText(lattitude + "," + longitude);
            tailorRegistration.setLattitude(lattitude);
            tailorRegistration.setLongitude(longitude);

        }

    }


    private void compressShopPhoto(String path) {
        //image compress task callback
        IImageCompressTaskListener iImageCompressTaskListener = new IImageCompressTaskListener() {
            @Override
            public void onComplete(List<File> compressed) {
                File file = compressed.get(0);
                Log.d("ImageCompressor", "New photo size ==> " + file.length()); //log new file size.
                Log.d(TAG, "Compressed Image Path: " + file.getAbsolutePath());
                ivPhotoShop.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
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

    private void compressOwnerPhoto(String path) {
        //image compress task callback
        IImageCompressTaskListener iImageCompressTaskListener = new IImageCompressTaskListener() {
            @Override
            public void onComplete(List<File> compressed) {
                File file = compressed.get(0);
                Log.d("ImageCompressor", "New photo size ==> " + file.length()); //log new file size.
                Log.d(TAG, "Compressed Image Path: " + file.getAbsolutePath());
                ivPhotoOwner.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
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


    private void saveData() {

        String shopName = ((EditText) findViewById(R.id.etShopName)).getText().toString().trim();
        String registrationNo = ((EditText) findViewById(R.id.etRegistraionNo)).getText().toString().trim();
        String ownerName = ((EditText) findViewById(R.id.etOwnerName)).getText().toString().trim();
        String emailId = ((EditText) findViewById(R.id.etEmailId)).getText().toString().trim();
        String mobile1 = ((EditText) findViewById(R.id.etMobile1)).getText().toString().trim();
        String mobile2 = ((EditText) findViewById(R.id.etMobile2)).getText().toString().trim();
        String shopNo = ((EditText) findViewById(R.id.etShopNo)).getText().toString().trim();
        String street = ((EditText) findViewById(R.id.etStreet)).getText().toString().trim();
        String colony = ((EditText) findViewById(R.id.etColony)).getText().toString().trim();
        String area = ((EditText) findViewById(R.id.etArea)).getText().toString().trim();
        String city = ((EditText) findViewById(R.id.etCity)).getText().toString().trim();
        String pinCode = ((EditText) findViewById(R.id.etPinCode)).getText().toString().trim();
        String landmark = ((EditText) findViewById(R.id.etLandmark)).getText().toString().trim();


        RadioButton rbGents = findViewById(R.id.rbGents);
        RadioButton rbLadies = findViewById(R.id.rbLadies);
        RadioButton rbGentsAndLadies = findViewById(R.id.rbGentsAndLadies);


        if (!rbGents.isChecked() && !rbLadies.isChecked() && !rbGentsAndLadies.isChecked()) {
            SnackBarUtil.showWarning(this, parentLayout, "Select Tailor Type");
        } else if (shopName.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayout, "Enter Shop Name");

        } else if (ownerName.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayout, "Enter Owner Name");

        } else if (mobile1.length() < 10) {
            SnackBarUtil.showWarning(this, parentLayout, "Enter Valid Mobile Number 1");

        } else if (colony.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayout, "Enter Colony Name");

        } else if (area.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayout, "Enter Area Name");

        } else if (city.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayout, "Enter City Name");

        } else if (pathPhotoShop.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayout, "Capture Shop Photo");

        } else if (pathPhotoOwner.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayout, "Capture Owner Photo");
        } else {

            tailorRegistration.setShopName(shopName);
            tailorRegistration.setRegistrationNumber(registrationNo);
            tailorRegistration.setOwnerName(ownerName);
            tailorRegistration.setEmailId(emailId);
            tailorRegistration.setMobile1(mobile1);
            tailorRegistration.setMobile2(mobile2);
            tailorRegistration.setShopNo(shopNo);
            tailorRegistration.setStreet(street);
            tailorRegistration.setColony(colony);
            tailorRegistration.setArea(area);
            tailorRegistration.setCity(city);
            tailorRegistration.setPincode(pinCode);
            tailorRegistration.setLandmark(landmark);
            if (rbGents.isChecked()) {
                tailorRegistration.setTailorType("Gents");
            } else if (rbLadies.isChecked()) {
                tailorRegistration.setTailorType("Ladies");
            } else if (rbGentsAndLadies.isChecked()) {
                tailorRegistration.setTailorType("Gents & Ladies");
            }

            tailorRegistration.setCreateDate(SDF_DATE.format(Timestamp.now().toDate()));
            tailorRegistration.setCreateTime(SDF_TIME.format(Timestamp.now().toDate()));

            uploadShopPhoto();
        }

    }


    private void uploadShopPhoto() {
        try {
            waitDialog.show();

            Uri uri = Uri.fromFile(new File(pathPhotoShop));
            final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Masterji/Tailors/shop/" + uri.getLastPathSegment());
            StorageMetadata storageMetadata = new StorageMetadata.Builder().setContentType("image/webp").build();
            UploadTask uploadTask = storageReference.putFile(uri, storageMetadata);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    waitDialog.dismiss();
                    SnackBarUtil.showWarning(TailorRegistrationActivity.this, parentLayout, "Data Not Saved ,Please Try Again");
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
                        tailorRegistration.setShopPhoto(downloadUri + "");
                        uploadOwnerPhoto();
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

    private void uploadOwnerPhoto() {
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
                    SnackBarUtil.showWarning(TailorRegistrationActivity.this, parentLayout, "Data Not Saved ,Please Try Again");


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
                        tailorRegistration.setOwnerPhoto(downloadUri + "");
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

    private void uploadData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Add a new document with a generated ID
        db.collection("MJ_Tailors").document(Constants.tailorMobile)
                .set(tailorRegistration)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        waitDialog.dismiss();
                        Snackbar snackbar = SnackBarUtil.showSuccess(TailorRegistrationActivity.this, parentLayout, "Data Saved Successfully!");
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
                        SnackBarUtil.showWarning(TailorRegistrationActivity.this, parentLayout, "Data Not Saved ,Please Try Again");

                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
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
*/
