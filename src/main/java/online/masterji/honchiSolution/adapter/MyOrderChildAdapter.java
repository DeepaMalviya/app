package online.masterji.honchiSolution.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.OrderDetailsActivity;
import online.masterji.honchiSolution.activity.TrackOrderActivity;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.Cart;
import online.masterji.honchiSolution.domain.Order;

public class MyOrderChildAdapter extends RecyclerView.Adapter<MyOrderChildAdapter.ViewHolder> {
    private static final String TAG = "MyOrderChildAdapter";

    private List<Cart> mDataset;
    // private List<Order> mDataset;
    private Context mContext;
    private Order orderData;
    String data, order_Id;

    public MyOrderChildAdapter(List<Cart> list, Context context, Order order, String order_Id) {
        this.mDataset = list;
        this.mContext = context;
        this.orderData = order;
        this.order_Id = order_Id;


    }

   /* public MyOrderChildAdapter(List<Order> orderList, Context context, Order order, String order_id) {
        this.mDataset = orderList;
        this.mContext = context;
        this.orderData = order;
        this.order_Id = order_Id;
    }*/



    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_child_my_order, parent, false));
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder( final ViewHolder holder, final int position) {
        data = orderData.getOrderId();
        orderData.getAddress();
        orderData.getBookingDate();
        orderData.getBookingTimeSlot();
        orderData.getOrderDate();
        orderData.getDeliveredDate();
        orderData.getUserId();
       /* Log.e(TAG, "onBindViewHolder:==== orderData.getAddress() " + orderData.getAddress());
        Log.e(TAG, "onBindViewHolder:==== orderData.getBookingDate() " + orderData.getBookingDate());
        Log.e(TAG, "onBindViewHolder:==== orderData.getBookingTimeSlot() " + orderData.getBookingTimeSlot());
        Log.e(TAG, "onBindViewHolder:==== orderData.getOrderDate() =========" + orderData.getOrderDate());
        Log.e(TAG, "onBindViewHolder:==== orderData.getDeliveredDate() " + orderData.getDeliveredDate());
        Log.e(TAG, "onBindViewHolder:==== orderData.getUserId() " + orderData.getUserId());
        Log.e(TAG, "onBindViewHolder:==== orderData.getProducts() " + orderData.getProducts());
        Log.e(TAG, "onBindViewHolder:==== orderData.getOrderId() " + orderData.getOrderId());
        Log.e(TAG, "onBindViewHolder:==== orderData.getDesign()======= " + orderData.getDesign());
        Log.e(TAG, "onBindViewHolder:==== orderData.getFebric()========= " + orderData.getFebric());
        Log.e(TAG, "onBindViewHolder:==== orderData.getDispatchedDate()========= " + orderData.getDispatchedDate());
        Log.e(TAG, "onBindViewHolder:==== orderData.getTransitDate()========= " + orderData.getTransitDate());
        Log.e(TAG, "onBindViewHolder:==== orderData.getDispatchedDate()========= " + orderData.getTransitDate());
        Log.e(TAG, "onBindViewHolder:==== orderData.isDelivered()========= " + orderData.isDelivered());
        Log.e(TAG, "onBindViewHolder:==== orderData.isDispatched()========= " + orderData.isDispatched());
        Log.e(TAG, "onBindViewHolder:==== orderData.isOrderPlaced()========= " + orderData.isOrderPlaced());
        Log.e(TAG, "onBindViewHolder:==== orderData.isTransit()========= " + orderData.isTransit());
        Log.e(TAG, "onBindViewHolder:==== orderData.isTransit()========= " + orderData.isTransit());
        Log.e(TAG, "onBindViewHolder:==== data " + data);
        Log.e(TAG, "onBindViewHolder:==== order_Id " + order_Id);
     */   //  order_Id = data;
        final Cart cart = mDataset.get(position);
        Log.e(TAG, "onBindViewHolder:isCancelStatus " + cart.isCancelStatus());
        if (cart.isCancelStatus()) {
            holder.tvOrderStatus.setBackground(mContext.getDrawable(R.drawable.round_back));
            holder.tvOrderStatus.setText("  " + Constants.OrderStatusString.CANCELED + "  ");

        } else {
            holder.tvOrderStatus.setBackground(mContext.getDrawable(R.drawable.round_green));
            holder.tvOrderStatus.setText("  " + Constants.OrderStatusString.CONFIRMED + "  ");
        }
       /* if (orderData.getStatus() == Constants.OrderStatus.REJECTED) {
            holder.tvOrderStatus.setText(Constants.OrderStatusString.REJECTED);
        } else if (orderData.getStatus() == Constants.OrderStatus.WAITING_FOR_CONFIRMATION) {
            holder.tvOrderStatus.setText(Constants.OrderStatusString.WAITING_FOR_CONFIRMATION);
        } else if (orderData.getStatus() == Constants.OrderStatus.CONFIRMED) {
            holder.tvOrderStatus.setText(Constants.OrderStatusString.CONFIRMED);
        } else if (orderData.getStatus() == Constants.OrderStatus.IN_PROCESS) {
            holder.tvOrderStatus.setText(Constants.OrderStatusString.IN_PROCESS);
        } else if (orderData.getStatus() == Constants.OrderStatus.OUT_FOR_DELIVERY) {
            holder.tvOrderStatus.setText(Constants.OrderStatusString.OUT_FOR_DELIVERY);
        } else if (orderData.getStatus() == Constants.OrderStatus.DELIVERED) {
            holder.tvOrderStatus.setText(Constants.OrderStatusString.DELIVERED);
        } else if (orderData.getStatus() == Constants.OrderStatus.CANCELED) {
            holder.tvOrderStatus.setText(Constants.OrderStatusString.CANCELED);
        } else if (orderData.getStatus() == Constants.OrderStatus.REFUND) {
            holder.tvOrderStatus.setText(Constants.OrderStatusString.REFUND);
        } else if (orderData.getStatus() == Constants.OrderStatus.RETRUN) {
            holder.tvOrderStatus.setText(Constants.OrderStatusString.RETRUN);
        }*/
        holder.tvtitle.setText(cart.getTitle());
        holder.tvPrice.setText(cart.getPrice());
        holder.linearOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                intent.putExtra("Title", cart.getTitle());
                intent.putExtra("data", data);
                intent.putExtra("Price", cart.getPrice());
                intent.putExtra("Id", cart.getId());
                intent.putExtra("Photo", cart.getPhoto());
                // intent.putExtra("ProductId", cart.getProductId());
                intent.putExtra("SubCategory", cart.getSubCategory());
                intent.putExtra("SuperCategory", cart.getSuperCategory());
                intent.putExtra("Address", orderData.getAddress());
                intent.putExtra("BookingDate", orderData.getBookingDate());
                intent.putExtra("BookingTimeSlot", orderData.getBookingTimeSlot());
                intent.putExtra("OrderDate", orderData.getOrderDate());
                intent.putExtra("DeliveredDate", orderData.getDeliveredDate());
                intent.putExtra("getUserId", orderData.getUserId());
                intent.putExtra("OrderId", orderData.getOrderId());
                intent.putExtra("getOrderId", orderData.getOrderId());
                intent.putExtra("getDesign", orderData.getDesign());
                intent.putExtra("getFebric", orderData.getFebric());
                intent.putExtra("DispatchedDate", orderData.getDispatchedDate());
                intent.putExtra("Delivered", orderData.isDelivered());
                intent.putExtra("Dispatched", orderData.isDispatched());
                intent.putExtra("OrderPlaced", orderData.isOrderPlaced());
                intent.putExtra("Transit", orderData.isTransit());
                intent.putExtra("TransitDate", orderData.getTransitDate());
                intent.putExtra("isCancelStatus", cart.isCancelStatus());

                Bundle bundle = new Bundle();
                //Add your data from getFactualResults method to bundle
                bundle.putString("VENUE_NAME", order_Id);
                //Add the bundle to the intent
                intent.putExtras(bundle);
                // intent.putExtra("new", (Serializable) orderData);


                mContext.startActivity(intent);
            }
        });

        Glide.with(mContext)
                .load(cart.getPhoto())
                .centerInside()
                .placeholder(R.drawable.ic_image_placeholder)
                .into(holder.ivProductPhoto);

        holder.TvTrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TrackOrderActivity.class);
                intent.putExtra("Track", "Order");
                intent.putExtra("Title", cart.getTitle());
                intent.putExtra("Price", cart.getPrice());
                intent.putExtra("Id", cart.getId());
                intent.putExtra("Photo", cart.getPhoto());
                // intent.putExtra("ProductId", cart.getProductId());
                intent.putExtra("SubCategory", cart.getSubCategory());
                intent.putExtra("SuperCategory", cart.getSuperCategory());
                intent.putExtra("Address", orderData.getAddress());
                intent.putExtra("BookingDate", orderData.getBookingDate());
                intent.putExtra("BookingTimeSlot", orderData.getBookingTimeSlot());
                intent.putExtra("OrderDate", orderData.getOrderDate());
                intent.putExtra("DeliveredDate", orderData.getDeliveredDate());
                intent.putExtra("getUserId", orderData.getUserId());
                intent.putExtra("OrderId", orderData.getOrderId());
                intent.putExtra("getOrderId", orderData.getOrderId());
                intent.putExtra("DispatchedDate", orderData.getDispatchedDate());
                intent.putExtra("Delivered", orderData.isDelivered());
                intent.putExtra("Dispatched", orderData.isDispatched());
                intent.putExtra("OrderPlaced", orderData.isOrderPlaced());
                intent.putExtra("Transit", orderData.isTransit());
                intent.putExtra("TransitDate", orderData.getTransitDate());

                mContext.startActivity(intent);
            }
        });
        holder.TVOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                intent.putExtra("Title", cart.getTitle());
                intent.putExtra("Price", cart.getPrice());
                intent.putExtra("Id", cart.getId());
                intent.putExtra("Photo", cart.getPhoto());
                //  intent.putExtra("ProductId", cart.getProductId());
                intent.putExtra("SubCategory", cart.getSubCategory());
                intent.putExtra("SuperCategory", cart.getSuperCategory());
                intent.putExtra("Address", orderData.getAddress());
                intent.putExtra("BookingDate", orderData.getBookingDate());
                intent.putExtra("BookingTimeSlot", orderData.getBookingTimeSlot());
                intent.putExtra("OrderDate", orderData.getOrderDate());
                intent.putExtra("DeliveredDate", orderData.getDeliveredDate());
                intent.putExtra("getUserId", orderData.getUserId());
                intent.putExtra("getOrderId", orderData.getOrderId());
                intent.putExtra("getDesign", orderData.getDesign());
                intent.putExtra("getFebric", orderData.getFebric());
                intent.putExtra("isCancelStatus", cart.isCancelStatus());

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return mDataset.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvtitle, TVOrderDetail, TvTrackOrder;
        private TextView tvOrderStatus, tvPrice;
        LinearLayout linearOrder;
        private ImageView ivProductPhoto;


        public ViewHolder(View itemView) {
            super(itemView);
            tvtitle = itemView.findViewById(R.id.tvtitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivProductPhoto = itemView.findViewById(R.id.ivProductPhoto);
            linearOrder = itemView.findViewById(R.id.linearOrder);
            TVOrderDetail = itemView.findViewById(R.id.TVOrderDetail);
            TvTrackOrder = itemView.findViewById(R.id.TvTrackOrder);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);

        }


    }
}


