package online.masterji.honchiSolution.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.BookTailorActivity;
import online.masterji.honchiSolution.activity.CatelogActivity;
import online.masterji.honchiSolution.activity.Recycler.TabDetailsActivity;
import online.masterji.honchiSolution.activity.SearchActivity;
import online.masterji.honchiSolution.activity.WelcomeActivity;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.Images;
import online.masterji.honchiSolution.domain.Slider;
import online.masterji.honchiSolution.domain.User;
import online.masterji.honchiSolution.util.PrefManager;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragment extends Fragment implements View.OnClickListener, BaseSliderView.OnSliderClickListener {
    private static final String TAG = "HomeFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Context context;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView tvUserName;
    CircleImageView civHeader;
    Intent intent;
    ImageView imageOffers1, imageOffers2;
    String name;
    Slider slider;
    List<Slider> sliders;
    Fragment fragment = null;
    private SliderLayout sliderTop, mDemoSlider;

    ImageView imageBack, imageSlider;
    private BottomNavigationView bottomNavigationView;
    EditText ETsearch;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        PrefManager prefManager = new PrefManager(getApplicationContext());

        if (prefManager.isFirstTimeLaunch()) {
            prefManager.setFirstTimeLaunch(false);
            context.startActivity(new Intent(context, WelcomeActivity.class));

        }
        Log.e(TAG, "onCreate: time========================" + Timestamp.now().toDate().toString());


        getOffer();
        getTopBannerOffer();


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Constants.userMobile = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().replace("+91", "").replace("-", "").trim();
            Constants.Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.d(TAG, "Constants.userMobile=" + Constants.userMobile);
            getUser();
            // getCart();

        }

    }

    private void getTopBannerOffer() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference collectionReference = db.collection("Home Top Slider");
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            List<Slider> sliders = task.getResult().toObjects(Slider.class);
                            if (sliders.isEmpty()) {
                                mDemoSlider.setVisibility(View.GONE);
                                imageSlider.setVisibility(View.VISIBLE);
                            } else {
                                mDemoSlider.setVisibility(View.VISIBLE);
                                imageSlider.setVisibility(View.VISIBLE);
                            }
                            try {
                                setSliders(sliders.get(0));
                                //setOffers(sliders.get(0));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });
    }

    private void setSliders(Slider slider) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        // requestOptions.placeholder(R.drawable.bannerhome);
        //requestOptions.error(R.drawable.bannerhome);

        for (String photo : slider.getSliders()) {
            TextSliderView sliderView = new TextSliderView(getContext());
            // if you want show image only / without description text use DefaultSliderView instead
            // initialize SliderLayout
            sliderView
                    .image(photo)
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
        mDemoSlider.setDuration(3000);

    }

    private void initView(View view) {
        ETsearch = view.findViewById(R.id.ETsearch);
        ETsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(context, SearchActivity.class);
                startActivity(intent);

            }
        });
      /*  bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

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
                });

*/


        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        imageBack = view.findViewById(R.id.imageBack);
        imageSlider = view.findViewById(R.id.imageSlider);
        mDemoSlider = view.findViewById(R.id.slider);
        sliderTop = view.findViewById(R.id.sliderTop);
        view.findViewById(R.id.layoutMale).setOnClickListener(this);
        view.findViewById(R.id.layoutFemle).setOnClickListener(this);
        //findViewById(R.id.layoutKids).setOnClickListener(this);
        view.findViewById(R.id.layoutCatalog).setOnClickListener(this);
        view.findViewById(R.id.layoutEnquiry).setOnClickListener(this);
        //imageOffers = view.findViewById(R.id.imageOfferHome);
      /*  imageOffers1 = findViewById(R.id.imageOfferss1);
        imageOffers2 = findViewById(R.id.imageOfferss2);
      */
        // imageOffers.setOnClickListener(this);
      /*  imageOffers1.setOnClickListener(this);
        imageOffers2.setOnClickListener(this);*/

    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    private void getOffer() {
        Log.e(TAG, "getOffer: ");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference collectionReference = db.collection("Offer");
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            Log.d(TAG, task.getResult() + "Offer => " + task.getResult());
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "Offer => " + document.getData());
                            }
                            List<Slider> sliders = task.getResult().toObjects(Slider.class);
                           // List<Images> sliders = task.getResult().toObjects(Images.class);
                            try {
                                setOffers( sliders.get(0));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });


               /*
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, task.getResult() + "Offer => " + task.getResult());
                          *//*  for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "Offer => " + document.getData());
                            }*//*
                            List<Slider> sliders = Collections.singletonList(task.getResult().toObject(Slider.class));
                            //List<Images> sliders = task.getResult().toObjects(Images.class);
                            try {
                                setOffers(sliders.get(0));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
*/

       /* new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "Offer => " + document.getData());
                            }
                            List<Slider> sliders = task.getResult().toObjects(Slider.class);
                            //List<Images> sliders = task.getResult().toObjects(Images.class);
                            try {
                                setOffers(sliders.get(0));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });*/
    }

    private void setOffers(Slider images) {


        Glide.with(getContext())
                .load(images.getSliders())
                .centerInside()
                .placeholder(R.drawable.bannerhome)
                .into(imageBack);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        //requestOptions.placeholder(R.drawable.bannerhome);
        //requestOptions.error(R.drawable.bannerhome);

        for (String photo : images.getSliders()) {
            TextSliderView sliderView = new TextSliderView(getContext());
            // if you want show image only / without description text use DefaultSliderView instead
            // initialize SliderLayout
            sliderView
                    .image(photo)
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            startActivity(new Intent(getContext(), BookTailorActivity.class));
                        }
                    });
            sliderTop.addSlider(sliderView);
        }

        // set Slider Transition Animation
        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderTop.setPresetTransformer(SliderLayout.Transformer.Accordion);

        sliderTop.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderTop.setCustomAnimation(new DescriptionAnimation());
        sliderTop.setDuration(3000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        view.setBackgroundColor(Color.WHITE);
        initView(view);
        return view;

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.layoutMale:
                intent = new Intent(context, TabDetailsActivity.class);
                intent.putExtra("category", "Male Category");
                startActivity(intent);

              /*  intent = new Intent(this, CategoryActivity.class);
                intent.putExtra("category", "Male");
                startActivity(intent);*/
                break;
            case R.id.layoutFemle:
                intent = new Intent(context, TabDetailsActivity.class);
                intent.putExtra("category", "Female Category");
                startActivity(intent);
                break;
         /*   case R.id.layoutKids:
                intent = new Intent(this, CategoryActivity.class);
                intent.putExtra("category", "Kids");
                startActivity(intent);
                break;*/
            case R.id.layoutEnquiry:

                intent = new Intent(context, BookTailorActivity.class);
                startActivity(intent);

                //callToHelpCenter();
                break;
            /*  case R.id.imageOfferss:

             *//*  intent = new Intent(context, OffersActivity.class);
                startActivity(intent);
*//*
                //callToHelpCenter();
                break;*/
           /* case R.id.imageOfferss1:

                intent = new Intent(this, OffersActivity.class);
                startActivity(intent);

                //callToHelpCenter();
                break;
            case R.id.imageOfferss2:
                intent = new Intent(this, OffersActivity.class);
                startActivity(intent);

                //callToHelpCenter();
                break;*/
            case R.id.layoutCatalog:
                intent = new Intent(context, CatelogActivity.class);
                intent.putExtra("category", "Raymond");
                startActivity(intent);


                //Toast.makeText(this, "Coming Soon", Toast.LENGTH_LONG).show();
                break;
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
             /*   Log.e(TAG, "onSuccess:getUid " + user.getUid());
                Log.e(TAG, "onSuccess:getToken " + user.getToken());
                Log.e(TAG, "onSuccess:Date " + user.getDate());*/

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
                            Glide.with(context)
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


    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(context, "slider", Toast.LENGTH_SHORT).show();
    }
}
