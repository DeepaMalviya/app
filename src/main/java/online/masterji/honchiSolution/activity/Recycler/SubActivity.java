package online.masterji.honchiSolution.activity.Recycler;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.CartActivity;
import online.masterji.honchiSolution.adapter.SubAdapter;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.custom.drawable.CountDrawable;
import online.masterji.honchiSolution.domain.Category;
import online.masterji.honchiSolution.domain.Data;
import online.masterji.honchiSolution.domain.Product;
import online.masterji.honchiSolution.util.RecyclerTouchListener;
import online.masterji.honchiSolution.util.WaitDialog;

public class SubActivity extends AppCompatActivity {
    private static final String TAG = "SubActivity";
    private RecyclerView recyclerView;
    SubAdapter mAdapter;
    String category = "Male Category";
    List<Product> dataList;
    WaitDialog waitDialog;
    public String productt;
    public static int size;
    List<Product> productList = new ArrayList<>();
    List<String> list;
    FirebaseFirestore db;
    CollectionReference collectionReference;
    private ImageView emptyView;
    LinearLayout linearDummy;
    String id;
    ;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        initView();
        getCategory();

    }

    private void initView() {


        linearDummy = findViewById(R.id.linearDummy);
        linearDummy.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.recycler_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        waitDialog = new WaitDialog(this);
        db = FirebaseFirestore.getInstance();
        emptyView = findViewById(R.id.empty_view);
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
        //  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(getApplicationContext()));
        recyclerView.smoothScrollToPosition(12);
        // recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
    }


    private void getProductList(String productt) {
        String producttt = " Kurtis";
        Log.d(TAG, "getProductList: ==============" + productt + "========");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        CollectionReference collectionReferenceRef;
        collectionReferenceRef = db.collection(category + "/" + productt + "/" + productt);
        Log.d(TAG, "getProductList: category + \"/\" + product + \"/\" + product======\n" + category + "/" + producttt + "/" + producttt);

        collectionReferenceRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                list.add(document.getId());
                                Log.e("TAG", document.getId() + " => " + document.getData());

                                Product pro = document.toObject(Product.class);
                                productList.add(pro);
                            }
                            Log.e(TAG, "onComplete: list----======--------" + list.size());
                            productList = task.getResult().toObjects(Product.class);


                            Log.e(TAG, "==@@@@@@@@@==productList.size(): ==@@@@@@@@@==" + productList.size());
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

    // json array response url
    private String urlJsonArryMen = "https://us-central1-project-masterji.cloudfunctions.net/api/maleCategory";
    private String urlJsonArryWomen = "https://us-central1-project-masterji.cloudfunctions.net/api/femaleCategory";
    private String jsonResponse;
    // Progress dialog
    private ProgressDialog pDialog;
    List<Category> categoryList = new ArrayList<>();

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void getCategory() {
        if (category.equals("Male Category")) {
            getMaleCategoryData();

        } else if (category.equals("Female Category")) {
            getFemaleCategoryData();
        }





      /* // waitDialog.show();
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
                            //waitDialog.dismiss();
                            // Toast.makeText(SubActivity.this, "getCategory", Toast.LENGTH_SHORT).show();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("TAG", "===" + document.getId() + "===" + document.getData());

                            }
                            dataList = task.getResult().toObjects(Product.class);
                            Log.e(TAG, "onComplete: dataList.size()" + dataList.size());

                            if (dataList.isEmpty()) {
                                recyclerView.setVisibility(View.GONE);
                                linearDummy.setVisibility(View.VISIBLE);
                            }
                            else {
                                recyclerView.setVisibility(View.VISIBLE);
                                linearDummy.setVisibility(View.GONE);
                            }
                            for (Product product : dataList) {
                                Log.e("TAG", "product id  => " + product.getId());
                                Log.e("TAG", "product id  => " + product.getTitle());
                                productt = product.getTitle();
                                Log.d(TAG, "onComplete:productt==" + productt + "===");
                            }


                            getProductList(productt);

                            initGridAdapter(dataList, size);


                        } else {
                            waitDialog.dismiss();
                            Log.e("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });*/


    }

    private void getFemaleCategoryData() {
       /* pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        showpDialog();


        showpDialog();*/
        RequestQueue requestQueue = Volley.newRequestQueue(SubActivity.this);

        JsonArrayRequest req = new JsonArrayRequest(urlJsonArryWomen,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        categoryList.clear();

                        jsonResponse = "";
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject person = (JSONObject) response
                                        .get(i);
                                Category category1 = new Category();
                                Data dataa = new Data();
                                List<Data> dataList = new ArrayList<>();

                                id = person.getString("id");
                                Log.e(TAG, "onResponse: id======" + id);
                                JSONObject data = person
                                        .getJSONObject("data");
                                Log.e(TAG, "======== cloth======" + data.getString("cloth"));

                                Log.e(TAG, "onResponse: data======" + data);
                                dataa.setPhoto(data.getString("photo"));
                                dataa.setPhoto1(data.getString("photo1"));
                                dataa.setPhoto2(data.getString("photo2"));
                                dataa.setPhoto3(data.getString("photo3"));
                                dataa.setTitle(data.getString("title"));
                              //  dataa.setTitle(data.getString("cloth"));
                                dataList.add(dataa);
                                category1.setId(id);
                                category1.setData(dataa);
                                categoryList.add(category1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }


                        //txtResponse.setText(jsonResponse);
                        Log.e(TAG, "onResponse: categoryList size" + categoryList.size());

                        if (categoryList.isEmpty()) {
                            recyclerView.setVisibility(View.GONE);
                            linearDummy.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            linearDummy.setVisibility(View.GONE);
                        }
                        initGridAdapter();
                        //  hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //   Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                //hidepDialog();
            }
        });

        requestQueue.add(req);


    }

    private void getMaleCategoryData() {
       /* pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        showpDialog();
*/

        //showpDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(SubActivity.this);

        JsonArrayRequest req = new JsonArrayRequest(urlJsonArryMen,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        categoryList.clear();

                        jsonResponse = "";
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject person = (JSONObject) response
                                        .get(i);
                                Category category1 = new Category();
                                Data dataa = new Data();
                                List<Data> dataList = new ArrayList<>();

                                String id = person.getString("id");
                                Log.e(TAG, "onResponse: id" + id);
                                JSONObject data = person
                                        .getJSONObject("data");
                                Log.e(TAG, "onResponse: data" + data);
                                dataa.setPhoto(data.getString("photo"));
                                dataa.setPhoto1(data.getString("photo1"));
                                dataa.setPhoto2(data.getString("photo2"));
                                dataa.setPhoto3(data.getString("photo3"));
                                dataa.setTitle(data.getString("title"));
                                dataList.add(dataa);
                                category1.setId(id);
                                category1.setData(dataa);
                                categoryList.add(category1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }


                        Log.e(TAG, "onResponse: categoryList size" + categoryList.size());
                        Log.e(TAG, "onResponse: " + jsonResponse);
                        //txtResponse.setText(jsonResponse);

                        if (categoryList.isEmpty()) {
                            recyclerView.setVisibility(View.GONE);
                            linearDummy.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            linearDummy.setVisibility(View.GONE);
                        }
                        initGridAdapter();
                        //hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                //hidepDialog();
            }
        });

        requestQueue.add(req);


    }

    private void setupRecycler() {
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

    private void initGridAdapter() {


        Log.e(TAG, "initGridAdapter: ");
        mAdapter = new SubAdapter(SubActivity.this, categoryList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
       /* mAdapter = new SubAdapter(SubActivity.this, dataList, SubActivity.size);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();*/
        //  mShimmerViewContainer.stopShimmerAnimation();
        //   mShimmerViewContainer.setVisibility(View.GONE);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.e(TAG, "onClick: " + "==Id===" + categoryList.get(position).getId());
                if (category.equals("Male Category")) {
                    Intent intent1 = new Intent(SubActivity.this, SubSubActivity.class);
                    intent1.putExtra("category", "Male Category");
                    intent1.putExtra("product", categoryList.get(position).getId());
                    productt = categoryList.get(position).getId();
                    intent1.putExtra("Id", categoryList.get(position).getId());
                    startActivity(intent1);

                } else if (category.equals("Female Category")) {
                    Intent intent1 = new Intent(SubActivity.this, SubSubActivity.class);
                    intent1.putExtra("category", "Female Category");
                    intent1.putExtra("product", categoryList.get(position).getId());
                    productt = categoryList.get(position).getId();

                    intent1.putExtra("Id", categoryList.get(position).getId());
                    startActivity(intent1);

                } else if (category.equals("Kids")) {
                    Intent intent1 = new Intent(SubActivity.this, SubSubActivity.class);
                    intent1.putExtra("category", "Kids Category");
                    intent1.putExtra("product", categoryList.get(position).getId());
                    intent1.putExtra("Id", categoryList.get(position).getId());
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
