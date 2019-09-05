package online.masterji.honchiSolution.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.Recycler.SolventRecyclerViewAdapterSuggestion;
import online.masterji.honchiSolution.adapter.Category11Adapter;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.custom.drawable.CountDrawable;
import online.masterji.honchiSolution.domain.Cart;
import online.masterji.honchiSolution.domain.Product1;
import online.masterji.honchiSolution.domain.Search;
import online.masterji.honchiSolution.util.RecyclerTouchListener;
import online.masterji.honchiSolution.util.SnackBarUtil;
import online.masterji.honchiSolution.util.WaitDialog;

import static online.masterji.honchiSolution.util.Utility.splitCamelCase;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener, BaseSliderView.OnSliderClickListener {
    private static final String TAG = "ProductActivity";
    RecyclerView rvSugget;
    private LinearLayout parentLayout;
    private SliderLayout mDemoSlider;
    private Button btnAddToCart, btnBuyNow;
    private WaitDialog waitDialog;
    private Product1 product;
    String EXTRA_MESSAGE, productName, category;
    Intent intent;
    ArrayList<HashMap<String, String>> contactList;
    List<Search> searchList;
    String title, Id, position, titlee, descri, description, price, photo1, photo2, id;
    String contact;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    Category11Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
        }
        intent = getIntent();
        // contactList = intent.getParcelableExtra("contactList");
        //Log.e(TAG, "onCreate:contactList " + contactList);

        Id = intent.getStringExtra("Id");
        position = intent.getStringExtra("position");
        contact = intent.getStringExtra("contactList");
        titlee = intent.getStringExtra("titlee");
        descri = intent.getStringExtra("descri");

        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        price = intent.getStringExtra("price");
        photo1 = intent.getStringExtra("photo1");
        photo2 = intent.getStringExtra("photo2");
        id = intent.getStringExtra("id");
        Log.e(TAG, "onCreate: ===========title============" + title);
        Log.e(TAG, "onCreate: ===========description============" + description);
        Log.e(TAG, "onCreate: ===========titlee============" + titlee);
        Log.e(TAG, "onCreate: ===========descri============" + descri);


        Log.e(TAG, "onCreate: ===========position============ \n" + position);
        Log.e(TAG, "onCreate: ===========contact============ \n" + contact);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            product = (Product1) bundle.getSerializable("product");
            Log.e(TAG, "onCreate: product======" + product);
            category = bundle.getString("category");
            productName = bundle.getString("productName");
            // EXTRA_MESSAGE = bundle.getString("EXTRA_MESSAGE");

            Log.e(TAG, "onCreate: productName" + productName);
            //Log.e(TAG, "onCreate: contactList" + contactList);

        }
        initView();
        Log.e(TAG, "onCreate: " + category);
        if (category != null && !category.isEmpty()) {
            if (category.equals("Female Category")) {
                String urlJsonArryM = "https://us-central1-project-masterji.cloudfunctions.net/api/femaleCategoryData?id=" + Id;
                Log.e(TAG, "onCreate: ======" + urlJsonArryM);
                getImages(urlJsonArryM);
            } else {
                String urlJsonArryM = "https://us-central1-project-masterji.cloudfunctions.net/api/maleCategoryData?id=" + Id;
                Log.e(TAG, "onCreate: ======" + urlJsonArryM);

                getImages(urlJsonArryM);
            }
        }


        // getImages();
        setSliders();
    }

    List<Product1> dataList = new ArrayList<>();

    private void getImages(String urlJsonArryM) {

        contactList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(ProductActivity.this);

        JsonArrayRequest req = new JsonArrayRequest(urlJsonArryM,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, "**********response.toString()**********" + response.toString());
                        Log.e(TAG, "********response.length()************" + response.length());


                        try {
                            for (int i = 0; i < response.length(); i++) {


                                JSONObject person = (JSONObject) response
                                        .get(i);
                                //  Data dataa = new Data();


                                String id = person.getString("id");
                                String price = person.getString("price");
                                String title = person.getString("title");
                                String photo1 = person.getString("photo1");
                                String description = person.getString("description");
                                String photo2 = person.getString("photo2");
                                Log.e(TAG, "dataList:=== \n" + id + "price \n" + price + "title \n" + title + "photo1 \n" + photo1 + "description \n" + description + "\n" + photo2);

                                Product1 product1 = new Product1();

                                product1.setPhoto1(photo1);
                                product1.setPhoto2(photo2);
                                product1.setTitle(title);
                                product1.setPrice(price);
                                product1.setDescription(description);
                                product1.setId(id);

                                Log.e(TAG, "onResponse: " + dataList.size());
                                Log.e(TAG, "dataList:=after add== " + dataList.size());
                                dataList.add(product1);
                                Log.e(TAG, "dataList:=after add== " + dataList.size());

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        initGridAdapter();
                        //  hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                // hidepDialog();
            }
        });

        requestQueue.add(req);
    }

    private void initGridAdapter() {
        Log.e(TAG, "initGridAdapter: ");
        SolventRecyclerViewAdapterSuggestion rcAdapter = new SolventRecyclerViewAdapterSuggestion(ProductActivity.this, dataList);
        rvSugget.setAdapter(rcAdapter);

        rvSugget.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvSugget, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


                Log.e(TAG, "onItemClick:getProductid==== " + dataList.get(position).getId());
                Log.e(TAG, "onItemClick:getProductid==== " + dataList.get(position).getProductId());
                Log.e(TAG, "===========:getTitle==== " + dataList.get(position).getTitle());

                Intent intent = new Intent(ProductActivity.this, ProductActivity.class);
                intent.putExtra("product", dataList.get(position));
                intent.putExtra("contactList", dataList.get(position));
                intent.putExtra("category", category);
                intent.putExtra("productName", dataList.get(position).getTitle());
                intent.putExtra("position", position);
                intent.putExtra("Id", Id);
                startActivity(intent);
                finish();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int locationPermission1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (locationPermission1 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private void initView() {
        rvSugget = (RecyclerView) findViewById(R.id.rvSugget);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(ProductActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvSugget.setLayoutManager(horizontalLayoutManagaer);
        parentLayout = findViewById(R.id.parentLayout);
        mDemoSlider = findViewById(R.id.slider);

        waitDialog = new WaitDialog(this);

        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvDescription = findViewById(R.id.tvDescription);

        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBuyNow = findViewById(R.id.btnBuyNow);

        btnAddToCart.setOnClickListener(this);
        btnBuyNow.setOnClickListener(this);
        if (title != null && !title.isEmpty()) {
            String[] tirle = title.split("\n");
            getSupportActionBar().setTitle(splitCamelCase(title));
            tvTitle.setText(title);
            //  tvDescription.setText(tirle[1]);

        }
        if (productName != null && !productName.isEmpty()) {
            getSupportActionBar().setTitle((productName));
            tvTitle.setText(productName);
            //  tvDescription.setText(tirle[1]);

        }


        if (description != null && !description.isEmpty()) {

            tvDescription.setText(description.replace("\\n", System.getProperty("line.separator")));
        } else if (descri != null && !descri.isEmpty()) {

            tvDescription.setText(descri.replace("\\n", System.getProperty("line.separator")));
        }
        if (price != null && !price.isEmpty()) {

            tvPrice.setText(price);
        }
        if (product != null) {
            Log.e(TAG, "initView: product.getDescription()===" + product.getDescription());
            if (product.getDescription() != null) {
                String description = product.getDescription().replace("\\n", System.getProperty("line.separator"));
                Log.e(TAG, "initView: description-------" + description);
                tvDescription.setText(description);
            }
            if (product.getPrice() != null) {
                tvPrice.setText(product.getPrice());

            }
            if (product.getTitle() != null) {
                tvTitle.setText((product.getTitle()));

            }


        }


        try {
            for (Cart cart : Constants.cartList) {
                if (cart != null && cart.getId().equals(product.getId())) {
                    btnAddToCart.setText("Go To Cart");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setSliders() {
        ArrayList<String> listUrl = new ArrayList<>();

        if (!TextUtils.isEmpty(photo1))
            listUrl.add(photo1);
        if (!TextUtils.isEmpty(photo2))
            listUrl.add(photo2);


        if (product != null) {
            if (!TextUtils.isEmpty(product.getPhoto()))
                listUrl.add(product.getPhoto());
            if (!TextUtils.isEmpty(product.getPhoto1()))
                listUrl.add(product.getPhoto1());
            if (!TextUtils.isEmpty(product.getPhoto2()))
                listUrl.add(product.getPhoto2());
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.fitCenter();

        //.diskCacheStrategy(DiskCacheStrategy.NONE)
        //.placeholder(R.drawable.placeholder)
        //.error(R.drawable.placeholder);

        for (int i = 0; i < listUrl.size(); i++) {
            TextSliderView sliderView = new TextSliderView(this);
            // if you want show image only / without description text use DefaultSliderView instead

            // initialize SliderLayout
            sliderView
                    .image(listUrl.get(i))

                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
                    .setOnSliderClickListener(this);
            mDemoSlider.addSlider(sliderView);
        }

        // set Slider Transition Animation
        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnAddToCart:
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(ProductActivity.this, CartActivity.class));

                   /* if (btnAddToCart.getText().equals("Go To Cart")) {
                        startActivity(new Intent(this, CartActivity.class));
                    } else {
                        addToCart();
                    }*/
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }

                break;
            case R.id.btnBuyNow:

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if (btnAddToCart.getText().equals("Go To Cart")) {
                        startActivity(new Intent(this, CartActivity.class));
                    } else {
                        addToCart();

                    }
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
              /*  if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    buyNow();
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }*/

                break;


        }

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        // Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (Constants.cartList != null)
            setCount(Constants.cartList.size(), menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_my_cart:
                startActivity(new Intent(this, CartActivity.class));
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setCount(int count, Menu defaultMenu) {
        MenuItem menuItem = defaultMenu.findItem(R.id.action_menu_my_cart);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

        CountDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(this);
        }

        badge.setCount(count + "");
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }


    private void addToCart() {
        waitDialog.show();
        final Cart cart = new Cart();

        if (product != null) {
            cart.setId(product.getId());
            cart.setPhoto(product.getPhoto1());
            cart.setPrice(product.getPrice());
            cart.setTitle(product.getTitle());
            cart.setSubCategory(productName);
            cart.setSuperCategory(category);
            Log.e(TAG, "addToCart: product.getProductid()" + product.getId());

        } else {
            cart.setId(id);
            cart.setPhoto(photo1);
            cart.setPrice(price);
            cart.setTitle(title);
            cart.setSubCategory(productName);
            cart.setSuperCategory(category);
        }

        //  Log.e(TAG, "addToCart: Constants.userMobile" + Constants.userMobile);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("MJ_Users").document(Constants.Uid).collection("Cart").document(cart.getId())
                .set(cart)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        waitDialog.dismiss();
                        SnackBarUtil.showSuccess(ProductActivity.this, parentLayout, "Added To bucket");
                        btnAddToCart.setVisibility(View.VISIBLE);
                        btnBuyNow.setVisibility(View.GONE);
                        btnAddToCart.setText("Continue");
                        btnBuyNow.setText(" Continue ");
                        Constants.cartList.add(cart);
                        invalidateOptionsMenu();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        waitDialog.dismiss();
                        SnackBarUtil.showError(ProductActivity.this, parentLayout, "Failed Added To Cart,Please Try Again!");
                    }
                });


    }

    private void buyNow() {
        final Cart cart = new Cart();
        cart.setId(product.getId());
        // cart.setProductId(product.getId());
        cart.setPhoto(product.getPhoto1());
        cart.setPrice(product.getPrice());
        cart.setTitle(product.getTitle());
        cart.setCancelStatus(false);
        cart.setCancelReason("");
        cart.setSuperCategory(category);
        cart.setSubCategory(productName);
        Intent intent = new Intent(this, PlaceOrderActivity.class);
        intent.putExtra("buy_now", cart);
        intent.putExtra("category", category);
        intent.putExtra("productName", productName);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "Network & location services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                            showDialogOK("Network and Location Services Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    checkAndRequestPermissions();
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }
}
