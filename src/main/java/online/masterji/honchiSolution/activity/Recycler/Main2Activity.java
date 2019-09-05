package online.masterji.honchiSolution.activity.Recycler;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.MeasurementActivity;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.Item;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";
    private TextView message;
    RecyclerView recyclervieww;
    Item myListData;
    List<Item> listdata = new ArrayList<>();
    List<String> strings = new ArrayList<>();
    List<String> data = new ArrayList<>();
    FirebaseFirestore db;
    DocumentReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        db = FirebaseFirestore.getInstance();
        myListData = new Item();
        init();
        getMeasurementData();

    }

    /**/
    private void getMeasurementData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("Measurment");
        db.collection("Measurment").whereEqualTo("id", Constants.Uid).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                strings.add(document.getId());
                                data.add(String.valueOf(document.getData()));
                            }
                            listdata = task.getResult().toObjects(Item.class);
                            // Collections.reverse(orderList);
                            Log.e(TAG, "onComplete: " + listdata);
                            Log.e(TAG, "onComplete: " + listdata.size());
                            Log.e(TAG, "data: " + data.size());
                        //    Log.e(TAG, "onComplete: " + listdata.get(0).getTitle1());
                            Log.e(TAG, "onComplete: " + data);
                            try {
                                initGridAdapter(listdata, strings, data);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            for (int i = 0; i < listdata.size(); i++) {
                               /* if (listdata.get(i).getMeasurments().get(i).getId().equals(Constants.Uid)) {
                                    //initAdapter(orderList);

                                }*/
                            }


                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                        }
                        //  waitDialog.dismiss();
                    }
                });
    }

    private void initGridAdapter(List<Item> listdata, List<String> strings, List<String> data) {
        MyListAdapter adapter = new MyListAdapter(listdata, strings, data);
        recyclervieww.setHasFixedSize(true);
        recyclervieww.setLayoutManager(new LinearLayoutManager(this));
        recyclervieww.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void init() {
        message = findViewById(R.id.messagee);

        recyclervieww = findViewById(R.id.recyclervieww);
        // MyListAdapter adapter = new MyListAdapter(listdata, strings);
        recyclervieww.setHasFixedSize(true);
        recyclervieww.setLayoutManager(new LinearLayoutManager(this));
        // recyclervieww.setAdapter(adapter);
    }

    public void addFamily(View view) {

        Intent intent = new Intent(Main2Activity.this, MeasurementActivity.class);
        intent.putExtra("addFamily", "addFamily");

        startActivity(intent);
    }

    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
        List<Item> listdata;
        List<String> strings;
        List<String> data;

        // RecyclerView recyclerView;
        public MyListAdapter(List<Item> listdata, List<String> strings, List<String> data) {
            this.listdata = listdata;
            this.strings = strings;
            this.data = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final Item myListData = listdata.get(position);
            holder.textView.setText(strings.get(position));
            holder.textView1.setText(myListData.getTitle1());
            final String name = holder.textView.getText().toString().trim();
            Log.e(TAG, "onBindViewHolder: name" + name);
            // Log.e(TAG, "onBindViewHolder: "+listdata.get(position).getMeasurments().get(position).getId() );
            Log.e(TAG, "onBindViewHolder:============= " + myListData.getTitle1());
            holder.linearM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Main2Activity.this, MeasurementActivity.class);
                    intent.putExtra("String", strings.get(position));
                    intent.putExtra("ArmLength", listdata.get(position).getArmLength());
                    intent.putExtra("Biceps", listdata.get(position).getBiceps());
                    intent.putExtra("Chest", listdata.get(position).getChest());
                    intent.putExtra("Extra", listdata.get(position).getExtra());
                    intent.putExtra("Hips", listdata.get(position).getHips());
                    intent.putExtra("Id", listdata.get(position).getId());
                    intent.putExtra("Inseam", listdata.get(position).getInseam());
                    intent.putExtra("Knee", listdata.get(position).getKnee());
                    intent.putExtra("LengthBottom", listdata.get(position).getLengthBottom());
                    intent.putExtra("LengthTop", listdata.get(position).getLengthTop());
                    intent.putExtra("Mohri", listdata.get(position).getMohri());
                    intent.putExtra("Name", listdata.get(position).getName());
                    intent.putExtra("Neck", listdata.get(position).getNeck());
                    intent.putExtra("Round", listdata.get(position).getRound());
                    intent.putExtra("Shoulder", listdata.get(position).getShoulder());
                    intent.putExtra("SleeveLngth", listdata.get(position).getSleeveLngth());
                    intent.putExtra("WaistBottom", listdata.get(position).getWaistBottom());
                    intent.putExtra("WaistTop", listdata.get(position).getWaistTop());
                    intent.putExtra("Wrist", listdata.get(position).getWrist());
                    intent.putExtra("Title1", listdata.get(position).getTitle1());
                    intent.putExtra("Title", listdata.get(position).getTitle());
                    intent.putExtra("Gender", listdata.get(position).getGender());
                    intent.putExtra("Thigh", listdata.get(position).getThigh());
                    intent.putExtra("name", name);

                    startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView, textView1;
            LinearLayout linearM;

            public ViewHolder(View itemView) {
                super(itemView);
                this.textView = (TextView) itemView.findViewById(R.id.textView);
                this.textView1 = (TextView) itemView.findViewById(R.id.textView1);
                this.linearM = itemView.findViewById(R.id.linearM);

            }
        }
    }
}
