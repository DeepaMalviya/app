package online.masterji.honchiSolution.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.BillingActivity;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.BookingTailor;
import online.masterji.honchiSolution.domain.Cart;
import online.masterji.honchiSolution.domain.Listdata;
import online.masterji.honchiSolution.domain.Order;
import online.masterji.honchiSolution.util.WaitDialog;


public class BillingFragment extends Fragment {
    private static final String TAG = "BillingFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    List<BookingTailor> bookingTailors = new ArrayList<>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Listdata> list;
    RecyclerView recyclerview1, recyclerview;
    LinearLayout layoutEmpty;
    List<Order> orderList= new ArrayList<>();

    public BillingFragment() {
        // Required empty public constructor
    }


    public static BillingFragment newInstance(String param1, String param2) {
        BillingFragment fragment = new BillingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    WaitDialog waitDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billing, container, false);
        view.setBackgroundColor(Color.WHITE);
        waitDialog = new WaitDialog(getContext());
        recyclerview = (RecyclerView) view.findViewById(R.id.rview);
        recyclerview1 = (RecyclerView) view.findViewById(R.id.rview1);
        layoutEmpty = view.findViewById(R.id.layoutEmpty);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            getBookingATailorOrders();
            getOrders();

            //layoutEmpty.setVisibility(View.GONE);


        } else {
            layoutEmpty.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.GONE);
            recyclerview1.setVisibility(View.GONE);

        }
       /* if (bookingTailors.isEmpty()) {
            if (orderList.isEmpty()) {
                recyclerview.setVisibility(View.GONE);
                layoutEmpty.setVisibility(View.VISIBLE);
                layoutEmpty.setVisibility(View.VISIBLE);

            } else {
                recyclerview.setVisibility(View.VISIBLE);
                layoutEmpty.setVisibility(View.GONE);
                layoutEmpty.setVisibility(View.GONE);
            }
            recyclerview1.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.VISIBLE);

        } else {
            if (orderList.isEmpty()) {
                recyclerview.setVisibility(View.GONE);
                layoutEmpty.setVisibility(View.VISIBLE);
                layoutEmpty.setVisibility(View.VISIBLE);

            } else {
                recyclerview.setVisibility(View.VISIBLE);
                layoutEmpty.setVisibility(View.GONE);
                layoutEmpty.setVisibility(View.GONE);
            }
            recyclerview1.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.GONE);
        }
*/
       // RecyclerviewAdapter recycler = new RecyclerviewAdapter(list);
        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutmanager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        // recyclerview.setAdapter(recycler);


        return view;


    }

   private void getBookingATailorOrders() {
        waitDialog.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("BookingTailor");
        db.collection("BookingTailor").whereEqualTo("id", Constants.Uid).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    }
                });
        /*.orderBy("date", Query.Direction.ASCENDING).limit(10)*/
        collectionReference.whereEqualTo("id", Constants.Uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());


                    }
                    try {
                        bookingTailors = task.getResult().toObjects(BookingTailor.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (bookingTailors.isEmpty()) {
                            recyclerview1.setVisibility(View.GONE);
                           /* layoutEmpty.setVisibility(View.VISIBLE);
                            layoutEmpty.setVisibility(View.VISIBLE);*/

                        } else {
                            for (int i = 0; i < bookingTailors.size(); i++) {
                                if (!TextUtils.isEmpty(Constants.Uid)) {
                                    if (bookingTailors.get(i).getId().equals(Constants.Uid)) {
                                        initAdapterBooking(bookingTailors);

                                    }
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    Log.e(TAG, "Error getting documents: ", task.getException());
                }
                waitDialog.dismiss();
            }
        });
    }

    private void initAdapterBooking(List<BookingTailor> bookingTailors) {
        final Cart cart = new Cart();
        list = new ArrayList<>();
        String order_Id = bookingTailors.get(0).getOrderId();

        //MyOrderChildAdapter childAdapter = new MyOrderChildAdapter(orderList, getContext(), order, order_Id);

        MyBookingAdapter adapter = new MyBookingAdapter(bookingTailors, getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerview1.setHasFixedSize(true);
        recyclerview1.setLayoutManager(layoutManager);
        recyclerview1.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        int newMsgPosition = bookingTailors.size() - 1;

        adapter.notifyItemInserted(newMsgPosition);

        recyclerview1.scrollToPosition(newMsgPosition);


    }

    private void getOrders() {
        //  waitDialog.show();

        Log.e(TAG, "getOrders: " );
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Order");
        db.collection("Order").whereEqualTo("userId", Constants.Uid).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

                            // Collections.reverse(orderList);

                           /* try {
                                SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                sfd.format(new Date(String.valueOf(orderList.get(0).getBookingDate())));
                                long timestamp = 0;
                                sfd.format(new Date(timestamp));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }*/
                            try {
                                getDateFromString(String.valueOf(orderList.get(0).getBookingDate()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                if (orderList.isEmpty()) {
                                    recyclerview.setVisibility(View.GONE);
                                   /* layoutEmpty.setVisibility(View.VISIBLE);
                                    layoutEmpty.setVisibility(View.VISIBLE);*/

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


                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                        }
                        //  waitDialog.dismiss();
                    }
                });

    }

    static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public Date getDateFromString(String datetoSaved) {

        try {
            Date date = format.parse(datetoSaved);

            Log.e(TAG, "getDateFromString:date " + date);
            return date;
        } catch (ParseException e) {
            return null;
        }

    }

    private void initAdapter(List<Order> orderList) {

        RecyclerviewAdapter recycler = new RecyclerviewAdapter(getContext(), orderList);
        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutmanager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(recycler);


        // MyOrderParentAdapter adapter = new MyOrderParentAdapter(orderList, getContext());
        // LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //  recyclerview.setHasFixedSize(true);
        // recyclerview.setLayoutManager(layoutManager);
        // recyclerview.setAdapter(adapter);
    }

    private class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyHolder> {

        List<Order> listdata;
        Context context;

        public RecyclerviewAdapter(Context context, List<Order> listdata) {
            this.listdata = listdata;
            this.context = context;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.billing_row, parent, false);

            MyHolder myHolder = new MyHolder(view);
            return myHolder;
        }


        public void onBindViewHolder(MyHolder holder, final int position) {
            final Order data = listdata.get(position);
          /*  Boolean isCancelStatus = listdata.get(position).getProducts().get(position).isCancelStatus();
            if (isCancelStatus) {*/

            if (listdata.get(position).getProducts().size() == 0) {
                holder.linearTrack.setVisibility(View.GONE);
            } else {
                Log.e(TAG, "getProducts().size(): else" + listdata.get(position).getProducts().size());
                try {
                    holder.vname.setText("Billing");
                    holder.vemail.setText(data.getProducts().get(0).getTitle());
                    holder.vaddress.setText(data.getProducts().get(0).getPrice());
                    holder.linearTrack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), BillingActivity.class);
                            intent.putExtra("Billing", "Order");
                            intent.putExtra("Title", data.getProducts().get(0).getTitle());
                            intent.putExtra("Price", data.getProducts().get(0).getPrice());
                            intent.putExtra("Id", data.getProducts().get(0).getId());
                            intent.putExtra("Photo", data.getProducts().get(0).getPhoto());
                            // intent.putExtra("ProductId", data.getProducts().get(0).getProductId());
                            intent.putExtra("SubCategory", data.getProducts().get(0).getSubCategory());
                            intent.putExtra("SuperCategory", data.getProducts().get(0).getSuperCategory());
                            intent.putExtra("getId", data.getOrderId());
                            startActivity(intent);
                        }
                    });
                    Glide.with(context)
                            .load(data.getProducts().get(0).getPhoto())
                            .centerInside()
                            .placeholder(R.drawable.ic_image_placeholder)
                            .into(holder.profile_image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



           /* } else {


            }
*/

        }

        @Override
        public int getItemCount() {
            return listdata.size();
        }


        class MyHolder extends RecyclerView.ViewHolder {
            TextView vname, vaddress, vemail;
            LinearLayout linearTrack;
            CircleImageView profile_image;

            public MyHolder(View itemView) {
                super(itemView);
                profile_image = itemView.findViewById(R.id.profile_image);
                vname = (TextView) itemView.findViewById(R.id.vname);
                vemail = (TextView) itemView.findViewById(R.id.vemail);
                vaddress = (TextView) itemView.findViewById(R.id.vaddress);
                linearTrack = itemView.findViewById(R.id.linearTrack);

            }
        }

    }

    private class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.MyViewHolder> {
        List<BookingTailor> bookingTailors;
        Context context;

        public MyBookingAdapter(List<BookingTailor> bookingTailors, Context context) {
            this.bookingTailors = bookingTailors;
            this.context = context;

        }

        @NonNull
        @Override
        public MyBookingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.billing_row, viewGroup, false);

            return new MyBookingAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyBookingAdapter.MyViewHolder myViewHolder, final int i) {
            if (bookingTailors.get(i).getId().equals(null)) {
                myViewHolder.linearTrack.setVisibility(View.GONE);
            } else {
                Log.e(TAG, "getId" + bookingTailors.get(i).getId());
                Log.e(TAG, "getOrderId" + bookingTailors.get(i).getOrderId());
                try {
                    myViewHolder.vname.setText("Billing");
                    myViewHolder.vemail.setText(bookingTailors.get(i).getName());
                    myViewHolder.vaddress.setText(bookingTailors.get(i).getWork());
                    myViewHolder.linearTrack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), BillingActivity.class);
                            intent.putExtra("Billing", "Booking");
                            intent.putExtra("Price", bookingTailors.get(i).getPrice());
                            intent.putExtra("CancelReason", bookingTailors.get(i).getCancelReason());
                            intent.putExtra("DesignImage", bookingTailors.get(i).getDesignImage());
                            intent.putExtra("Design", bookingTailors.get(i).getDesign());
                            intent.putExtra("Work", bookingTailors.get(i).getWork());
                            intent.putExtra("Type", bookingTailors.get(i).getType());
                            intent.putExtra("Time", bookingTailors.get(i).getTime());
                            intent.putExtra("Name", bookingTailors.get(i).getName());
                            intent.putExtra("Mobile", bookingTailors.get(i).getMobile());
                            intent.putExtra("Gender", bookingTailors.get(i).getGender());
                            intent.putExtra("FebricImage", bookingTailors.get(i).getFebricImage());
                            intent.putExtra("Febric", bookingTailors.get(i).getFebric());
                            intent.putExtra("Email", bookingTailors.get(i).getEmail());
                            intent.putExtra("Date", bookingTailors.get(i).getDate());
                            intent.putExtra("Category", bookingTailors.get(i).getCategory());
                            intent.putExtra("Address", bookingTailors.get(i).getAddress());
                            intent.putExtra("OrderId", bookingTailors.get(i).getOrderId());
                            intent.putExtra("Id", bookingTailors.get(i).getId());

                            startActivity(intent);
                        }
                    });
                    if (!TextUtils.isEmpty(bookingTailors.get(i).getFebricImage())) {
                        Glide.with(context)
                                .load(bookingTailors.get(i).getFebricImage())
                                .centerInside()
                                .placeholder(R.drawable.ic_image_placeholder)
                                .into(myViewHolder.profile_image);

                    } else if (!TextUtils.isEmpty(bookingTailors.get(i).getDesignImage())) {
                        Glide.with(context)
                                .load(bookingTailors.get(i).getDesignImage())
                                .centerInside()
                                .placeholder(R.drawable.ic_image_placeholder)
                                .into(myViewHolder.profile_image);
                    } else {

                        Glide.with(context)
                                .load(bookingTailors.get(i).getDesignImage())
                                .centerInside()
                                .placeholder(R.drawable.ic_image_placeholder)
                                .into(myViewHolder.profile_image);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



           /* myViewHolder.textViewName.setText("Name :  " + bookingTailors.get(i).getName());
            myViewHolder.textViewMobile.setText("Mobile :  " + bookingTailors.get(i).getMobile());
            myViewHolder.textViewtype.setText("Type :  " + bookingTailors.get(i).getWork());
            myViewHolder.textViewAddress.setText("Address :  " + bookingTailors.get(i).getAddress());
            myViewHolder.textViewGender.setText("Gender :  " + bookingTailors.get(i).getGender());
           *//* if (bookingTailors.get(i).isCancelStatus()) {
                myViewHolder.cvHome.setBackgroundColor(getResources().getColor(R.color.red_600));

            } else {
                myViewHolder.cvHome.setBackgroundColor(getResources().getColor(R.color.green_800));

            }*//*

            if (bookingTailors.get(i).isCancelStatus()) {
                myViewHolder.textViewName1.setBackground(getResources().getDrawable(R.drawable.round_back));
                myViewHolder.textViewName1.setText("  " + Constants.OrderStatusString.CANCELED + "  ");
                myViewHolder.textViewName1.setTextColor(getResources().getColor(R.color.white));

            } else {
                myViewHolder.textViewName1.setTextColor(getResources().getColor(R.color.white));
                myViewHolder.textViewName1.setBackground(getResources().getDrawable(R.drawable.round_green));
                myViewHolder.textViewName1.setText("  " + Constants.OrderStatusString.CONFIRMED + "  ");
            }
            toDate(bookingTailors.get(i).getDate().getSeconds());

            myViewHolder.textViewDate.setText("Date :  " + toDate(bookingTailors.get(i).getDate().getSeconds()));
            myViewHolder.textViewTime.setText("Time :  " + bookingTailors.get(i).getTime());
            myViewHolder.linearHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick: " + "==Id===" + bookingTailors.get(i).getId());
                    Intent intent1 = new Intent(getContext(), BookingDetailsActivity.class);
                    intent1.putExtra("Id", bookingTailors.get(i).getId());
                    intent1.putExtra("OrderId", bookingTailors.get(i).getOrderId());
                    intent1.putExtra("Address", bookingTailors.get(i).getAddress());
                    intent1.putExtra("Category", bookingTailors.get(i).getCategory());
                    intent1.putExtra("Date", toDate(bookingTailors.get(i).getDate().getSeconds()));
                    intent1.putExtra("Design", bookingTailors.get(i).getDesign());
                    intent1.putExtra("Email", bookingTailors.get(i).getEmail());
                    intent1.putExtra("Febric", bookingTailors.get(i).getFebric());
                    intent1.putExtra("FebricImage", bookingTailors.get(i).getFebricImage());
                    intent1.putExtra("Gender", bookingTailors.get(i).getGender());
                    intent1.putExtra("Mobile", bookingTailors.get(i).getMobile());
                    intent1.putExtra("Name", bookingTailors.get(i).getName());
                    intent1.putExtra("Price", bookingTailors.get(i).getPrice());
                    intent1.putExtra("Time", bookingTailors.get(i).getTime());
                    intent1.putExtra("Type", bookingTailors.get(i).getType());
                    intent1.putExtra("Work", bookingTailors.get(i).getWork());
                    intent1.putExtra("DesignImage", bookingTailors.get(i).getDesignImage());
                    intent1.putExtra("CancelReason", bookingTailors.get(i).getCancelReason());
                    intent1.putExtra("CancelStatus", bookingTailors.get(i).isCancelStatus());
                    intent1.putExtra("Price", bookingTailors.get(i).getPrice());
                    startActivity(intent1);
                }
            });*/


        }

        private String toDate(long timestamp) {
            Date date = new Date(timestamp * 1000);
            Log.e(TAG, "toDate: " + timestamp * 1000);
            Log.e(TAG, "toDate: " + date);
            Log.e(TAG, "toDate: " + new SimpleDateFormat("dd-MM-yyyy").format(date));
            return new SimpleDateFormat("dd-MM-yyyy").format(date);
        }

        @Override
        public int getItemCount() {
            return bookingTailors.size();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder {
            TextView vname, vaddress, vemail;
            LinearLayout linearTrack;
            CircleImageView profile_image;

            public MyViewHolder(View itemView) {
                super(itemView);
                profile_image = itemView.findViewById(R.id.profile_image);
                vname = (TextView) itemView.findViewById(R.id.vname);
                vemail = (TextView) itemView.findViewById(R.id.vemail);
                vaddress = (TextView) itemView.findViewById(R.id.vaddress);
                linearTrack = itemView.findViewById(R.id.linearTrack);

            }
        }
    }
}
