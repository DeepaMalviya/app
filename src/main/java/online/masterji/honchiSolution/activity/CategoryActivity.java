package online.masterji.honchiSolution.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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
import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.adapter.Category1Adapter;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.custom.drawable.CountDrawable;
import online.masterji.honchiSolution.domain.Category;
import online.masterji.honchiSolution.domain.Data;
import online.masterji.honchiSolution.domain.Product;
import online.masterji.honchiSolution.util.WaitDialog;


public class CategoryActivity extends AppCompatActivity {
    private static final String TAG = "CategoryActivity";

    private String urlJsonArryMen = "https://us-central1-project-masterji.cloudfunctions.net/api/maleCategory";
    private String urlJsonArryWomen = "https://us-central1-project-masterji.cloudfunctions.net/api/femaleCategory";
    private String jsonResponse;

    GridView gridview;
    String id, category = "Male Category";
    List<Product> dataList;
    WaitDialog waitDialog;
    List<Category> categoryList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            category = bundle.getString("category", "");
            id = bundle.getString("id", "");
            Log.e(TAG, "onCreate: ======" + id);
            if (category.equals("Male Category")) {
                getSupportActionBar().setTitle("Male Category");

            } else if (category.equals("Female Category")) {
                getSupportActionBar().setTitle("Female Category");
            } else if (category.equals("Kids")) {
                getSupportActionBar().setTitle("Kids Category");
            }
        }

        waitDialog = new WaitDialog(this);


        initView();
        getCategory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    private void initView() {
        gridview = (GridView) findViewById(R.id.gridview);

    }

    private void initGridAdapter() {
        gridview.setAdapter(new Category1Adapter(this, categoryList));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (category.equals("Male")) {
                    Intent intent1 = new Intent(CategoryActivity.this, ProductListActivity.class);
                    intent1.putExtra("category", "Male Category");
                    intent1.putExtra("product", dataList.get(position).getTitle());
                    startActivity(intent1);

                } else if (category.equals("Female")) {
                    Intent intent1 = new Intent(CategoryActivity.this, ProductListActivity.class);
                    intent1.putExtra("category", "Female Category");
                    intent1.putExtra("product", dataList.get(position).getTitle());
                    startActivity(intent1);

                } else if (category.equals("Kids")) {
                    Intent intent1 = new Intent(CategoryActivity.this, ProductListActivity.class);
                    intent1.putExtra("category", "Kids Category");
                    intent1.putExtra("product", dataList.get(position).getTitle());
                    startActivity(intent1);

                }
            }
        });
    }


    /* private void getCategory() {
         waitDialog.show();
         FirebaseFirestore db = FirebaseFirestore.getInstance();

         CollectionReference collectionReference;
         if (category.equals("Male")) {
             collectionReference = db.collection("Male Category");
         } else if (category.equals("Female")) {
             collectionReference = db.collection("Female Category");
         } else {
             collectionReference = db.collection("Kids Category");
         }
         collectionReference.get()
                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                         if (task.isSuccessful()) {
                             for (QueryDocumentSnapshot document : task.getResult()) {
                                 Log.d(TAG, document.getId() + " => " + document.getData());
                             }
                             dataList = task.getResult().toObjects(Product.class);

                             for (Product product : dataList) {
                                 Log.d(TAG, "product id  => " + product.getId());
                             }


                             initGridAdapter();

                         } else {
                             Log.d(TAG, "Error getting documents: ", task.getException());
                         }
                         waitDialog.dismiss();
                     }
                 });
     }*/
    private void getCategory() {
        if (category.equals("Male Category")) {
            getMaleCategoryData();

        } else if (category.equals("Female Category")) {
            getFemaleCategoryData();
        }
    }

    private void getMaleCategoryData() {
       /* pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        showpDialog();
*/

        //showpDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(CategoryActivity.this);

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


                        Log.e(TAG, "onResponse: " + categoryList.size());
                        Log.e(TAG, "onResponse: " + jsonResponse);
                        //txtResponse.setText(jsonResponse);

                       /* if (categoryList.isEmpty()) {
                            recyclerView.setVisibility(View.GONE);
                            linearDummy.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            linearDummy.setVisibility(View.GONE);
                        }*/
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

    private void getFemaleCategoryData() {
       /* pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        showpDialog();


        showpDialog();*/
        RequestQueue requestQueue = Volley.newRequestQueue(CategoryActivity.this);

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

                                String id = person.getString("id");
                                Log.e(TAG, "onResponse: id======" + id);
                                JSONObject data = person
                                        .getJSONObject("data");
                                Log.e(TAG, "onResponse: data======" + data);
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


                        //txtResponse.setText(jsonResponse);

                       /* if (categoryList.isEmpty()) {
                            recyclerView.setVisibility(View.GONE);
                            linearDummy.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            linearDummy.setVisibility(View.GONE);
                        }*/
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
