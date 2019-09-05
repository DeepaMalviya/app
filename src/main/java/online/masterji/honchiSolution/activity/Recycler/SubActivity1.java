package online.masterji.honchiSolution.activity.Recycler;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.CartActivity;
import online.masterji.honchiSolution.adapter.SubAdapter;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.custom.drawable.CountDrawable;
import online.masterji.honchiSolution.domain.Product;
import online.masterji.honchiSolution.util.RecyclerTouchListener;
import online.masterji.honchiSolution.util.WaitDialog;

public class SubActivity1 extends AppCompatActivity {
    private static final String TAG = "SubActivity";
    //private ArrayList<Item> items;
    private RecyclerView recyclerView;
    SubAdapter mAdapter;
    String category;
    List<Product> dataList;
    WaitDialog waitDialog;
    public String productt;
    public static int size;
    List<Product> productList = new ArrayList<>();
    List<String> list;
    FirebaseFirestore db;
    CollectionReference collectionReference;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        initView();
        getCategory();

    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        waitDialog = new WaitDialog(this);
        db = FirebaseFirestore.getInstance();
        emptyView = (TextView) findViewById(R.id.empty_view);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            category = bundle.getString("category", "");
            if (category.equals("Male Category")) {
                getSupportActionBar().setTitle("Mens Category");

            } else if (category.equals("Female Category")) {
                getSupportActionBar().setTitle("Womens Category");
            } else if (category.equals("Kids")) {
                getSupportActionBar().setTitle("Kids Category");
            }
        }
        //  mAdapter = new SubAdapter(SubActivity.this, dataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
    }


    private void getProductList(String productt) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        CollectionReference collectionReferenceRef;
        collectionReferenceRef = db.collection(category + "/" + productt + "/" + productt);
        Log.d(TAG, "getProductList: category + \"/\" + product + \"/\" + product======\n" + category + "/" + productt + "/" + productt);

        collectionReferenceRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e(TAG, "onComplete: " + document.get("description"));
                                list.add(document.getId());
                                Log.e(TAG, "onComplete: list------------" + list.size());
                                Log.e("TAG", document.getId() + " => " + document.getData());

                                Product pro = document.toObject(Product.class);
                                productList.add(pro);
                            }
                            Log.e(TAG, "onComplete: list----======--------" + list.size());


                            // Log.e(TAG, "productList.size(): ==@@@@@@@@@==" + productList.size());
                            size = productList.size();
                            // initGridAdapter(dataList);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
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

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
        // mShimmerViewContainer.startShimmerAnimation();
    }


    private void getCategory() {
        waitDialog.show();
        Log.e(TAG, "getCategory: ");
        if (category.equals("Male Category")) {
            collectionReference = db.collection("Male Category");
        } else if (category.equals("Female Category")) {
            collectionReference = db.collection("Female Category");
        } else {
            collectionReference = db.collection("Kids Category");
        }
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            waitDialog.dismiss();
                            // Toast.makeText(SubActivity.this, "getCategory", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("TAG", "===" + document.getId() + "===" + document.getData());

                            }
                            dataList = task.getResult().toObjects(Product.class);
                            Log.e(TAG, "onComplete: dataList.size()" + dataList.size());
                            /*if (dataList.isEmpty()) {
                                recyclerView.setVisibility(View.GONE);
                                emptyView.setVisibility(View.VISIBLE);
                            }
                            else {
                                recyclerView.setVisibility(View.VISIBLE);
                                emptyView.setVisibility(View.GONE);
                            }*/
                            for (Product product : dataList) {
                                Log.e("TAG", "product id  => " + product.getId());
                                Log.e("TAG", "product id  => " + product.getTitle());
                                productt = product.getTitle();
                                Log.d(TAG, "onComplete:productt==" + productt + "===");
                            }


                            getProductList(productt);

                            initGridAdapter(dataList);


                        } else {
                            waitDialog.dismiss();
                            Log.e("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onPause() {
        // mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

    private void initGridAdapter(final List<Product> dataList) {
        Log.e(TAG, "initGridAdapter: ");
       // mAdapter = new SubAdapter(SubActivity1.this, dataList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        //  mShimmerViewContainer.stopShimmerAnimation();
        //   mShimmerViewContainer.setVisibility(View.GONE);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (category.equals("Male Category")) {
                    Intent intent1 = new Intent(SubActivity1.this, SubSubActivity.class);
                    intent1.putExtra("category", "Male Category");
                    intent1.putExtra("product", dataList.get(position).getTitle());
                    productt = dataList.get(position).getTitle();
                    intent1.putExtra("productId", dataList.get(position).getId());
                    startActivity(intent1);

                } else if (category.equals("Female Category")) {
                    Intent intent1 = new Intent(SubActivity1.this, SubSubActivity.class);
                    intent1.putExtra("category", "Female Category");
                    intent1.putExtra("product", dataList.get(position).getTitle());
                    productt = dataList.get(position).getTitle();

                    intent1.putExtra("productId", dataList.get(position).getId());
                    startActivity(intent1);

                } else if (category.equals("Kids")) {
                    Intent intent1 = new Intent(SubActivity1.this, SubSubActivity.class);
                    intent1.putExtra("category", "Kids Category");
                    intent1.putExtra("product", dataList.get(position).getTitle());
                    intent1.putExtra("productId", dataList.get(position).getId());
                    startActivity(intent1);

                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
       /* recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (category.equals("Male")) {
                    Intent intent1 = new Intent(SubActivity.this, ProductListActivity.class);
                    intent1.putExtra("category", "Male Category");
                    intent1.putExtra("product", dataList.get(position).getTitle());
                    startActivity(intent1);

                } else if (category.equals("Female")) {
                    Intent intent1 = new Intent(SubActivity.this, ProductListActivity.class);
                    intent1.putExtra("category", "Female Category");
                    intent1.putExtra("product", dataList.get(position).getTitle());
                    startActivity(intent1);

                } else if (category.equals("Kids")) {
                    Intent intent1 = new Intent(SubActivity.this, ProductListActivity.class);
                    intent1.putExtra("category", "Kids Category");
                    intent1.putExtra("product", dataList.get(position).getTitle());
                    startActivity(intent1);

                }
            }
        });*/
    }

}
