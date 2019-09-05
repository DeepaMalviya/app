package online.masterji.honchiSolution.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.adapter.OfferAdapter;
import online.masterji.honchiSolution.domain.Data;
import online.masterji.honchiSolution.domain.Images;

public class OffersActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "OffersActivity";
    ImageView imageOffers, imageOffers1, imageOffers2;
    RecyclerView rvOffers;
    OfferAdapter mAdapter;
    List<Data> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
/*
rvOffers = findViewById(R.id.rvOffers);

        mAdapter = new OfferAdapter(OffersActivity.this, images);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvOffers.setLayoutManager(mLayoutManager);
        rvOffers.setItemAnimator(new DefaultItemAnimator());
        rvOffers.setAdapter(mAdapter);

*/

        getOffer();
/*

        imageOffers = findViewById(R.id.imageOffersss);
        imageOffers1 = findViewById(R.id.imageOffersss1);
        imageOffers2 = findViewById(R.id.imageOffersss2);
*/

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
        //mAdapter.notifyDataSetChanged();

      /*  Glide.with(OffersActivity.this)
                .load(images.getPhoto())
                .centerInside()
                .placeholder(R.drawable.ic_image_placeholder)
                .into(imageOffers);
        Glide.with(OffersActivity.this)
                .load(images.getPhoto1())
                .centerInside()
                .placeholder(R.drawable.ic_image_placeholder)
                .into(imageOffers1);
        Glide.with(OffersActivity.this)
                .load(images.getPhoto2())
                .centerInside()
                .placeholder(R.drawable.ic_image_placeholder)
                .into(imageOffers2);

*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}
