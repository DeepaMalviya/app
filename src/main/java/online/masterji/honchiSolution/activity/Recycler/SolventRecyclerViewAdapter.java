package online.masterji.honchiSolution.activity.Recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.ProductActivity;
import online.masterji.honchiSolution.domain.Product1;

public class SolventRecyclerViewAdapter extends RecyclerView.Adapter<SolventViewHolders> {
    private static final String TAG = "SolventRecyclerViewAdap";
    private List<Product1> itemList;
    private Context context;
    ArrayList<HashMap<String, String>> contactList;

    public SolventRecyclerViewAdapter(SubSubActivity context, List<Product1> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    public SolventRecyclerViewAdapter(SubSubActivity context, ArrayList<HashMap<String, String>> contactList) {
        this.contactList = contactList;
        this.context = context;
    }

    public SolventRecyclerViewAdapter(ProductActivity productActivity, List<Product1> dataList) {
        this.itemList = dataList;
        this.context = productActivity;
    }


    @Override
    public SolventViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_su_sub, null);
        SolventViewHolders rcv = new SolventViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(SolventViewHolders holder, final int position) {
        Log.e(TAG, "onBindViewHolder: " + itemList.get(position).getTitle());
        try {
            holder.countryName.setText(itemList.get(position).getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // holder.countryPhoto.setImageResource(itemList.get(position).getPhoto());
        Glide.with(context)
                .load(itemList.get(position).getPhoto1())
                .centerInside()
                .placeholder(R.drawable.ic_image_placeholder)
                .into(holder.countryPhoto);
        /*holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("product", itemList.get(position));
                intent.putExtra("contactList", itemList.get(position));
                //intent.putExtra("category", category);
                intent.putExtra("productName", itemList.get(position).getTitle());
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {


        return itemList.size();


    }
}