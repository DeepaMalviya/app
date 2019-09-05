package online.masterji.honchiSolution.activity;

import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.constant.Constants;
import pub.devrel.easypermissions.EasyPermissions;

public class UpdateFieldActivity extends AppCompatActivity {
    private static final String TAG = "UpdateFieldActivity";
    private static final int ADDRESS_UPDATE_REQUEST = 2;
    EditText editText;
    TextView btnCurrentLocation1;
    static final int REQUEST_CURRENT_LOCATION = 3;
    //LinearLayout linearOr, linearAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_field);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText = findViewById(R.id.editText);
        /*linearAdd = findViewById(R.id.linearAdd);
        linearOr = findViewById(R.id.linearOr);
       */ btnCurrentLocation1 = findViewById(R.id.btnCurrentLocation1);
        btnCurrentLocation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EasyPermissions.hasPermissions(UpdateFieldActivity.this, Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //Toast.makeText(this, "Opening camera", Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(UpdateFieldActivity.this, MapActivity.class), REQUEST_CURRENT_LOCATION);

                } else {
                    EasyPermissions.requestPermissions(UpdateFieldActivity.this, "We need permissions",
                            123, Manifest.permission.ACCESS_FINE_LOCATION
                            , Manifest.permission.ACCESS_COARSE_LOCATION);
                }

            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String field = bundle.getString("field_name");
            getSupportActionBar().setTitle("Update " + field);
            if (field.equals("Address")) {
                editText.setHint("Enter " + "Enter Address,Street,Colony,PinCode");
               /* linearOr.setVisibility(View.VISIBLE);
                linearAdd.setVisibility(View.VISIBLE);*/
            } else {
                editText.setHint("Enter " + field);
               /* linearOr.setVisibility(View.GONE);
                linearAdd.setVisibility(View.GONE);*/
            }

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == ADDRESS_UPDATE_REQUEST) {
            editText.setText(data.getStringExtra("field_value"));
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
            editText.setText(address + "," + city + "," + state);
        }
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

                String field = editText.getText().toString().trim();
                if (field.length() > 0) {
                    Intent intent = new Intent(UpdateFieldActivity.this, ProfileActivity.class);
                    intent.putExtra("field_value", field);


                    intent.putExtra("field_value", field);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
