package online.masterji.honchiSolution.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.custom.drawable.CountDrawable;
import online.masterji.honchiSolution.domain.Cart;
import online.masterji.honchiSolution.domain.Images;
import online.masterji.honchiSolution.domain.User;
import online.masterji.honchiSolution.domain.Userdetails;
import online.masterji.honchiSolution.fragment.BillingFragment;
import online.masterji.honchiSolution.fragment.HomeFragment;
import online.masterji.honchiSolution.fragment.OrderFragmentNew;
import online.masterji.honchiSolution.fragment.TrackFragment;
import online.masterji.honchiSolution.fragment.dummy.ChatttFragment;
import online.masterji.honchiSolution.util.PrefManager;
import online.masterji.honchiSolution.util.VersionChecker;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    TextView tvUserName;
    CircleImageView civHeader;
    Intent intent;
    ImageView imageOffers, imageOffers1, imageOffers2;
    String name;
    private final int TIMEOUT = 3000;
    private BottomNavigationView bottomNavigationView;
    final Fragment fragment1 = new HomeFragment();
    final Fragment fragment2 = new OrderFragmentNew();
    final Fragment fragment3 = new TrackFragment();
    final Fragment fragment4 = new BillingFragment();
    //final Fragment fragment5 = new ChattingFragmentNew();
    //final Fragment fragment5 = new InboxFragment();
    final Fragment fragment6 = new ChatttFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    static int currentVersion;
    static String currentVersionn;
    static String latestVersion;
    Dialog dialog;
    public static String nameStr, idStr, imagesStr;
    // public static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(23.2599, 77.4126), new LatLng(22.7196, 75.8577));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PrefManager prefManager = new PrefManager(getApplicationContext());


       /* try {

            currentVersionn = String.valueOf(Integer.parseInt(getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0).versionName));

            Log.e("Current Version","::"+currentVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
*/
        /*getCurrentVersion();

        getVersionCode(this);
*/
        getVersionDatabase(getVersionCode(this));

        boolean b = VersionChecker.checkVersionIsUpdated(getApplicationContext());
        Log.e(TAG, "onCreate: " + getVersionCode(this));
        Log.e(TAG, "onCreate: VersionChecker    " + b);
        if (b) {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Update");
            alertDialog.setIcon(R.drawable.masterji_logo_round);
            alertDialog.setMessage("Masterji app New Update is available");

            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        //  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                    }
                }
            });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertDialog.show();
        } else {

        }
        if (prefManager.isFirstTimeLaunch()) {
            prefManager.setFirstTimeLaunch(false);
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            finish();
        }
        Log.e(TAG, "onCreate: time========================" + Timestamp.now().toDate().toString());
        printHashKey(this);
        initView();
        startTimer();
        //getTopSlider();
        getOffer();
        subscribeToTopic();
        getSearchApi();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Constants.userMobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().replace("+91", "").replace("-", "").trim();
            Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.d(TAG, "Constants.userMobile=" + Constants.userMobile);
            getUser();
            getCart();

        }
        //   loadFragment(new HomeFragment());

        fm.beginTransaction().add(R.id.fragment_container, fragment6, "6").hide(fragment6).commit();
        // fm.beginTransaction().add(R.id.fragment_container, fragment5, "5").hide(fragment5).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment1, "1").commit();
        Toolbar toolbar = findViewById(R.id.toolbar);

        //getting bottom navigation view and attaching the listener
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);//RemoveShiftMode(bottomNavigationView);
    }


    public static String Algolia_api_key;
    public static String Algolia_app_id;
    public static String Algolia_index;

    /* private void updateDatabase(String fullname, String photo, String id) {
         reference = FirebaseDatabase.getInstance().getReference();
         Log.e(TAG, "updateDatabase:fullname+photo+id " + fullname + photo + id);
         String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
         Log.e(TAG, "getUserDAtaID: " + Constants.Uid);


        *//* DatabaseReference userMessageKeyRef = reference.child("UserList")
                .child(Uid).push();*//*
        UserChatting user = new UserChatting(fullname, photo, id);

        //reference.child("usersList").child("5KktiHZb7aSbaFqZJUiSF8fhzYn2").setValue(user);
        reference.child("usersList").child("5KktiHZb7aSbaFqZJUiSF8fhzYn2").setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "onSuccess: ");
                        // Write was successful!
                        // ...
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                    }
                });

       *//* Map messageTextBody = new HashMap();
        messageTextBody.put("fullname", fullname);
        messageTextBody.put("photo", photo);
        messageTextBody.put("id", id);
        messageTextBody.put("time", saveCurrentTime);
        messageTextBody.put("date", saveCurrentDate);

        reference.updateChildren(messageTextBody).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Message Sent Successfully...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });*//*
    }
*/


    private void getVersionDatabase(int versionCode) {

        //Version
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> nestedData = new HashMap<>();
        nestedData.put("currentVersion", versionCode);
        nestedData.put("latestVersion", latestVersion);
        nestedData.put("new", "1");
        db.collection("Version").document()
                .update(nestedData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        FirebaseFirestore db1 = FirebaseFirestore.getInstance();

        db1.collection("Version")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " 9999=> " + document.getData());
                                Log.d(TAG, "currentVersion" + " 9999=> " + document.getData().get("currentVersion"));

                                currentVersionn = document.getData().get("currentVersion").toString();

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }


    private void getSearchApi() {

        Userdetails userdetails = new Userdetails();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("keys");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());

                        Algolia_api_key = String.valueOf(document.getData().get("ALGOLIA_API_KEY"));
                        Algolia_app_id = String.valueOf(document.getData().get("ALGOLIA_APP_ID"));
                        Algolia_index = String.valueOf(document.getData().get("ALGOLIA_INDEX"));
                        Log.e(TAG, "onSuccess: ===2222222222==" + Algolia_api_key);
                        Log.e(TAG, "onSuccess: ===2222222222==" + document.getData().get("ALGOLIA_APP_ID"));
                        Log.e(TAG, "onSuccess: ===2222222222==" + document.getData().get("ALGOLIA_INDEX"));

                    }
                    List<Userdetails> sliders = task.getResult().toObjects(Userdetails.class);

                    try {
                        Log.e(TAG, "onSuccess: ==66666666666666===" + sliders.get(0).getAlgolia_api_key());
                        Log.e(TAG, "onSuccess: ===66666666666666==" + sliders.get(0).getAlgolia_app_id());
                        Log.e(TAG, "onSuccess: ===66666666666666==" + sliders.get(0).getAlgolia_index());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    //setOffers(sliders.get(0));
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
      /*  DocumentReference collectionReference = db.collection("keys");
       // DocumentReference collectionReference = db.collection("keys").document("ALGOLIA");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Userdetails city = documentSnapshot.toObject(Userdetails.class);

                Algolia_api_key = city.getAlgolia_api_key();
                Algolia_app_id = city.getAlgolia_app_id();
                Algolia_index = city.getAlgolia_index();
                Log.e(TAG, "onSuccess: ==66666666666666===" + city.getAlgolia_api_key());
                Log.e(TAG, "onSuccess: ===66666666666666==" + city.getAlgolia_app_id());
                Log.e(TAG, "onSuccess: ===66666666666666==" + city.getAlgolia_index());
            }
        });
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Userdetails city = document.toObject(Userdetails.class);
                            List<Userdetails> userdetails = new ArrayList<>();
                            if (document.exists()) {
                                userdetails.add(city);
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData().entrySet());
                                Log.d(TAG, "DocumentSnapshot data: " + userdetails.get(0).getAlgolia_api_key());
                                Log.d(TAG, "DocumentSnapshot data: " + userdetails.get(0).getAlgolia_index());
                                Log.d(TAG, "DocumentSnapshot data: " + userdetails.get(0).getAlgolia_app_id());
                            } else {
                                Log.d(TAG, "No such document");
                            }


                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }

                    }
                });*/

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
                            // setOffers(sliders.get(0));
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });
    }

    private void setOffers(Images images) {

      /*  Glide.with(HomeActivity.this)
                .load(images.getPhoto())
                .centerInside()
                .placeholder(R.drawable.coupleoffer)
                .into(imageOffers);*/
        /*Glide.with(HomeActivity.this)
                .load(images.getPhoto1())
                .centerInside()
                .placeholder(R.drawable.coupleoffer)
                .into(imageOffers1);
        Glide.with(HomeActivity.this)
                .load(images.getPhoto2())
                .centerInside()
                .placeholder(R.drawable.coupleoffer)
                .into(imageOffers2);*/
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

    private void startTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //  showPermissionDialog();
                if (EasyPermissions.hasPermissions(MainActivity.this, Constants.permissions)) {
                    //Toast.makeText(this, "Opening camera", Toast.LENGTH_SHORT).show();


                } else {

                    showPermissionDialog(MainActivity.this);

                    /*EasyPermissions.requestPermissions(MainActivity.this, "We need permissions",
                            123, Constants.permissions);*/
                }
            }
        }, TIMEOUT);
    }

    private void showPermissionDialog(MainActivity homeActivity) {

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
                if (EasyPermissions.hasPermissions(MainActivity.this, Constants.permissions)) {
                    //Toast.makeText(this, "Opening camera", Toast.LENGTH_SHORT).show();


                } else {

                    EasyPermissions.requestPermissions(MainActivity.this, "We need permissions",
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

        Toolbar toolbar = findViewById(R.id.toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }
      /*  int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();


        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {

                super.onBackPressed();
            }
        }
    }*/
        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {*/
    // super.onBackPressed();
      /*  new AlertDialog.Builder(getApplicationContext())
                .setTitle("Close App")
                .setMessage("Are you sure you want to Close this app?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        onBackPressed();
                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();*/
    /* }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart_menu, menu);
       /* final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));*/
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
            case R.id.action_menu_my_call:
                if (EasyPermissions.hasPermissions(MainActivity.this, Constants.permissions)) {
                    //Toast.makeText(this, "Opening camera", Toast.LENGTH_SHORT).show();
                    callToHelpCenter();

                } else {

                    EasyPermissions.requestPermissions(MainActivity.this, "We need permissions",
                            123, Constants.permissions);
                }


              /*  if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    // startActivity(new Intent(this, ProfileActivity.class));
                    startActivity(new Intent(this, ProfileActivity.class));
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
*/

                // startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.action_menu_my_noti:

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    // startActivity(new Intent(this, ProfileActivity.class));
                    startActivity(new Intent(this, ProfileActivity.class));
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }


                // startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.action_menu_my_cart:
              /*  Log.e(TAG, "onOptionsItemSelected: ");
                String tittle = "tittle";
                String subject = "tittle";
                String body = "tittle";

                NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify = new Notification.Builder
                        (getApplicationContext()).setContentTitle(tittle).setContentText(body).
                        setContentTitle(subject).setSmallIcon(R.drawable.ic_stat_name_logo).build();

                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.notify(0, notify);
                Log.e(TAG, "onOptionsItemSelected:end ");
*/

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(this, CartActivity.class));
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
                //startActivity(new Intent(this, CartActivity.class));
                break;
           /* case R.id.action_menu_my_noti:
                startActivity(new Intent(this, NotificationActivity.class));
                break;*/
        }
        return super.

                onOptionsItemSelected(item);

    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.action_profile:
                    //   item.setIcon(R.drawable.ic_home_white_24dp);
                    getSupportActionBar().setTitle("Masterji");
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    fm.beginTransaction().hide(fragment2).commit();
                    ;
                    fm.beginTransaction().hide(fragment3).commit();
                    ;
                    fm.beginTransaction().hide(fragment4).commit();
                    ;
                    fm.beginTransaction().hide(fragment6).commit();
                    ;
                    fm.beginTransaction().show(fragment1).commit();
                    ;
                    active = fragment1;

/*
 getSupportActionBar().setTitle("Masterji");

                    fragment = new HomeFragment();
*/

                    return true;

                case R.id.action_bookings:
                    // item.setIcon(R.drawable.ic_planning_white);

                    getSupportActionBar().setTitle("Order");
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    fm.beginTransaction().hide(fragment1).commit();
                    ;
                    fm.beginTransaction().hide(fragment3).commit();
                    ;
                    fm.beginTransaction().hide(fragment4).commit();
                    ;
                   fm.beginTransaction().hide(fragment6).commit();
                    ;
                    fm.beginTransaction().show(fragment2).commit();
                    ;

                    active = fragment2;
                    // fragment = new OrderFragment();

                    // TODO
                    return true;
/*
                case R.id.action_track:
                    //  item.setIcon(R.drawable.ic_logistics_white);

                    getSupportActionBar().setTitle("Track");
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                   *//* getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                    fragment = new TrackFragment();
*//*
                    return true;*/

                case R.id.action_billing:
                    //  item.setIcon(R.drawable.ic_invoice_white);

                    getSupportActionBar().setTitle("Billing");
                    fm.beginTransaction().hide(active).show(fragment4).commit();
                    fm.beginTransaction().hide(fragment1).commit();
                    ;
                    fm.beginTransaction().hide(fragment3).commit();
                    ;
                    fm.beginTransaction().hide(fragment2).commit();
                    ;
                      fm.beginTransaction().hide(fragment6).commit();
                    //  ;
                    fm.beginTransaction().show(fragment4).commit();
                    ;

                    active = fragment4;
                   /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                    fragment = new BillingFragment();
*/
                    return true;
                case R.id.action_faq:
                    //  item.setIcon(R.drawable.ic_invoice_white);

                    getSupportActionBar().setTitle("FAQ");
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    fm.beginTransaction().hide(fragment1).commit();

                    fm.beginTransaction().hide(fragment4).commit();

                    fm.beginTransaction().hide(fragment2).commit();

                     fm.beginTransaction().hide(fragment6).commit();

                    fm.beginTransaction().show(fragment3).commit();


                    active = fragment3;
                   /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                    fragment = new BillingFragment();
*/
                    return true;

                case R.id.action_chat:
                    Log.e(TAG, "onNavigationItemSelected:action_chat " );
                    //item.setIcon(R.drawable.ic_chat_white);
                    //   Toast.makeText(MainActivity.this, "Still working", Toast.LENGTH_SHORT).show();
                    getSupportActionBar().setTitle("Chatting");

                  /*  fm.beginTransaction().hide(active).show(fragment6).commit();
                    fm.beginTransaction().hide(fragment1).commit();
                    fm.beginTransaction().hide(fragment3).commit();
                    fm.beginTransaction().hide(fragment2).commit();
                    fm.beginTransaction().hide(fragment4).commit();
                    fm.beginTransaction().show(fragment6).commit();
                    ;
                    active = fragment6;
                 */ //  fragment = new ChattingFragmentNew();

/*
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        Intent intent = new Intent(MainActivity.this, ChatActivityMain.class);
                        intent.putExtra("MainActivity", "MainActivity");
                        intent.putExtra("list", "Masterji");
                        intent.putExtra("imagesstrings", "https://lh6.googleusercontent.com/-gfHhONjkkq8/AAAAAAAAAAI/AAAAAAAAAB0/WrX4Y4oeQ80/s96-c/photo.jpg");
                        intent.putExtra("id", "BfX8tqKA2cRVvWozWMldbHdKcfj1");
                        startActivity(intent);

                    } else {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));

                    }*/
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                       /* fm.beginTransaction().hide(active).show(fragment5).commit();
                        fm.beginTransaction().hide(fragment1).commit();
                        ;
                        fm.beginTransaction().hide(fragment3).commit();
                        ;
                        fm.beginTransaction().hide(fragment2).commit();
                        ;
                        fm.beginTransaction().hide(fragment4).commit();
                        fm.beginTransaction().hide(fragment6).commit();
                        ;
                        fm.beginTransaction().show(fragment5).commit();
                        ;
                        active = fragment5;
                        fragment = new ChattingFragmentNew();
*/
                        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                            if (FirebaseAuth.getInstance().getUid().equals("BfX8tqKA2cRVvWozWMldbHdKcfj1")) {
                                Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                Log.e(TAG, "getUserDAtaID:1 " + Constants.Uid);
                                // active = fragment6;
                               /* nameStr = "";
                                idStr = Constants.Uid;
                                imagesStr = "";
                              */
                                idStr = "BfX8tqKA2cRVvWozWMldbHdKcfj1";
                                nameStr = "deeksha gupta";
                                imagesStr = "https://lh6.googleusercontent.com/-gfHhONjkkq8/AAAAAAAAAAI/AAAAAAAAAB0/WrX4Y4oeQ80/s96-c/photo.jpg";
                                fragment = new ChatttFragment();
                                fm.beginTransaction().hide(active).show(fragment6).commit();
                                fm.beginTransaction().hide(fragment1).commit();
                                fm.beginTransaction().hide(fragment3).commit();
                                fm.beginTransaction().hide(fragment2).commit();
                                fm.beginTransaction().hide(fragment4).commit();
                                // fm.beginTransaction().hide(fragment5).commit();
                                fm.beginTransaction().show(fragment6).commit();

                                active = fragment6;

                            } else if (FirebaseAuth.getInstance().getUid().equals("5KktiHZb7aSbaFqZJUiSF8fhzYn2")) {
                                //  active = fragment6;
                                idStr = Constants.Uid;
                                idStr = "5KktiHZb7aSbaFqZJUiSF8fhzYn2";
                                nameStr = "deeps malvi";
                                imagesStr = "https://lh6.googleusercontent.com/-WNcBrHSRLeo/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3relhSfVzGoMvCg7pe3lmyHx-UtyVg/s96-c/photo.jpg";
                                Log.e(TAG, "getUserDAtaID: 2" + Constants.Uid);
                                fragment = new ChatttFragment();
                                active = fragment6;

                                fm.beginTransaction().hide(active).show(fragment6).commit();
                                fm.beginTransaction().hide(fragment1).commit();
                                fm.beginTransaction().hide(fragment3).commit();
                                fm.beginTransaction().hide(fragment2).commit();
                                fm.beginTransaction().hide(fragment4).commit();
                                // fm.beginTransaction().hide(fragment5).commit();
                                fm.beginTransaction().show(fragment6).commit();

                                //  active = fragment5;



                            } else if (FirebaseAuth.getInstance().getUid().equals("MWklcsqWViN1m2vIwQcyqcOWo893")) {
                                nameStr = "";
                                idStr = Constants.Uid;
                                imagesStr = "";
                                nameStr = "diksha gupta";
                                imagesStr = "https://lh5.googleusercontent.com/-YZHqM0b2Po8/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3reQxSpodnBzUp3nxmzNpS_ZtLk8dg/s96-c/photo.jpg";
                                idStr = "MWklcsqWViN1m2vIwQcyqcOWo893";
                                Log.e(TAG, "getUserDAtaID: 3" + Constants.Uid);

                                fragment = new ChatttFragment();
                                //  active = fragment6;
                                fm.beginTransaction().hide(active).show(fragment6).commit();
                                fm.beginTransaction().hide(fragment1).commit();
                                fm.beginTransaction().hide(fragment3).commit();
                                fm.beginTransaction().hide(fragment2).commit();
                                fm.beginTransaction().hide(fragment4).commit();
                                // fm.beginTransaction().hide(fragment5).commit();
                                fm.beginTransaction().show(fragment6).commit();

                                active = fragment6;


                            } else if (FirebaseAuth.getInstance().getUid().equals("N5KmELyGB8QrlWdgjMOUiofK3m13")) {
                                nameStr = "";
                                idStr = Constants.Uid;
                                imagesStr = "";
                                nameStr = "utkarsh kumar";
                                imagesStr = "https://lh5.googleusercontent.com/-wLbQRKzgGXY/AAAAAAAAAAI/AAAAAAAAqrI/PPHMMaQUN_o/s96-c/photo.jpg";
                                idStr = "N5KmELyGB8QrlWdgjMOUiofK3m13";
                                Log.e(TAG, "getUserDAtaID: 4" + Constants.Uid);

                                fragment = new ChatttFragment();
                                //  active = fragment6;
                                fm.beginTransaction().hide(active).show(fragment6).commit();
                                fm.beginTransaction().hide(fragment1).commit();
                                fm.beginTransaction().hide(fragment3).commit();
                                fm.beginTransaction().hide(fragment2).commit();
                                fm.beginTransaction().hide(fragment4).commit();
                                //fm.beginTransaction().hide(fragment5).commit();
                                fm.beginTransaction().show(fragment6).commit();
                                active = fragment6;


                            } else if (FirebaseAuth.getInstance().getUid().equals("WIQFi2OFViWMeFE5pI4RKEaTrM82")) {
                                nameStr = "";
                                idStr = Constants.Uid;
                                imagesStr = "";
                                nameStr = "shikha sharma";
                                imagesStr = "https://lh3.googleusercontent.com/-r8oLxAgnzik/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3rcdd_1KyH6eJtnZitopSNyJ0lpuWg/s96-c/photo.jpg";
                                idStr = "WIQFi2OFViWMeFE5pI4RKEaTrM82";
                                Log.e(TAG, "getUserDAtaID: 5" + Constants.Uid);

                                fragment = new ChatttFragment();
                                //  active = fragment6;
                                fm.beginTransaction().hide(active).show(fragment6).commit();
                                fm.beginTransaction().hide(fragment1).commit();
                                fm.beginTransaction().hide(fragment3).commit();
                                fm.beginTransaction().hide(fragment2).commit();
                                fm.beginTransaction().hide(fragment4).commit();
                                // fm.beginTransaction().hide(fragment5).commit();
                                fm.beginTransaction().show(fragment6).commit();
                                active = fragment6;


                            } else if (FirebaseAuth.getInstance().getUid().equals("TwNBTwPMeaQgUf2LtaxX6B1Uw7n1")) {
                                //  active = fragment6;
                                nameStr = "";
                                idStr = Constants.Uid;
                                imagesStr = "";
                                nameStr = "nirupama Lowanshi";
                                idStr = "TwNBTwPMeaQgUf2LtaxX6B1Uw7n1";
                                Log.e(TAG, "getUserDAtaID: 6 " + Constants.Uid);

                                imagesStr = "https://lh5.googleusercontent.com/-aSi5PSDcMfY/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3rchliQkzgHC21qyIYqkK43JcJ5qQg/s96-c/photo.jpg";

                                fragment = new ChatttFragment();
                                fm.beginTransaction().hide(active).show(fragment6).commit();
                                fm.beginTransaction().hide(fragment1).commit();
                                fm.beginTransaction().hide(fragment3).commit();
                                fm.beginTransaction().hide(fragment2).commit();
                                fm.beginTransaction().hide(fragment4).commit();
                                // fm.beginTransaction().hide(fragment5).commit();
                                fm.beginTransaction().show(fragment6).commit();

                                active = fragment6;

                            } /*else if (FirebaseAuth.getInstance().getUid().equals("dKxjxLvOAXdgxOLiFgzUfMR733t1")) {
                                nameStr = "";
                                idStr = Constants.Uid;
                                imagesStr = "";
                                idStr = "dKxjxLvOAXdgxOLiFgzUfMR733t1";
                                nameStr = "Sanjay";

                                imagesStr = "https://lh6.googleusercontent.com/-WwrwoX0QviU/AAAAAAAAAAI/AAAAAAAAF-M/oa_JXLE8wd0/s96-c/photo.jpg";
                                fragment = new ChatttFragment();
                                //  active = fragment6;
                                fm.beginTransaction().hide(active).show(fragment6).commit();
                                fm.beginTransaction().hide(fragment1).commit();
                                fm.beginTransaction().hide(fragment3).commit();
                                fm.beginTransaction().hide(fragment2).commit();
                                fm.beginTransaction().hide(fragment4).commit();
                                fm.beginTransaction().hide(fragment5).commit();
                                fm.beginTransaction().show(fragment6).commit();

                                active = fragment6;


                            }*/ else {
                                Log.e(TAG, "getUserDAtaID:7 " + Constants.Uid);

                                idStr = "BfX8tqKA2cRVvWozWMldbHdKcfj1";
                                nameStr = "deeksha gupta";
                                imagesStr = "https://lh6.googleusercontent.com/-gfHhONjkkq8/AAAAAAAAAAI/AAAAAAAAAB0/WrX4Y4oeQ80/s96-c/photo.jpg";
                               /* fm.beginTransaction().hide(active).show(fragment6).commit();
                                fm.beginTransaction().hide(fragment1).commit();
                                fm.beginTransaction().hide(fragment3).commit();
                                fm.beginTransaction().hide(fragment2).commit();
                                fm.beginTransaction().hide(fragment4).commit();
                                //fm.beginTransaction().hide(fragment5).commit();
                                fm.beginTransaction().show(fragment6).commit();

                                active = fragment6;
                                fragment = new ChatttFragment();*/

                                updateData();
                                idStr = "5KktiHZb7aSbaFqZJUiSF8fhzYn2";
                                nameStr = "deeps malvi";
                                imagesStr = "https://lh6.googleusercontent.com/-WNcBrHSRLeo/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3relhSfVzGoMvCg7pe3lmyHx-UtyVg/s96-c/photo.jpg";


                                //fragment = new ChattingFragmentNew();
                            }

                            /*===========================*//*
                             *//* fm.beginTransaction().hide(active).show(fragment5).commit();
                            fm.beginTransaction().hide(fragment1).commit();
                            fm.beginTransaction().hide(fragment3).commit();
                            fm.beginTransaction().hide(fragment2).commit();
                            fm.beginTransaction().hide(fragment4).commit();
                            fm.beginTransaction().show(fragment5).commit();
                            fm.beginTransaction().hide(fragment6).commit();

                            active = fragment5;
                            fragment = new ChattingFragmentNew();*//*
                            //fragment = new ChatttFragment();
                        }*/
                        }
                    } else {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                    return true;

            }
            return false;

            //return loadFragment(fragment);
        }
    };

    private void updateData() {
        Log.e(TAG, "updateData: " );
        if (FirebaseAuth.getInstance().getUid().equals("BfX8tqKA2cRVvWozWMldbHdKcfj1")) {
            idStr = "BfX8tqKA2cRVvWozWMldbHdKcfj1";
            // nameStr = "Masterji";
            nameStr = "deeksha gupta";
            imagesStr = "https://lh6.googleusercontent.com/-gfHhONjkkq8/AAAAAAAAAAI/AAAAAAAAAB0/WrX4Y4oeQ80/s96-c/photo.jpg";
            getActivityDAta(idStr, nameStr, imagesStr);
        } else if (FirebaseAuth.getInstance().getUid().equals("MWklcsqWViN1m2vIwQcyqcOWo893")) {
            nameStr = "diksha gupta";
            // nameStr = "Masterji";

            imagesStr = "https://lh5.googleusercontent.com/-YZHqM0b2Po8/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3reQxSpodnBzUp3nxmzNpS_ZtLk8dg/s96-c/photo.jpg";

            idStr = "MWklcsqWViN1m2vIwQcyqcOWo893";
            getActivityDAta(idStr, nameStr, imagesStr);
        } else if (FirebaseAuth.getInstance().getUid().equals("WIQFi2OFViWMeFE5pI4RKEaTrM82")) {
            nameStr = "shikha sharma";
            // nameStr = "Masterji";

            imagesStr = "https://lh3.googleusercontent.com/-r8oLxAgnzik/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3rcdd_1KyH6eJtnZitopSNyJ0lpuWg/s96-c/photo.jpg";
            idStr = "WIQFi2OFViWMeFE5pI4RKEaTrM82";
            getActivityDAta(idStr, nameStr, imagesStr);
        } else if (FirebaseAuth.getInstance().getUid().equals("N5KmELyGB8QrlWdgjMOUiofK3m13")) {
            nameStr = "utkarsh kumar";
            //nameStr = "Masterji";

            imagesStr = "https://lh5.googleusercontent.com/-wLbQRKzgGXY/AAAAAAAAAAI/AAAAAAAAqrI/PPHMMaQUN_o/s96-c/photo.jpg";
            idStr = "N5KmELyGB8QrlWdgjMOUiofK3m13";
            getActivityDAta(idStr, nameStr, imagesStr);
        } else if (FirebaseAuth.getInstance().getUid().equals("TwNBTwPMeaQgUf2LtaxX6B1Uw7n1")) {
            //nameStr = "Masterji";

            nameStr = "nirupama Lowanshi";
            idStr = "TwNBTwPMeaQgUf2LtaxX6B1Uw7n1";
            imagesStr = "https://lh5.googleusercontent.com/-aSi5PSDcMfY/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3rchliQkzgHC21qyIYqkK43JcJ5qQg/s96-c/photo.jpg";
            getActivityDAta(idStr, nameStr, imagesStr);
        } else {
            nameStr = "diksha gupta";
            // nameStr = "Masterji";
            imagesStr = "https://lh5.googleusercontent.com/-YZHqM0b2Po8/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3reQxSpodnBzUp3nxmzNpS_ZtLk8dg/s96-c/photo.jpg";
            idStr = "MWklcsqWViN1m2vIwQcyqcOWo893";
          /*idStr = "RMz8wKoMqBRwMHfqmT8Xqaa6Q5l2";
            //nameStr = "deeps malvi";
            nameStr = "Masterji";

            imagesStr = "https://lh6.googleusercontent.com/-WNcBrHSRLeo/AAAAAAAAAAI/AAAAAAAAAAA/ACHi3relhSfVzGoMvCg7pe3lmyHx-UtyVg/s96-c/photo.jpg";
           */
            getActivityDAta(idStr, nameStr, imagesStr);
        }
    }

    private void getActivityDAta(String idStr, String nameStr, String imagesStr) {
        Log.e(TAG, "getActivityDAta: nameStr----" + nameStr);
        Log.e(TAG, "getActivityDAta:idStr----------- " + idStr);
        Intent intent = new Intent(MainActivity.this, SendActivity1.class);
        intent.putExtra("MainActivity", "MainActivity");
        intent.putExtra("list", nameStr);
        intent.putExtra("imagesstrings", imagesStr);
        intent.putExtra("id", idStr);
        startActivity(intent);

    }

   /* @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
           *//* case R.id.navigation_home:

                break;

            case R.id.navigation_dashboard:
                fragment = new DashboardFragment();
                break;

            case R.id.navigation_notifications:
                fragment = new NotificationsFragment();
                break;

            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;*//*
            case R.id.action_profile:
              *//*  Intent intent = new Intent(HomeActivity.this, NavActivity.class);
                startActivity(intent);*//*
                fragment = new HomeFragment();
                // TODO
                break;
            case R.id.action_bookings:
                fragment = new OrderFragment();

                // TODO
                break;
            case R.id.action_track:
                fragment = new TrackFragment();

                break;
            case R.id.action_billing:
                fragment = new BillingFragment();

                break;
            case R.id.action_chat:
                fragment = new ChattingFragment();

                break;

        }

        return loadFragment(fragment);
    }
*/

    private void getUser() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("MJ_Users").document(Constants.Uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                if (user != null) name = user.getFullname();
                Constants.NAME = user.getFullname();
                //  Constants.NAME= user.getFullname();
             /*   Log.e(TAG, "onSuccess:getUid " + user.getUid());
                Log.e(TAG, "onSuccess:getToken " + user.getToken());
                Log.e(TAG, "onSuccess:Date " + user.getDate());*/

                //  tvUserName.setText(name);

                // Log.e(TAG, "onSuccess:getUid " + user.getUid());
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
                            Glide.with(MainActivity.this)
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


    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            currentVersion = pi.versionCode;
            currentVersionn = String.valueOf(currentVersion);

            return pi.versionCode;

        } catch (PackageManager.NameNotFoundException ex) {
        }
        return 0;
    }


}
