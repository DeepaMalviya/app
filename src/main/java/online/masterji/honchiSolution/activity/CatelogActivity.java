package online.masterji.honchiSolution.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.adapter.Category11Adapter;
import online.masterji.honchiSolution.domain.Images;

public class CatelogActivity extends AppCompatActivity {
    private static final String TAG = "CatelogActivity";
    GridView gridviewCatelog;
    ProgressDialog progressDialog;
    CollectionReference collectionReferenceRef;
    String category;
    FirebaseFirestore db;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catelog);

        initView();
        getCategory();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initView() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            category = bundle.getString("category", "");
        }
       // Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(category);
        gridviewCatelog = findViewById(R.id.gridviewCatelog);
        progressDialog = new ProgressDialog(CatelogActivity.this);
    }

    private void getCategory() {
        progressDialog.show();
        db = FirebaseFirestore.getInstance();

        if (category.equals("Raymond")) {
            collectionReferenceRef = db.collection("Raymond");
        } else if (category.equals("Raymond")) {
            collectionReferenceRef = db.collection("Raymond");
        } else {
            collectionReferenceRef = db.collection("Raymond");
        }
        collectionReferenceRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e(TAG, document.getId() + " => " + document.getData());
                            }
                            List<Images> dataList = task.getResult().toObjects(Images.class);
                            initGridAdapter(dataList);
                        } else {
                            progressDialog.dismiss();
                        }
                    }
                });

    }

    private void initGridAdapter(final List<Images> dataList) {
        gridviewCatelog.setAdapter(new Category11Adapter(this, dataList));

        gridviewCatelog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                String item = ((TextView) v.findViewById(R.id.tvTitle)).getText().toString();
                Log.e(TAG, "onItemClick:===item===" + "---" + item + "---");
                //  Toast.makeText(CatelogActivity.this, " " + item, Toast.LENGTH_SHORT).show();
                if (item.equals("  Raymond Customized Jacket ")) {
                    intent = new Intent(CatelogActivity.this, CategoryDetailsActivity.class);
                    intent.putExtra("Raymond", "Raymond Customized Jackets");
                    intent.putExtra("Photo", dataList.get(position).getPhoto());
                    intent.putExtra("Title", dataList.get(position).getTitle());
                    startActivity(intent);
                } else if (item.equals("Raymond Customized Shirts")) {

                    intent = new Intent(CatelogActivity.this, CategoryDetailsActivity.class);
                    intent.putExtra("Raymond", "Raymond Customized Shirts");
                    intent.putExtra("Photo", dataList.get(position).getPhoto());
                    intent.putExtra("Title", dataList.get(position).getTitle());
                    startActivity(intent);
                } else if (item.equals("Raymond Customized Trousers")) {

                    intent = new Intent(CatelogActivity.this, CategoryDetailsActivity.class);
                    intent.putExtra("Raymond", "Raymond Customized Trousers");
                    intent.putExtra("Photo", dataList.get(position).getPhoto());
                    intent.putExtra("Title", dataList.get(position).getTitle());
                    startActivity(intent);

                } else {
                }

            }
        });

    }

}
