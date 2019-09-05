/*
package online.masterji.honchiSolution.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.Recycler.SubActivity;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.custom.drawable.CountDrawable;
import online.masterji.honchiSolution.domain.Cart;
import online.masterji.honchiSolution.domain.Images;
import online.masterji.honchiSolution.domain.Slider;
import online.masterji.honchiSolution.domain.User;
import online.masterji.honchiSolution.util.PrefManager;
import pub.devrel.easypermissions.EasyPermissions;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = "HomeActivity";
*/
/* FirebaseAuth.getInstance().getCurrentUser().getUid();
        //user register device token field add in database *//*


    TextView tvUserName;
    CircleImageView civHeader;
    Intent intent;
    ImageView imageOffers, imageOffers1, imageOffers2;
    String name;
    Slider slider;
    List<Slider> sliders;
    private final int TIMEOUT = 3000;
    ImageView imageBack;
    private BottomNavigationView bottomNavigationView;
    EditText ETsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        PrefManager prefManager = new PrefManager(getApplicationContext());

        if (prefManager.isFirstTimeLaunch()) {
            prefManager.setFirstTimeLaunch(false);
            startActivity(new Intent(HomeActivity.this, WelcomeActivity.class));
            finish();
        }
        Log.e(TAG, "onCreate: time========================" + Timestamp.now().toDate().toString());
        printHashKey(this);
        initView();
        startTimer();
        //getTopSlider();
        getOffer();
        subscribeToTopic();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Constants.userMobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().replace("+91", "").replace("-", "").trim();
            Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.d(TAG, "Constants.userMobile=" + Constants.userMobile);
            getUser();
            getCart();

        }
    }

    private void subscribeToTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("users")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (!task.isSuccessful()) {
                            Log.e("err", "error in setting up topic");
                        } else {
                            Log.i("info", "subscribed to topic successfully");
                        }
                    }
                });

    }

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }

    private void getOffer() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference collectionReference = db.collection("Offers");
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            List<Images> sliders = task.getResult().toObjects(Images.class);
                            setOffers(sliders.get(0));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });
    }

    private void setOffers(Images images) {

      */
/*  Glide.with(HomeActivity.this)
                .load(images.getPhoto())
                .centerInside()
                .placeholder(R.drawable.coupleoffer)
                .into(imageOffers);*//*

        */
/*Glide.with(HomeActivity.this)
                .load(images.getPhoto1())
                .centerInside()
                .placeholder(R.drawable.coupleoffer)
                .into(imageOffers1);
        Glide.with(HomeActivity.this)
                .load(images.getPhoto2())
                .centerInside()
                .placeholder(R.drawable.coupleoffer)
                .into(imageOffers2);*//*

    }

    private void startTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //  showPermissionDialog();
                if (EasyPermissions.hasPermissions(HomeActivity.this, Constants.permissions)) {
                    //Toast.makeText(this, "Opening camera", Toast.LENGTH_SHORT).show();


                } else {

                    showPermissionDialog(HomeActivity.this);

                    */
/*EasyPermissions.requestPermissions(HomeActivity.this, "We need permissions",
                            123, Constants.permissions);*//*

                }
            }
        }, TIMEOUT);
    }

    private void showPermissionDialog(HomeActivity homeActivity) {

        final Dialog dialog = new Dialog(homeActivity);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialoge_permission);

        //dialog.setTitle("Sample");
        final TextView tvGive = (TextView) dialog.findViewById(R.id.tvGive);
        final TextView Tvlater = (TextView) dialog.findViewById(R.id.Tvlater);
        Tvlater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        tvGive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (EasyPermissions.hasPermissions(HomeActivity.this, Constants.permissions)) {
                    //Toast.makeText(this, "Opening camera", Toast.LENGTH_SHORT).show();


                } else {

                    EasyPermissions.requestPermissions(HomeActivity.this, "We need permissions",
                            123, Constants.permissions);
                }

            }
        });
        dialog.show();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        invalidateOptionsMenu();
    }

    private void initView() {
        ETsearch = findViewById(R.id.ETsearch);
       */
/* bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_profile:
                                Intent intent = new Intent(HomeActivity.this, NavActivity.class);
                                startActivity(intent);

                                // TODO
                                return true;
                            case R.id.action_bookings:
                                // TODO
                                return true;
                            case R.id.action_faq:
                                // TODO
                                return true;
                            case R.id.action_billing:
                                // TODO
                                return true;
                            case R.id.action_chat:
                                // TODO
                                return true;
                        }
                        return false;
                    }
                });*//*

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            slider = (Slider) extras.getSerializable("sliders");
            // sliders.add(slider);


            //setSliders(sliders.get(0));

            // do something with the customer
        }
        // setSliders(sliders.get(0));
        Toolbar toolbar = findViewById(R.id.toolbar);

        */
/*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*//*

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
       */
/*//*
/ NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.setSelected(false);

        *//*
*/
/*get Navigation Header view*//*
*/
/*
        View hView = navigationView.getHeaderView(0);*//*

     */
/*   tvUserName = hView.findViewById(R.id.tvUserNamee);
        civHeader = hView.findViewById(R.id.civHeader);*//*


        imageBack = findViewById(R.id.imageBack);


        findViewById(R.id.layoutMale).setOnClickListener(this);
        findViewById(R.id.layoutFemle).setOnClickListener(this);
        //findViewById(R.id.layoutKids).setOnClickListener(this);
        findViewById(R.id.layoutCatalog).setOnClickListener(this);
        findViewById(R.id.layoutEnquiry).setOnClickListener(this);
        imageOffers = findViewById(R.id.imageOfferss);
      */
