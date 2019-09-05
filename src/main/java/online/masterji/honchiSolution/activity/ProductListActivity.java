package online.masterji.honchiSolution.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.adapter.Category1Adapter;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.custom.drawable.CountDrawable;
import online.masterji.honchiSolution.domain.Product;
import online.masterji.honchiSolution.util.WaitDialog;

public class ProductListActivity extends AppCompatActivity {
    private static final String TAG = "ProductListActivity";

    GridView gridview;
    String category, product;
    WaitDialog waitDialog;
    List<Product> dataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            category = bundle.getString("category", "");
            product = bundle.getString("product", "");
            Log.e(TAG, "onCreate: category" + category);
            Log.e(TAG, "onCreate: product" + product);
            getSupportActionBar().setTitle(product);

        }
        waitDialog = new WaitDialog(this);
        initView();
        getProductList();


    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    private void initView() {
        gridview = (GridView) findViewById(R.id.gridview);

    }

    private void initGridAdapter(List<Product> dataList) {
        gridview.setAdapter(new Category1Adapter(this, this.dataList));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                Log.e(TAG, "onItemClick:getProductid==== " + ProductListActivity.this.dataList.get(position).getId());
                Log.e(TAG, "onItemClick:getProductid==== " + ProductListActivity.this.dataList.get(position).getProductId());
                Log.e(TAG, "===========:getTitle==== " + ProductListActivity.this.dataList.get(position).getTitle());
                Intent intent = new Intent(ProductListActivity.this, ProductActivity.class);
                intent.putExtra("product", ProductListActivity.this.dataList.get(position));
                intent.putExtra("category", category);
                intent.putExtra("productName", product);
                startActivity(intent);

            }
        });
    }


    private void getProductList() {
        waitDialog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference collectionReferenceRef;
        collectionReferenceRef = db.collection(category + "/" + product + "/" + product);

        Log.e(TAG, "getProductList:========= "+category + "/" + product + "/" + product);
        collectionReferenceRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            dataList = task.getResult().toObjects(Product.class);

                            initGridAdapter(dataList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        waitDialog.dismiss();
                    }
                });
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

}
