package online.masterji.honchiSolution.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.others.AppLocationService;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.Measurment;
import online.masterji.honchiSolution.util.SnackBarUtil;
import online.masterji.honchiSolution.util.WaitDialog;

public class Measurement2Activity extends AppCompatActivity {
    private static final String TAG = "MeasurmentActivity";

    String id;
    FirebaseFirestore db;
    Button save;
    Measurment measurment;
    WaitDialog waitDialog;
    String city;
    LinearLayout parentLayoutt;
    EditText etNeck, EtNAme, etChest, etWaistTop, etWaistBottom, etShoulder, etThigh,
            etKnee, etArm_length, etWrist, etHips, etInseam, etSleeve_length,
            etLengthBottom, etBiceps, etMohri, etLengthTop, etRound, etExtra;
    AppLocationService appLocationService;
    String Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appLocationService = new AppLocationService(
                Measurement2Activity.this);

        db = FirebaseFirestore.getInstance();
        waitDialog = new WaitDialog(this);
        measurment = new Measurment();
        EtNAme = findViewById(R.id.EtNAme);
        etNeck = findViewById(R.id.etNeck);
        etChest = findViewById(R.id.etChest);
        etWaistTop = findViewById(R.id.etWaistTop);
        etShoulder = findViewById(R.id.etShoulder);
        etArm_length = findViewById(R.id.etArm_length);
        etWrist = findViewById(R.id.etWrist);
        etLengthTop = findViewById(R.id.etLengthTop);
        etSleeve_length = findViewById(R.id.etSleeve_length);
        etBiceps = findViewById(R.id.etBiceps);
        etLengthTop = findViewById(R.id.etLengthTop);


