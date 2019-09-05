package online.masterji.honchiSolution.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.adapter.MyOrderChildAdapter;
import online.masterji.honchiSolution.adapter.MyOrderParentAdapter;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.Cart;
import online.masterji.honchiSolution.domain.Order;
import online.masterji.honchiSolution.util.WaitDialog;


public class OrderFragment extends Fragment {
    private static final String TAG = "OrderFragment";
    private static final String ARG_COLUMN_COUNT = "column-count";
    WaitDialog waitDialog;
    RecyclerView recyclerView;
    LinearLayout layoutEmptyo;
    Order order = new Order();
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OrderFragment() {
        Log.e(TAG, "OrderFragment: ");
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static OrderFragment newInstance(int columnCount) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        waitDialog = new WaitDialog(getContext());
        layoutEmptyo = (LinearLayout) view.findViewById(R.id.layoutEmptyo);
        recyclerView = view.findViewById(R.id.list);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {

            }
        }
        getOrders();

        return view;
    }
    List<Order> orderList = null;

    private void getOrders() {
        waitDialog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Order");
        db.collection("Order").whereEqualTo("userId", Constants.Uid).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.e(TAG, "onSuccess: " );
                    }
                });

        collectionReference.whereEqualTo("userId", Constants.Uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());


                    }
                    try {
                        orderList = task.getResult().toObjects(Order.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        if (orderList.isEmpty()) {
                            recyclerView.setVisibility(View.GONE);
                            layoutEmptyo.setVisibility(View.VISIBLE);
                            layoutEmptyo.setVisibility(View.VISIBLE);

                        } else {
                            for (int i = 0; i < orderList.size(); i++) {
                                if (!TextUtils.isEmpty(Constants.Uid)) {
                                    if (orderList.get(i).getUserId().equals(Constants.Uid)) {
                                        initAdapter(orderList);

                                    }
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                   /* if (CollectionUtils.isEmpty(orderList)) {
                        recyclerView.setVisibility(View.VISIBLE);
                        layoutEmptyo.setVisibility(View.GONE);

                        orderList = task.getResult().toObjects(Order.class);

                        Log.d(TAG, "onComplete() called with: task = [" + orderList.size() + "]");


                        for (int i = 0; i < orderList.size(); i++) {
                            if (!TextUtils.isEmpty(Constants.Uid)) {
                                if (orderList.get(i).getUserId().equals(Constants.Uid)) {
                                    initAdapter(orderList);

                                }
                            }
                        }
                    } else {


                        recyclerView.setVisibility(View.GONE);
                        layoutEmptyo.setVisibility(View.VISIBLE);

                    }
*/
                } else {
                    Log.e(TAG, "Error getting documents: ", task.getException());
                }
                waitDialog.dismiss();
            }
        });

    }

    List<Cart> list;

    private void initAdapter(final List<Order> orderList) {
        final Cart cart = new Cart();
        list = new ArrayList<>();
        Log.e(TAG, "initAdapter: " + orderList.size());
        list = orderList.get(0).getProducts();
        order = orderList.get(0);
        String order_Id = orderList.get(0).getOrderId();
        Log.e(TAG, "onBindViewHolder:order_Id " + order_Id);
        Log.e(TAG, "onBindViewHolder:getOrderDate " + order.getOrderDate());

        //MyOrderChildAdapter childAdapter = new MyOrderChildAdapter(orderList, getContext(), order, order_Id);

        MyOrderParentAdapter adapter = new MyOrderParentAdapter(orderList, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
      /*  recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.e(TAG, "onClick: " + "==Id===" + orderList.get(position).getOrderId());
                Intent intent1 = new Intent(getContext(), OrderDetailsActivity.class);
                intent1.putExtra("product", orderList.get(position).getOrderId());
               // productt = categoryList.get(position).getId();
                intent1.putExtra("getOrderId", orderList.get(position).getOrderId());
                 startActivity(intent1);


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/
    }


}
