package online.masterji.honchiSolution.activity.Recycler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.CartActivity;
import online.masterji.honchiSolution.activity.ProductActivity;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.custom.drawable.CountDrawable;
import online.masterji.honchiSolution.domain.Product1;
import online.masterji.honchiSolution.util.RecyclerTouchListener;

import static online.masterji.honchiSolution.util.Utility.splitCamelCase;

public class SubSubActivity extends AppCompatActivity {
    private static final String TAG = "SubSubActivity";
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    List<Product1> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    String Id, category = "Female Category", product, productId;
    ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> contactList;
    Product1 category2 = new Product1();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_sub);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //LeakCanary.install(getApplication());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Id = bundle.getString("Id", "");
            Log.e(TAG, "onCreate: Id===========" + Id);
            category = bundle.getString("category", "");
            product = bundle.getString("product", "");
            productId = bundle.getString("productId", "");
            Log.e(TAG, "onCreate: category" + category);
            Log.e(TAG, "onCreate: product" + product);
            Log.e(TAG, "onCreate: productId" + productId);
            getSupportActionBar().setTitle(splitCamelCase(product));

        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        // getProductList();
        if (category.equals("Female Category")) {
            String urlJsonArryM = "https://us-central1-project-masterji.cloudfunctions.net/api/femaleCategoryData?id=" + Id;
            Log.e(TAG, "onCreate: ======" + urlJsonArryM);
            getList(urlJsonArryM);
        } else {
            String urlJsonArryM = "https://us-central1-project-masterji.cloudfunctions.net/api/maleCategoryData?id=" + Id;
            Log.e(TAG, "onCreate: ======" + urlJsonArryM);

            getList(urlJsonArryM);
        }

        // List<Product> gaggeredList = getListItemData();

       /* SolventRecyclerViewAdapter rcAdapter = new SolventRecyclerViewAdapter(SubSubActivity.this, gaggeredList);
        recyclerView.setAdapter(rcAdapter);*/
    }


    private void getList(String urlJsonArryM) {

        contactList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(SubSubActivity.this);

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
       /* contactList = new ArrayList<>();

        JsonArrayRequest req = new JsonArrayRequest(urlJsonArryM,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Log.d(TAG, response.toString());
                        Product category1 = new Product();
                        //


                        dataList.clear();
                        // Parsing json array response
                        // loop through each json object
                        String jsonResponse = "";
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject person = (JSONObject) response
                                        .get(i);

                                String id = person.getString("id");
                                String price = person.getString("price");
                                String title = person.getString("title");
                                String photo1 = person.getString("photo1");
                                String description = person.getString("description");
                                String photo2 = person.getString("photo2");

                                category1.setPhoto1(person.getString("photo1"));
                                category1.setPhoto2(person.getString("photo2"));
                                category1.setTitle(person.getString("title"));
                                category1.setPrice(person.getString("price"));
                                category1.setDescription(person.getString("description"));
                                category1.setId(person.getString("id"));

                                final HashMap<String, String> contact = new HashMap<>();

                                // adding each child node to HashMap key => value
                                contact.put("id", id);
                                contact.put("description", description);
                                contact.put("price", price);
                                contact.put("photo1", photo1);
                                contact.put("photo2", photo2);
                                contact.put("title", title);


                                // adding contact to contact list
                                contactList.add(contact);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        dataList.add(category1);
                        if (dataList.isEmpty()) {
                            recyclerView.setVisibility(View.GONE);
                            //linearDummy.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            //linearDummy.setVisibility(View.GONE);
                        }
                        initGridAdapter();

                        *//* txtResponse.setText(jsonResponse)*//*
                        ;
                        // hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                //hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);*/
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
    }

    private void getProductList() {
      /*  FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference collectionReferenceRef;
        collectionReferenceRef = db.collection(category + "/" + product + "/" + product);
        Log.e(TAG, "getProductList: category + \"/\" + product + \"/\" + product" + category + "/" + product + "/" + product);

        collectionReferenceRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("TAG", document.getId() + " => " + document.getData());
                                Product pro = document.toObject(Product.class);
                                Log.e(TAG, "onComplete: " + pro.getTitle());
                                //dataList.add((pro));
                            }
                            //dataList = task.getResult().toObjects(Product.class);
                            Log.e(TAG, "onComplete: ====" + dataList.size());
                            // initGridAdapter();
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });*/
    }

    private void initGridAdapter() {
        Log.e(TAG, "initGridAdapter: ");
        SolventRecyclerViewAdapter rcAdapter = new SolventRecyclerViewAdapter(SubSubActivity.this, dataList);
        recyclerView.setAdapter(rcAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


                Log.e(TAG, "onItemClick:getProductid==== " + dataList.get(position).getId());
                Log.e(TAG, "onItemClick:getProductid==== " + dataList.get(position).getProductId());
                Log.e(TAG, "===========:getTitle==== " + dataList.get(position).getTitle());

                Intent intent = new Intent(SubSubActivity.this, ProductActivity.class);
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
        }));/* Log.e(TAG, "onItemClick:getProductid==== " + ProductListActivity.this.dataList.get(position).getId());
        Log.e(TAG, "onItemClick:getProductid==== " + ProductListActivity.this.dataList.get(position).getProductId());
        Log.e(TAG, "===========:getTitle==== " + ProductListActivity.this.dataList.get(position).getTitle());
        Intent intent = new Intent(ProductListActivity.this, ProductActivity.class);
        intent.putExtra("product", ProductListActivity.this.dataList.get(position));
        intent.putExtra("category", category);
        intent.putExtra("productName", product);
        startActivity(intent);*/

    }
}
