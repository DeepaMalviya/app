package online.masterji.honchiSolution.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import online.masterji.honchiSolution.R;

public class CategoryDetailsActivity extends AppCompatActivity {
    private static final String TAG = "CategoryDetailsActivity";
    String Raymond, Photo, Title;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    TextView tvDesc, tvDesc1, tvDesc2;
    ImageView imageView;
    Button AddToCart, BuyNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        initView();


    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Raymond = bundle.getString("Raymond", "");
            Photo = bundle.getString("Photo", "");
            Title = bundle.getString("Title", "");
            Log.e(TAG, "initView:========== " + Raymond);
            Log.e(TAG, "initView: ========" + Photo);
            Log.e(TAG, "initView: =========" + Title);

        }
        Log.e(TAG, "initView: " + Raymond);
        tvDesc = findViewById(R.id.tvDesc);
        tvDesc1 = findViewById(R.id.tvDesc1);
        tvDesc2 = findViewById(R.id.tvDesc2);
        imageView = findViewById(R.id.imageView);
        AddToCart = findViewById(R.id.AddToCart);
        BuyNow = findViewById(R.id.BuyNow);
        tvDesc.setText("" + Title);
        tvDesc1.setText("Rs 2000");
        tvDesc2.setText("Order custom made shirts online from the finest shirt fabrics for party, office or any occasion with Raymond Custom Tailoring. Book an appointment today & get your shirt customized!");

        Glide.with(CategoryDetailsActivity.this)
                .load(Photo)
                .centerInside()
                .into(imageView);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(Title);


        progressDialog = new ProgressDialog(CategoryDetailsActivity.this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