/*  imageOffers1 = findViewById(R.id.imageOfferss1);
        imageOffers2 = findViewById(R.id.imageOfferss2);
      *//*

        imageOffers.setOnClickListener(this);
      */
/*  imageOffers1.setOnClickListener(this);
        imageOffers2.setOnClickListener(this);*//*


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart_menu, menu);
       */
/* final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));*//*

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
           */
/* case R.id.action_search:
                // startActivity(new Intent(this, CartActivity.class));
                break;*//*

            case R.id.action_menu_my_cart:
                startActivity(new Intent(this, CartActivity.class));
                break;
           */
/* case R.id.action_menu_my_noti:
                startActivity(new Intent(this, NotificationActivity.class));
                break;*//*

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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart) {
            startActivity(new Intent(this, CartActivity.class));
        } else if (id == R.id.nav_my_order) {

            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                startActivity(new Intent(this, MyOrderActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }

        } else if (id == R.id.nav_custom_design) {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_offers) {

            intent = new Intent(this, OffersActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_measurement) {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_profile) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                startActivity(new Intent(this, ProfileActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }

        } else if (id == R.id.nav_facebook_chat) {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_share_app) {
            shareMasterji();

        } else if (id == R.id.nav_help) {
            callToHelpCenter();

        } else if (id == R.id.nav_payment) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://imjo.in/taNnZD")));


        } else if (id == R.id.nav_about_us) {
            startActivity(new Intent(this, AboutUsActivity.class));

        } else if (id == R.id.nav_tailor_registraion) {

            //startActivity(new Intent(this, TailorRegistrationActivity.class));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callToHelpCenter() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + "+91-9902127313"));
        startActivity(intent);
    }

    private void shareMasterji() {
        try {
            String text = "MasterJi is custom crafted, bespoke tailoring application intended to offer quality sewed pieces of clothing, dress modifications, with guaranteed bother free fitting at moderate costs with free get and conveyance administrations."
                    + "\nDownload Android app now "
                    + "\nhttps://play.google.com/store/apps/details?id=online.masterji.honchi&hl=en";

            Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(),
                    BitmapFactory.decodeResource(getResources(), R.drawable.masterji_logo_for_share), null, null));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.setType("image/*");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share images..."));


        } catch (Exception e) {
            Log.e(TAG, "Can not Share ", e);
        }
    }


    private void getUser() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("MJ_Users").document(Constants.Uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                if (user != null) name = user.getFullname();
             */
/*   Log.e(TAG, "onSuccess:getUid " + user.getUid());
                Log.e(TAG, "onSuccess:getToken " + user.getToken());
                Log.e(TAG, "onSuccess:Date " + user.getDate());*//*


                //  tvUserName.setText(name);


                try {
                    String sampleString = name;
                    String[] names = sampleString.split(" ");

                    for (int i = 0; i < names.length; i++) {


                        // tvUserName.setText(names[0] + "\n" + names[1] + " " + names[2]);
                    }
                } catch (Exception e) {
                    // tvUserName.setText(name);
                    e.printStackTrace();
                }

                try {
                    if (user.getPhoto() != null) {
                        if (!TextUtils.isEmpty(user.getPhoto())) {
                            Glide.with(HomeActivity.this)
                                    .load(user.getPhoto())
                                    .centerInside()
                                    .placeholder(R.drawable.ic_menu_help)
                                    .into(civHeader);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }


    private void getCart() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("MJ_Users").document(Constants.Uid).collection("Cart");
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            List<Cart> cartList = task.getResult().toObjects(Cart.class);

                            for (Cart cart : cartList) {
                                Log.d(TAG, "cart id  => " + cart.getId());
                            }

                            if (cartList != null) {
                                Constants.cartList = cartList;
                                //Constants.cartSize = cartList.size();
                            } else {
                                Constants.cartList = new ArrayList<>();
                            }
                            invalidateOptionsMenu();


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            Constants.cartList = new ArrayList<>();
                        }

                    }
                });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutMale:
                intent = new Intent(this, SubActivity.class);
                intent.putExtra("category", "Male Category");
                startActivity(intent);

              */
/*  intent = new Intent(this, CategoryActivity.class);
                intent.putExtra("category", "Male");
                startActivity(intent);*//*

                break;
            case R.id.layoutFemle:
                intent = new Intent(this, SubActivity.class);
                intent.putExtra("category", "Female Category");
                startActivity(intent);
                break;
         */
/*   case R.id.layoutKids:
                intent = new Intent(this, CategoryActivity.class);
                intent.putExtra("category", "Kids");
                startActivity(intent);
                break;*//*

            case R.id.layoutEnquiry:

                intent = new Intent(this, BookTailorActivity.class);
                startActivity(intent);

                //callToHelpCenter();
                break;
            case R.id.imageOfferss:

                intent = new Intent(this, OffersActivity.class);
                startActivity(intent);

                //callToHelpCenter();
                break;
           */
/* case R.id.imageOfferss1:

                intent = new Intent(this, OffersActivity.class);
                startActivity(intent);

                //callToHelpCenter();
                break;
            case R.id.imageOfferss2:
                intent = new Intent(this, OffersActivity.class);
                startActivity(intent);

                //callToHelpCenter();
                break;*//*

            case R.id.layoutCatalog:
                intent = new Intent(this, CatelogActivity.class);
                intent.putExtra("category", "Raymond");
                startActivity(intent);


                //Toast.makeText(this, "Coming Soon", Toast.LENGTH_LONG).show();
                break;
        }

    }


}
*/
