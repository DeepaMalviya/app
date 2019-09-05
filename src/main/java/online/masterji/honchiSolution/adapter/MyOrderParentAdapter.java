package online.masterji.honchiSolution.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.util.CollectionUtils;

import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.domain.Cart;
import online.masterji.honchiSolution.domain.Order;

public class MyOrderParentAdapter extends RecyclerView.Adapter<MyOrderParentAdapter.ViewHolder> {

    private static final String TAG = "MyOrderParentAdapter";
    private Context context;
    private List<Order> mDataset;
    private MyOrderChildAdapter childAdapter;
    private RecyclerView.RecycledViewPool recycledViewPool;
    List<Cart> list;
    Order order;
    Cart cart = null;
    public String order_Id;
    ;

    public MyOrderParentAdapter(List<Order> data, Context context) {
        this.mDataset = data;
        this.context = context;

        recycledViewPool = new RecyclerView.RecycledViewPool();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View theView = LayoutInflater.from(context).inflate(R.layout.item_parent_my_order, parent, false);
        return new ViewHolder(theView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        list = mDataset.get(position).getProducts();
        order = mDataset.get(position);
        order_Id = mDataset.get(position).getOrderId();
        Log.e(TAG, "onBindViewHolder:order_Id " + order_Id);
        Log.e(TAG, "onBindViewHolder:getOrderDate " + order.getOrderDate());
        Log.e(TAG, "onBindViewHolder:getProducts " + mDataset.get(position).getProducts().size());
       /* if (mDataset.get(position).getProducts().size() == 0) {
            holder.linearOrderr.setVisibility(View.GONE);
        } else {
            Log.e(TAG, "getProducts().size(): else"+mDataset.get(position).getProducts().size() );
            if (!CollectionUtils.isEmpty(mDataset.get(position).getProducts())) {
                childAdapter = new MyOrderChildAdapter(mDataset.get(position).getProducts(), context, order, order_Id);
                holder.childRecyclerView.setAdapter(childAdapter);
                holder.childRecyclerView.setHasFixedSize(true);

                holder.childRecyclerView.setRecycledViewPool(recycledViewPool);
           *//* holder.linearOrderr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(getApplicationContext(), OrderDetailsActivity.class);
                    intent1.putExtra("product", mDataset.get(position).getOrderId());
                    // productt = categoryList.get(position).getId();
                    intent1.putExtra("getOrderId", mDataset.get(position).getOrderId());
                    getApplicationContext().startActivity(intent1);

                }
            });*//*
                childAdapter.notifyDataSetChanged();


            }
        }*/
        childAdapter = new MyOrderChildAdapter(mDataset.get(position).getProducts(), context, order, order_Id);
        holder.childRecyclerView.setAdapter(childAdapter);
        holder.childRecyclerView.setHasFixedSize(true);

        holder.childRecyclerView.setRecycledViewPool(recycledViewPool);
           /* holder.linearOrderr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(getApplicationContext(), OrderDetailsActivity.class);
                    intent1.putExtra("product", mDataset.get(position).getOrderId());
                    // productt = categoryList.get(position).getId();
                    intent1.putExtra("getOrderId", mDataset.get(position).getOrderId());
                    getApplicationContext().startActivity(intent1);
     holder.childRecyclerView.setRecycledViewPool(recycledViewPool);
           /* holder.linearOrderr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(getApplicationContext(), OrderDetailsActivity.class);
                    intent1.putExtra("product", mDataset.get(position).getOrderId());
                    // productt = categoryList.get(position).getId();
                    intent1.putExtra("getOrderId", mDataset.get(position).getOrderId());
                    getApplicationContext().startActivity(intent1);

                }
            });*/
        childAdapter.notifyDataSetChanged();

        if (!CollectionUtils.isEmpty(mDataset.get(position).getProducts())) {
            childAdapter = new MyOrderChildAdapter(mDataset.get(position).getProducts(), context, order, order_Id);
            holder.childRecyclerView.setAdapter(childAdapter);
            holder.childRecyclerView.setHasFixedSize(true);

            holder.childRecyclerView.setRecycledViewPool(recycledViewPool);
           /* holder.linearOrderr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(getApplicationContext(), OrderDetailsActivity.class);
                    intent1.putExtra("product", mDataset.get(position).getOrderId());
                    // productt = categoryList.get(position).getId();
                    intent1.putExtra("getOrderId", mDataset.get(position).getOrderId());
                    getApplicationContext().startActivity(intent1);
     holder.childRecyclerView.setRecycledViewPool(recycledViewPool);
           /* holder.linearOrderr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(getApplicationContext(), OrderDetailsActivity.class);
                    intent1.putExtra("product", mDataset.get(position).getOrderId());
                    // productt = categoryList.get(position).getId();
                    intent1.putExtra("getOrderId", mDataset.get(position).getOrderId());
                    getApplicationContext().startActivity(intent1);

                }
            });*/
                childAdapter.notifyDataSetChanged();





        }
    }


    @Override
    public int getItemCount() {
        return mDataset.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView childRecyclerView;
        private TextView tvOrderStatus;
        private LinearLayoutManager horizontalManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        CardView linearOrderr;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            linearOrderr = itemView.findViewById(R.id.linearOrderr);
            childRecyclerView = itemView.findViewById(R.id.childRecyclerView);
            childRecyclerView.setHasFixedSize(true);
            childRecyclerView.setNestedScrollingEnabled(false);
            childRecyclerView.setLayoutManager(horizontalManager);
            childRecyclerView.setItemAnimator(new DefaultItemAnimator());


        }


    }


}



