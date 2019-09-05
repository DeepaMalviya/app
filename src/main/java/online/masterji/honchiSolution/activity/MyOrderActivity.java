package online.masterji.honchiSolution.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.adapter.MyOrderParentAdapter;
import online.masterji.honchiSolution.domain.Order;
import online.masterji.honchiSolution.util.WaitDialog;

import static android.support.v7.widget.LinearLayoutManager.*;

public class MyOrderActivity extends AppCompatActivity {
    private static final String TAG = "MyOrderActivity";

    RecyclerView recyclerView;
    WaitDialog waitDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        waitDialog = new WaitDialog(this);
        getOrders();
    }

    private void initView() {

        recyclerView = findViewById(R.id.recyclerView);


    }


    private void initAdapter(List<Order> orderList) {
        MyOrderParentAdapter adapter = new MyOrderParentAdapter(orderList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }


    private void getOrders() {
        waitDialog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Order");
        collectionReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            List<Order> orderList = task.getResult().toObjects(Order.class);
                            initAdapter(orderList);


                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                        }
                        waitDialog.dismiss();
                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

