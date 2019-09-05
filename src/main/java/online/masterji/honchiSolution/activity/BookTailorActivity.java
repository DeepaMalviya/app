package online.masterji.honchiSolution.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.util.SnackBarUtil;
import online.masterji.honchiSolution.util.easyphotoupload.core.ImageCompressTask;
import online.masterji.honchiSolution.util.easyphotoupload.listeners.IImageCompressTaskListener;

public class BookTailorActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BookTailorActivity";
    private TabLayout tabLayout;
    // private ViewPager viewPager;
    Button btnNextM, btnNextMmm;
    ImageView image;
    Spinner SpinnerWork, SpinnerDesignnn, SpinnerDesign, SpinnerWorkk, SpinnerDesignn, SpinnerCategory, SpinnerFabric;
    List<Integer> Quantity = new ArrayList<>();
    List<String> Work = new ArrayList<>();
    List<String> Fabric = new ArrayList<>();
    List<String> CategoryMen = new ArrayList<>();
    List<String> CategoryWomen = new ArrayList<>();
    List<String> Design = new ArrayList<>();
    ArrayAdapter<String> spinnerAdapter;
    String itemWork, itemFabric;
    LinearLayout lDesign, lFabric ,linearFabric,layOPrice,layoutStich;
    String work, febric, category, design, price;
    RadioButton rbMale, rbFemale;
    RelativeLayout parentLayoutt;
    RadioGroup rgDDD;
    TextView tvDesign, tvDesi, tvSelectCat, tvachange;//SpinnerFabric//SpinnerDesign/tvDesign
    int quantity, Cost;
    ImageView imageDD, imageDesignn, imageFebricc;
    static final int REQUEST_IMAGE_CAPTURE_FEBRIC = 6;
    static final int REQUEST_IMAGE_CAPTURE_OWNER = 2;
    //action_price
    String pathPhotoDesign, pathPhotoFebric;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(1);
    ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tailor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewByIds();

        rgDDD.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e(TAG, "onCheckedChanged: " + checkedId);
                View radioButton = rgDDD.findViewById(checkedId);
                int index = rgDDD.indexOfChild(radioButton);

                // Add logic here

                switch (index) {
                    case 0: // first button

                        updateAdapter(CategoryMen);
                        break;
                    case 1: // secondbutton

                        updateAdapter(CategoryWomen);
                        break;
                }

            }
        });
        SpinnerDesignData();
        SpinnerWorkData();
        SpinnerFabricData();
        SpinnerCategoryData();

        /*if (design.equals("Upload Design") && febric.equals("Fabric")) {
            linearFabric.setVisibility(View.VISIBLE);

        } else {
            linearFabric.setVisibility(View.INVISIBLE);

        }*/


    }

    private void findViewByIds() {
        lFabric = findViewById(R.id.lFabric);
        lDesign = findViewById(R.id.lDesign);
        linearFabric = findViewById(R.id.linearFabric);
        tvDesi = findViewById(R.id.tvDesi);
        imageDD = findViewById(R.id.imageDD);
        rbMale = findViewById(R.id.rbMale);
        rgDDD = findViewById(R.id.rgDDD);
        rbFemale = findViewById(R.id.rbFemale);
        SpinnerWork = (Spinner) findViewById(R.id.SpinnerWork);
        SpinnerFabric = (Spinner) findViewById(R.id.SpinnerFabric);
        imageFebricc = findViewById(R.id.imageFebricc);
        imageDesignn = findViewById(R.id.imageDesignn);
        parentLayoutt = findViewById(R.id.parentLayoutt);
        tvSelectCat = findViewById(R.id.tvSelectCat);
        tvachange = findViewById(R.id.tvachange);
        tvDesign = findViewById(R.id.tvDesign);
        layoutStich = findViewById(R.id.layoutStich);
        // layoutAlterr = findViewById(R.id.layoutAlterr);
        layOPrice = findViewById(R.id.layOPrice);
        SpinnerCategory = (Spinner) findViewById(R.id.SpinnerCategory);
        SpinnerDesign = (Spinner) findViewById(R.id.SpinnerDesign);


        btnNextM = findViewById(R.id.btnNextM);
        btnNextM.setOnClickListener(this);
        imageFebricc.setOnClickListener(this);
        imageDesignn.setOnClickListener(this);

        Quantity = new ArrayList<Integer>();
        for (int i = 5; i <= 50; i++) {
            Quantity.add(i);
        }


        Work = new ArrayList<String>();
        Work.add("Stitching");
        Work.add("Alteration");

        Design = new ArrayList<>();
        //  Design.add("Select Design");
        Design.add("No Design");
        Design.add("Upload Design");

        CategoryMen = new ArrayList<>();
        CategoryMen.add("Shirts");
        CategoryMen.add("Blazers");
        CategoryMen.add("Casual Shirts");
        CategoryMen.add("Dhoti And Pyjama");
        CategoryMen.add("Formal Shirts");
        CategoryMen.add("Kurta");
        CategoryMen.add("Nehru Jackets");
        CategoryMen.add("Pants");
        CategoryMen.add("Safari Suit");
        CategoryMen.add("Sherwani");

        CategoryWomen = new ArrayList<>();
        CategoryWomen.add("Blouses");
        CategoryWomen.add("Dungaree");
        CategoryWomen.add("Ethnic Wear");
        CategoryWomen.add("Gowns");
        CategoryWomen.add("Jacket And Waistcoat");
        CategoryWomen.add("Jumpsuit");
        CategoryWomen.add("Kurtis");
        CategoryWomen.add("Lehengas");
        CategoryWomen.add("Night Gown");
        CategoryWomen.add("Pant And Trousers");
        CategoryWomen.add("Plus Size");
        CategoryWomen.add("Sharara Suit");
        CategoryWomen.add("Shirts");
        CategoryWomen.add("Shrugs");
        CategoryWomen.add("Skirts");
        CategoryWomen.add("Suit And Salwar");

        Fabric = new ArrayList<>();
        Fabric.add("Fabric");
        Fabric.add("No Fabric");

    }

    private void updateAdapter(List<String> categoryMen) {

        Log.e(TAG, "SpinnerCategoryData: Male");
        dataAdapter = new ArrayAdapter<String>
                (BookTailorActivity.this, android.R.layout.simple_spinner_item, categoryMen);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCategory.setAdapter(dataAdapter);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCategory.setAdapter(dataAdapter);
        SpinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                category = item;
                // Showing selected spinner item
                //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void SpinnerDesignData() {


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (BookTailorActivity.this, android.R.layout.simple_spinner_item, Design);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        SpinnerDesign.setAdapter(dataAdapter);
        SpinnerDesign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                design = item;
                if (design.equals("Upload Design")) {
                    if (febric.equals("Fabric")) {
                        lDesign.setVisibility(View.VISIBLE);
                        linearFabric.setVisibility(View.VISIBLE);
                        lFabric.setVisibility(View.VISIBLE);

                    } else {
                        lDesign.setVisibility(View.VISIBLE);
                        linearFabric.setVisibility(View.VISIBLE);
                        lFabric.setVisibility(View.VISIBLE);

                    }
                    lDesign.setVisibility(View.VISIBLE);
                    linearFabric.setVisibility(View.VISIBLE);
                    lFabric.setVisibility(View.VISIBLE);

                    // tvDesi.setText("Upload Design");
                } else {
                    if (febric.equals("No Fabric")) {
                        lFabric.setVisibility(View.INVISIBLE);
                        lDesign.setVisibility(View.INVISIBLE);
                        linearFabric.setVisibility(View.INVISIBLE);

                    } else {
                        lFabric.setVisibility(View.VISIBLE);
                        lDesign.setVisibility(View.INVISIBLE);
                        linearFabric.setVisibility(View.VISIBLE);

                    }
                    lFabric.setVisibility(View.VISIBLE);
                    lDesign.setVisibility(View.INVISIBLE);
                    linearFabric.setVisibility(View.VISIBLE);

                }
                /*if (design.equals("Upload Design") && febric.equals("Fabric")) {
                    Log.e(TAG, "SpinnerCategoryData: Upload Design ");

                    lDesign.setVisibility(View.VISIBLE);
                    linearFabric.setVisibility(View.VISIBLE);
                    lFabric.setVisibility(View.VISIBLE);

                    // tvDesi.setText("Upload Design");
                } else {
                    Log.e(TAG, "SpinnerCategoryData: Upload Design else");

                    lFabric.setVisibility(View.GONE);
                    lDesign.setVisibility(View.GONE);
                    linearFabric.setVisibility(View.GONE);

                }
                if (design.equals("Upload Design") || febric.equals("Fabric")) {
                    Log.e(TAG, "SpinnerCategoryData: Upload Design ");

                    lDesign.setVisibility(View.VISIBLE);
                    linearFabric.setVisibility(View.VISIBLE);
                    lFabric.setVisibility(View.VISIBLE);

                    // tvDesi.setText("Upload Design");
                } else {
                    Log.e(TAG, "SpinnerCategoryData: Upload Design else");

                    lFabric.setVisibility(View.GONE);
                    lDesign.setVisibility(View.GONE);
                    linearFabric.setVisibility(View.GONE);

                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    private void SpinnerCategoryData() {
      /*  if (febric.equals("Fabric")) {
            Log.e(TAG, "SpinnerCategoryData: Fabric" );
            lFabric.setVisibility(View.VISIBLE);
            linearFabric.setVisibility(View.VISIBLE);
            lDesign.setVisibility(View.INVISIBLE);

            // tvDesi.setText("Upload Design");
        } else {
            Log.e(TAG, "SpinnerCategoryData: Fabric else" );

            lFabric.setVisibility(View.INVISIBLE);
            lDesign.setVisibility(View.INVISIBLE);
            linearFabric.setVisibility(View.INVISIBLE);

        }

        if (febric.equals("Fabric") && design.equals("Upload Design")) {

            Log.e(TAG, "SpinnerCategoryData: ");
        }*/
    }

    private void SpinnerFabricData() {


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (BookTailorActivity.this, android.R.layout.simple_spinner_item, Fabric);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        SpinnerFabric.setAdapter(dataAdapter);
        SpinnerFabric.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                febric = item;
                /*if (febric.equals("Fabric")) {

                    linearFabric.setVisibility(View.VISIBLE);
                    lFabric.setVisibility(View.VISIBLE);
                    lDesign.setVisibility(View.INVISIBLE);

                } else {
                    linearFabric.setVisibility(View.GONE);
                    lFabric.setVisibility(View.GONE);
                    lDesign.setVisibility(View.GONE);

                }*/
                /*if (febric.equals("Fabric")) {
                    lFabric.setVisibility(View.VISIBLE);
                    linearFabric.setVisibility(View.VISIBLE);
                    lDesign.setVisibility(View.INVISIBLE);

                    // tvDesi.setText("Upload Design");
                } else {
                    lFabric.setVisibility(View.GONE);
                    lDesign.setVisibility(View.GONE);
                    linearFabric.setVisibility(View.GONE);

                }*/

                if (febric.equals("No Fabric")) {
                    Log.e(TAG, "SpinnerCategoryData: Fabric else");

                    lFabric.setVisibility(View.GONE);
                    lDesign.setVisibility(View.GONE);
                    linearFabric.setVisibility(View.GONE);

                    // tvDesi.setText("Upload Design");
                } else {
                    Log.e(TAG, "SpinnerCategoryData: Fabric");
                    lFabric.setVisibility(View.VISIBLE);
                    linearFabric.setVisibility(View.VISIBLE);
                    lDesign.setVisibility(View.VISIBLE);

                }
                if (febric.equals("No Fabric") && design.equals("Upload Design")) {
                    Log.e(TAG, "SpinnerCategoryData: Fabric else");

                    lFabric.setVisibility(View.INVISIBLE);
                    lDesign.setVisibility(View.VISIBLE);
                    linearFabric.setVisibility(View.VISIBLE);

                    // tvDesi.setText("Upload Design");
                } else {
                    Log.e(TAG, "SpinnerCategoryData: Fabric");
                    lFabric.setVisibility(View.VISIBLE);
                    linearFabric.setVisibility(View.VISIBLE);
                    lDesign.setVisibility(View.INVISIBLE);

                }
                if (febric.equals("No Fabric") || design.equals("Upload Design")) {
                    Log.e(TAG, "SpinnerCategoryData: Fabric else");

                    lFabric.setVisibility(View.INVISIBLE);
                    lDesign.setVisibility(View.VISIBLE);
                    linearFabric.setVisibility(View.VISIBLE);

                    // tvDesi.setText("Upload Design");
                } else {
                    Log.e(TAG, "SpinnerCategoryData: Fabric");
                    lFabric.setVisibility(View.VISIBLE);
                    linearFabric.setVisibility(View.VISIBLE);
                    lDesign.setVisibility(View.INVISIBLE);

                }
                // Showing selected spinner item
                //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void SpinnerWorkData() {


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (BookTailorActivity.this, android.R.layout.simple_spinner_item, Work);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        SpinnerWork.setAdapter(dataAdapter);
        SpinnerWork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                work = item;
                if (item.equals("Alteration")) {
                    //
                    tvachange.setText("Select Quantity");
                    ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>
                            (BookTailorActivity.this, android.R.layout.simple_spinner_item, Quantity);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    SpinnerFabric.setAdapter(dataAdapter);
                    SpinnerFabric.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int item = Integer.parseInt(parent.getItemAtPosition(position).toString());
                            quantity = item;
                            if (item <= 5) {

                                new LovelyInfoDialog(BookTailorActivity.this)
                                        .setTopColorRes(R.color.colorPrimary)
                                        .setIcon(R.drawable.ic_info)
                                        //This will add Don't show again checkbox to the dialog. You can pass any ID as argument
                                        .setNotShowAgainOptionEnabled(1)
                                        .setNotShowAgainOptionChecked(false)
                                        .setTitle(R.string.app_name)
                                        .setMessage(R.string.info_message)
                                        .show();
                                //  Toast.makeText(BookTailorActivity.this, "Please select minimum 5 clothes", Toast.LENGTH_SHORT).show();
                            } else if (item >= 10) {


                                new LovelyInfoDialog(BookTailorActivity.this)
                                        .setTopColorRes(R.color.colorPrimary)
                                        .setIcon(R.drawable.ic_info)
                                        //This will add Don't show again checkbox to the dialog. You can pass any ID as argument
                                        .setNotShowAgainOptionEnabled(1)
                                        .setNotShowAgainOptionChecked(false)
                                        .setTitle(R.string.app_name)
                                        .setMessage(R.string.info_messagess)
                                        .show();
                                Log.e(TAG, "onItemSelected: else" + item);
                            } else {

                                //Toast.makeText(BookTailorActivity.this, "As per quantity of clothes time for delivery is increase", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    // attaching data adapter to spinner
                    SpinnerFabric.setAdapter(dataAdapter);
                    imageDD.setVisibility(View.INVISIBLE);
                    tvSelectCat.setVisibility(View.INVISIBLE);
                    tvDesign.setVisibility(View.INVISIBLE);
                    tvachange.setVisibility(View.VISIBLE);
                    SpinnerFabric.setVisibility(View.VISIBLE);
                    SpinnerDesign.setVisibility(View.INVISIBLE);
                    SpinnerCategory.setVisibility(View.INVISIBLE);
                    linearFabric.setVisibility(View.INVISIBLE);

                } else if (item.equals("Stitching")) {
                    tvachange.setText("Select Fabric");
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                            (BookTailorActivity.this, android.R.layout.simple_spinner_item, Fabric);

                    // Drop down layout style - list view with radio button
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // attaching data adapter to spinner
                    SpinnerFabric.setAdapter(dataAdapter);
                    SpinnerFabric.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String item = adapterView.getItemAtPosition(i).toString();
                            febric = item;
                            if (item.equals("Fabric")) {
                                linearFabric.setVisibility(View.VISIBLE);
                                lFabric.setVisibility(View.VISIBLE);
                                linearFabric.setVisibility(View.VISIBLE);
                                lDesign.setVisibility(View.INVISIBLE);

                            } else {
                                linearFabric.setVisibility(View.INVISIBLE);
                                lFabric.setVisibility(View.INVISIBLE);
                                lDesign.setVisibility(View.INVISIBLE);
                                linearFabric.setVisibility(View.INVISIBLE);

                            }

                            if (febric.equals("No Fabric")) {
                                Log.e(TAG, "SpinnerCategoryData: Fabric else");

                                lFabric.setVisibility(View.GONE);
                                lDesign.setVisibility(View.GONE);
                                linearFabric.setVisibility(View.GONE);

                                // tvDesi.setText("Upload Design");
                            } else {
                                Log.e(TAG, "SpinnerCategoryData: Fabric");
                                lFabric.setVisibility(View.VISIBLE);
                                linearFabric.setVisibility(View.VISIBLE);
                                lDesign.setVisibility(View.INVISIBLE);

                            }

                           /* if (febric.equals("No Fabric") && design.equals("Upload Design")) {
                                Log.e(TAG, "febric.equals(\"No Fabric\") && design.equals(\"Upload Design\")");

                                lFabric.setVisibility(View.INVISIBLE);
                                lDesign.setVisibility(View.VISIBLE);
                                linearFabric.setVisibility(View.VISIBLE);

                                // tvDesi.setText("Upload Design");
                            } else {
                                Log.e(TAG, "febric.equals(\"No Fabric\") && design.equals(\"Upload Design\") else");
                                lFabric.setVisibility(View.VISIBLE);
                                linearFabric.setVisibility(View.VISIBLE);
                                lDesign.setVisibility(View.INVISIBLE);

                            }

                            if (febric.equals("No Fabric") && design.equals("No Design")) {
                                Log.e(TAG, "SpinnerCategoryData: Fabric else");

                                lFabric.setVisibility(View.GONE);
                                lDesign.setVisibility(View.GONE);
                                linearFabric.setVisibility(View.GONE);

                                // tvDesi.setText("Upload Design");
                            } else {
                                Log.e(TAG, "SpinnerCategoryData: Fabric");
                                lFabric.setVisibility(View.VISIBLE);
                                linearFabric.setVisibility(View.VISIBLE);
                                lDesign.setVisibility(View.VISIBLE);

                            }*/
                          /* if (febric.equals("Fabric") && design.equals("No Design")) {
                                Log.e(TAG, "SpinnerCategoryData: Fabric else");

                                lFabric.setVisibility(View.VISIBLE);
                                lDesign.setVisibility(View.GONE);
                                linearFabric.setVisibility(View.VISIBLE);

                                // tvDesi.setText("Upload Design");
                            } else {
                                Log.e(TAG, "SpinnerCategoryData: Fabric");
                                lFabric.setVisibility(View.INVISIBLE);
                                linearFabric.setVisibility(View.VISIBLE);
                                lDesign.setVisibility(View.VISIBLE);

                            }*/
                           /* if (febric.equals("No Fabric") || design.equals("Upload Design")) {
                                Log.e(TAG, "SpinnerCategoryData: Fabric else");

                                lFabric.setVisibility(View.INVISIBLE);
                                lDesign.setVisibility(View.VISIBLE);
                                linearFabric.setVisibility(View.VISIBLE);

                                // tvDesi.setText("Upload Design");
                            } else {
                                Log.e(TAG, "SpinnerCategoryData: Fabric");
                                lFabric.setVisibility(View.VISIBLE);
                                linearFabric.setVisibility(View.VISIBLE);
                                lDesign.setVisibility(View.INVISIBLE);

                            }*/
                            // Showing selected spinner item
                            //Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    tvSelectCat.setText("Select Category");
                    tvSelectCat.setVisibility(View.VISIBLE);
                    SpinnerDesign.setVisibility(View.VISIBLE);
                    SpinnerFabric.setVisibility(View.VISIBLE);
                    tvDesign.setVisibility(View.VISIBLE);
                    tvachange.setVisibility(View.VISIBLE);
                    SpinnerCategory.setVisibility(View.VISIBLE);
                    imageDD.setVisibility(View.VISIBLE);
                    linearFabric.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageFebricc:
                captureFebricImage();
                break;
            case R.id.imageDesignn:
                captureImageDesign();
                break;
            case R.id.btnNextM:
                if (!rbMale.isChecked() && !rbFemale.isChecked()) {
                    SnackBarUtil.showWarning(this, parentLayoutt, "Select Gender");
                    return;
                } else if (febric.equals("febric")) {
                    if (pathPhotoFebric.isEmpty()) {
                        SnackBarUtil.showWarning(this, parentLayoutt, "Capture Fabric Image");
                        return;
                    } else {
                    }
                    if (pathPhotoDesign.isEmpty()) {
                        SnackBarUtil.showWarning(this, parentLayoutt, "Capture Design Image");
                        return;
                    } else {
                    }
                } else if (TextUtils.isEmpty(pathPhotoFebric)) {
                    SnackBarUtil.showWarning(this, parentLayoutt, "Capture Fabric Image ");
                    return;
                } else if (TextUtils.isEmpty(pathPhotoDesign)) {
                    SnackBarUtil.showWarning(this, parentLayoutt, "Capture Design Image ");
                    return;
                } else {
                    // price1 = etPricee.getText().toString().trim();
                    String gender = rbMale.isChecked() ? "Male" : "Female";
                    Intent intent = new Intent(BookTailorActivity.this, BookActivity.class);
                    intent.putExtra("work", work);
                    intent.putExtra("febric", febric);
                    intent.putExtra("category", category);
                    intent.putExtra("design", design);
                    intent.putExtra("quantity", quantity);
                    intent.putExtra("gender", gender);
                    intent.putExtra("pathPhotoDesign", pathPhotoDesign);
                    intent.putExtra("pathPhotoFebric", pathPhotoFebric);


                    startActivity(intent);
                }


                break;
           /* case R.id.btnNextMmm:
                price = etPrice.getText().toString().trim();
                price1 = etPricee.getText().toString().trim();

                Intent intent1 = new Intent(BookTailorActivity.this, BookActivity.class);
                intent1.putExtra("work", work);
                intent1.putExtra("febric", febric);
                intent1.putExtra("category", category);
                intent1.putExtra("design", design);
                intent1.putExtra("price", price);
                intent1.putExtra("price1", price1);

                startActivity(intent1);
                break;*/
        }
    }

    private void captureFebricImage() {
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
            pathPhotoFebric = photoFile.getAbsolutePath();

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this, "online.masterji.honchiSolution.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE_FEBRIC);
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "error", e);
        }

    }

    private void captureImageDesign() {
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
            pathPhotoDesign = photoFile.getAbsolutePath();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE_OWNER && resultCode == RESULT_OK) {
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivPhotoShop.setImageBitmap(imageBitmap);*/
            compressShopPhoto(pathPhotoDesign);
            pathPhotoDesign = "";

        }
        if (requestCode == REQUEST_IMAGE_CAPTURE_FEBRIC && resultCode == RESULT_OK) {
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivPhotoShop.setImageBitmap(imageBitmap);*/
            compressFebricPhoto(pathPhotoFebric);
            pathPhotoFebric = "";

        } else {


        }
    }

    private void compressFebricPhoto(String path) {
        //image compress task callback
        IImageCompressTaskListener iImageCompressTaskListener = new IImageCompressTaskListener() {
            @Override
            public void onComplete(List<File> compressed) {
                File file = compressed.get(0);
                Log.d("ImageCompressor", "New photo size ==> " + file.length()); //log new file size.
                Log.d(TAG, "Compressed Image Path: " + file.getAbsolutePath());
                imageFebricc.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                pathPhotoFebric = file.getAbsolutePath();
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
                imageDesignn.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                pathPhotoDesign = file.getAbsolutePath();
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_price:
                Intent intent = new Intent(this, EnquiryActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //clean up!
        mExecutorService.shutdown();
        mExecutorService = null;

    }

}