        etWaistBottom = findViewById(R.id.etKnee);
        etLengthBottom = findViewById(R.id.etLengthBottom);
        etMohri = findViewById(R.id.etMohri);
        etRound = findViewById(R.id.etRound);
        etExtra = findViewById(R.id.etExtra);
        etHips = findViewById(R.id.etHips);
        etInseam = findViewById(R.id.etInseam);
        etKnee = findViewById(R.id.etKnee);
        etThigh = findViewById(R.id.etThigh);
        save = findViewById(R.id.save);
        parentLayoutt = findViewById(R.id.parentLayout);
        getMeasurement();



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
            } catch (IOException e) {
                e.printStackTrace();
            }

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality();
            Log.e(TAG, "onCreate: city" + city);
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            // Only if available else return NULL
        } else {
            showSettingsAlert("NETWORK");
        }

    }

    String WaistTop,name, strings, Wrist, Title1, Title, Chest, Biceps, ArmLength, Extra, Hips, Id, Inseam, Knee, LengthBottom, LengthTop, Mohri, Neck, Round, Shoulder, SleeveLngth, WaistBottom;

    private void getMeasurement() {
        Intent intent = getIntent();
        strings = intent.getStringExtra("String");
        name = intent.getStringExtra("name");
        ArmLength = intent.getStringExtra("ArmLength");
        Biceps = intent.getStringExtra("Biceps");
        Chest = intent.getStringExtra("Chest");
        Extra = intent.getStringExtra("Extra");
        Hips = intent.getStringExtra("Hips");
        Id = intent.getStringExtra("Id");
        Inseam = intent.getStringExtra("Inseam");
        Knee = intent.getStringExtra("Knee");
        LengthBottom = intent.getStringExtra("LengthBottom");
        LengthTop = intent.getStringExtra("LengthTop");
        Mohri = intent.getStringExtra("Mohri");
        Name = intent.getStringExtra("Name");
        Neck = intent.getStringExtra("Neck");
        Round = intent.getStringExtra("Round");
        Shoulder = intent.getStringExtra("Shoulder");
        SleeveLngth = intent.getStringExtra("SleeveLngth");
        WaistBottom = intent.getStringExtra("WaistBottom");
        WaistTop = intent.getStringExtra("WaistTop");
        Wrist = intent.getStringExtra("Wrist");
        Title1 = intent.getStringExtra("Title1");
        Title = intent.getStringExtra("Title");
        Log.e(TAG, "getMeasurement: name"+name );

        EtNAme.setText(name);
        etNeck.setText(Neck);
        etChest.setText(Chest);
        etWaistTop.setText(WaistTop);
        etWaistBottom.setText(WaistBottom);
        etWrist.setText(Wrist);
        etLengthBottom.setText(LengthBottom);
        etBiceps.setText(Biceps);
        etMohri.setText(Mohri);
        etLengthTop.setText(LengthTop);
        etKnee.setText(Knee);
        etRound.setText(Round);
        etExtra.setText(Extra);
        etShoulder.setText(Shoulder);
        etArm_length.setText(ArmLength);
        etHips.setText(Hips);
        etInseam.setText(Inseam);
        etSleeve_length.setText(SleeveLngth);


    }

    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                Measurement2Activity.this);

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog
                .setMessage(provider + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        Measurement2Activity.this.startActivity(intent);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void MeasurmentPlus(View view) {
        Name = EtNAme.getText().toString().trim();
        String Neck = etNeck.getText().toString().trim();
        String Chest = etChest.getText().toString().trim();
        String WaistTop = etWaistTop.getText().toString().trim();
        String WaistBottom = etWaistBottom.getText().toString().trim();
        String Wrist = etWrist.getText().toString().trim();


        String LengthBottom = etLengthBottom.getText().toString().trim();
        String Biceps = etBiceps.getText().toString().trim();
        String Mohri = etMohri.getText().toString().trim();
        String LengthTop = etLengthTop.getText().toString().trim();

        String Knee = etKnee.getText().toString().trim();
        String Round = etRound.getText().toString().trim();
        String Extra = etExtra.getText().toString().trim();


        String Shoulder = etShoulder.getText().toString().trim();
        String Arm_length = etArm_length.getText().toString().trim();
        String Hips = etHips.getText().toString().trim();
        String Inseam = etInseam.getText().toString().trim();
        String Sleeve_length = etSleeve_length.getText().toString().trim();

        if (Name.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Name");
            return;
        } else if (Neck.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Neck");
            return;

        } else if (Chest.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Chest");
            return;
        } else if (WaistTop.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Waist");
            return;
        } else if (WaistBottom.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter WaistBottom");
            return;
        } else if (Wrist.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Wrist");
            return;
        } else if (LengthBottom.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter LengthBottom");
            return;
        } else if (Biceps.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Biceps");
            return;
        } else if (Mohri.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Mohri");
            return;
        } else if (LengthTop.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter LengthTop");
            return;
        } else if (Knee.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Knee");
            return;
        } else if (Round.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Round");
            return;
        } else if (Extra.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Extra");
            return;
        } else if (Shoulder.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Shoulder");
            return;
        } else if (Arm_length.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Arm Length");
            return;
        } else if (Hips.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Hips");
            return;
        } else if (Inseam.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Inseam");
            return;
        } else if (Sleeve_length.isEmpty()) {
            SnackBarUtil.showWarning(this, parentLayoutt, "Enter Sleeve Length");
            return;
        } else {


            waitDialog.show();
            long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
            Log.e(TAG, "placeOrder:============ number====" + number);

            String code = city.substring(0, 3).toUpperCase();
            id = "MJ" + code + "" + number;
            Log.e(TAG, "placeOrder:============ id====" + id);


            measurment.setId(Constants.Uid);
            measurment.setName(EtNAme.getText().toString().trim());
            //measurment.setInseam(etNeck.getText().toString().trim());
            measurment.setChest(etChest.getText().toString().trim());
            measurment.setNeck(etNeck.getText().toString().trim());
            measurment.setWaistBottom(etWaistBottom.getText().toString().trim());
            measurment.setWaistTop(etWaistTop.getText().toString().trim());
            measurment.setWrist(etWrist.getText().toString().trim());
            measurment.setLengthBottom(etLengthBottom.getText().toString().trim());
            measurment.setBiceps(etBiceps.getText().toString().trim());
            measurment.setMohri(etMohri.getText().toString().trim());
            measurment.setLengthTop(etLengthTop.getText().toString().trim());


            measurment.setKnee(etKnee.getText().toString().trim());
            measurment.setMohri(etMohri.getText().toString().trim());
            measurment.setRound(etRound.getText().toString().trim());


            measurment.setExtra(etExtra.getText().toString().trim());
            measurment.setShoulder(etShoulder.getText().toString().trim());
            measurment.setArmLength(etArm_length.getText().toString().trim());
            measurment.setHips(etHips.getText().toString().trim());

            measurment.setInseam(etInseam.getText().toString().trim());
            measurment.setSleeveLngth(etSleeve_length.getText().toString().trim());


//             setBookingTailorData();
            db.collection("Measurment").document(Name)
                    .set(measurment)

                    .addOnSuccessListener(new OnSuccessListener<Void>() {


                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                            waitDialog.dismiss();
                            SnackBarUtil.showSuccess(Measurement2Activity.this, parentLayoutt, "Measurment Successfully ");
                            Intent intent = new Intent(Measurement2Activity.this, ProfileActivity.class);
                            startActivity(intent);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                            waitDialog.dismiss();
                            SnackBarUtil.showError(Measurement2Activity.this, parentLayoutt, "Measurment Failed,Please Try Again!");
                        }
                    });
        }
    }
}

