package online.masterji.honchiSolution.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.adapter.CustomList;
import online.masterji.honchiSolution.adapter.SearchAdapter;
import online.masterji.honchiSolution.domain.Product;
import online.masterji.honchiSolution.domain.Search;
import online.masterji.honchiSolution.util.RecyclerTouchListener;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    RecyclerView recyclerView;
    EditText editTextSearch;
    CustomList adapter;
    Product product;
    private FirebaseFirestore mFirestore;
    private Query mQuery;
    private static final int LIMIT = 50;

    static final String ALGOLIA_APP_ID = "2IRDE8GWI2";
    static final String ALGOLIA_INDEX = "products";
    static final String ALGOLIA_API_KEY = "c251493aba2032356766ef5a1a2a2403";
    final List<String> list = new ArrayList<>();
    final List<String> web = new ArrayList<>();
    final List<String> imageId = new ArrayList<>();
    final ArrayList<String> history = new ArrayList<>();
    final List<Search> searchList = new ArrayList<>();
    Search search;
    Client client = new Client(MainActivity.Algolia_app_id, MainActivity.Algolia_api_key);
    Index index = client.getIndex(MainActivity.Algolia_index);
    CompletionHandler completionHandler;
    ListView output;
    ArrayList<HashMap<String, String>> contactList;
    SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        output = findViewById(R.id.listtt);
        search = new Search();

        shared = getSharedPreferences("App_settings", MODE_PRIVATE);
        //arrPackage = new ArrayList<>();
        output.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String product = ((TextView) view).getText().toString();
                Intent intent = new Intent(SearchActivity.this, ProductActivity.class);
                try {
                    intent.putExtra("title", web.get(position));
                    intent.putExtra("description", contactList.get(position).get("description"));
                    intent.putExtra("price", contactList.get(position).get("price"));
                    intent.putExtra("photo1", imageId.get(position));
                    intent.putExtra("photo2", contactList.get(position).get("photo2"));
                    intent.putExtra("id", contactList.get(position).get("id"));
                    intent.putExtra("descri", contactList.get(position).get("descriptionn"));
                    intent.putExtra("titlee", contactList.get(position).get("titlee"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
        //  getDAta();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView
                .setLayoutManager(new StaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL));// Here 2 is no. of columns to be displayed

        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    Query query = new Query(editTextSearch.getText().toString()).setHitsPerPage(20);
                    index.searchAsync(query, completionHandler);
                    return true;
                }
                return false;
            }
        });
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    web.clear();
                    imageId.clear();
                } else {
                    history.add(charSequence.toString());
                    SharedPreferences.Editor editor = shared.edit();
                    Set<String> set = new HashSet<String>();
                    set.addAll(history);
                    editor.putStringSet("DATE_LIST", set);
                    editor.apply();

                    web.clear();
                    imageId.clear();
                    Query query = new Query(charSequence.toString()).setHitsPerPage(20);
                    index.searchAsync(query, completionHandler);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String edit = editable.toString();
                if (editable.toString().equals("")) {
                    web.clear();
                    imageId.clear();
                } else {
                    recyclerView.setVisibility(View.GONE);
                    if (history.size() <= 10) {
                        Log.e(TAG, "afterTextChanged: "+history.size() );
                    } else {
                        Log.e(TAG, "afterTextChanged: "+history.size() );
                        history.add(editable.toString());
                        SharedPreferences.Editor editor = shared.edit();
                        Set<String> set = new HashSet<String>();
                        set.addAll(history);
                        editor.putStringSet("DATE_LIST", set);
                        editor.apply();

                    }
                    web.clear();
                    imageId.clear();
                    Query query = new Query(editable.toString()).setHitsPerPage(20);
                    index.searchAsync(query, completionHandler);
                }
            }
        });

        if (editTextSearch.getText().toString().trim().equals("")) {
            web.clear();
            imageId.clear();
            Log.e(TAG, "onCreate: " + history.size());

            if (history != null) {

                try {
                    Set<String> set = shared.getStringSet("DATE_LIST", null);
                    history.addAll(set);
                    Log.e(TAG, "retrive sharedPreferences" + history);
                    recyclerView.setVisibility(View.GONE);
                    SearchAdapter searchAdapter = new SearchAdapter(SearchActivity.this, history);
                   /* SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), contactList, R.layout.item_category);
                    output.setAdapter(simpleAdapter);*/
                    recyclerView.setAdapter(searchAdapter);
                    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                            try {
                                editTextSearch.setText("" + history.get(position));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                    searchAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
            }
        } else {
            recyclerView.setVisibility(View.VISIBLE);

        }
        for (int i = 0; i < history.size(); i++) {
            Log.e(TAG, "history: " + history.get(i));

        }
        completionHandler = new CompletionHandler() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void requestCompleted(JSONObject content, AlgoliaException error) {
                Log.e("requestCompleted: ", String.valueOf(content));
                getDAta(content);

                /*try {
                    JSONArray hits = content.getJSONArray("hits");
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < hits.length(); i++) {
                        JSONObject jsonObject = hits.getJSONObject(i);
                        ;
                        JSONObject jsonObject1 = jsonObject.getJSONObject("_highlightResult");
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("title");
                        JSONObject jsonObject3 = jsonObject1.getJSONObject("description");

                        String title = jsonObject2.getString("value");
                        String description = jsonObject3.getString("value");
                        list.add(title + "\n" + description);

                    }
                    // get data from the table by the ListAdapter

                    ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.itemlistrow, list);
                    // output.setAdapter(ad);

                    output.setAdapter(stringArrayAdapter);
                    output.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String product = ((TextView) view).getText().toString();
                            Intent intent = new Intent(SearchActivity.this, ProductActivity.class);
                            intent.putExtra("EXTRA_MESSAGE", product);
                            startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/

            }
        };


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getDAta(JSONObject content) {
        contactList = new ArrayList<>();

        Log.e(TAG, "getDAta: " + content);
        if (content != null) {
            try {
                //  JSONObject jsonObj = new JSONObject(content);

                // Getting JSON Array node
                JSONArray contacts = content.getJSONArray("hits");
                if (contacts.equals("") && contacts == null) {

                } else {
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String description = c.getString("description");
                        String id = c.getString("id");
                        String photo1 = c.getString("photo1");
                        String photo2 = c.getString("photo2");
                        String price = c.getString("price");
                        String title = c.getString("title");

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("_highlightResult");
                        JSONObject descriptionn = phone.getJSONObject("description");
                        JSONObject titlee = phone.getJSONObject("title");
                        String descri = descriptionn.getString("value");
                        String tit = titlee.getString("value");

                        // tmp hash map for single contact
                        final List<Search> searchList = new ArrayList<>();

                        search.setId(id);
                        search.setTitle(title);
                        search.setPhoto1(photo1);
                        search.setPhoto2(photo2);
                        search.setDescription(description);
                        search.setPrice(price);
                        // search.setId(id);
                        // adding each child node to HashMap key => value
                        searchList.add(search);


                        final HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("description", description);
                        contact.put("price", price);
                        contact.put("photo1", photo1);
                        contact.put("photo2", photo2);
                        contact.put("title", title);
                        contact.put("titlee", tit);
                        contact.put("descriptionn", descri);

                        // adding contact to contact list
                        contactList.add(contact);
                        Log.e(TAG, "getDAta: " + contactList.size());
                        list.add(Html.fromHtml(title, Html.FROM_HTML_MODE_COMPACT)
                                + "\n" + Html.fromHtml(descri, Html.FROM_HTML_MODE_COMPACT) + "\n" + price + "\n" + photo1 + "\n" + photo2);
                        if (!TextUtils.isEmpty(title))
                            web.add(title);
                        if (!TextUtils.isEmpty(photo1))
                            imageId.add(photo1);

                        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.itemlistrow, list);
                        // output.setAdapter(ad);

                        try {
                            adapter = new
                                    CustomList(SearchActivity.this, web, imageId);
                            output.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        SearchAdapter searchAdapter = new SearchAdapter(SearchActivity.this, contactList);
                   /* SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), contactList, R.layout.item_category);
                    output.setAdapter(simpleAdapter);*/
                        recyclerView.setAdapter(searchAdapter);
                        searchAdapter.notifyDataSetChanged();
                        // adapter.notifyDataSetChanged();
                    }
                }

            } catch (final JSONException e) {
                web.clear();
                imageId.clear();
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        web.clear();
                        imageId.clear();
                       /* Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();*/
                    }
                });


            }
        }
    }

    private void filter(String toString) {
        //new array list that will hold the filtered data
        ArrayList<Search> filterdNames = new ArrayList<>();

        //looping through existing elements
      /*  for (String s : names) {
            //if the existing elements contains the search input
            if (s.toLowerCase().contains(toString.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }
*/
        //calling a method of the adapter class and passing the filtered list
        //adapter.filterList(filterdNames);

    }
}
