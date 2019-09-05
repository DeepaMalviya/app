package online.masterji.honchiSolution.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.SearchActivity;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    ArrayList<HashMap<String, String>> contactList;
    Context context;
    List<String> history;


    public SearchAdapter(SearchActivity activity, ArrayList<HashMap<String, String>> contactList) {
        this.contactList = contactList;
        this.context = activity;
    }

    public SearchAdapter(SearchActivity activity, List<String> history) {
        this.history = history;
        this.context = activity;
    }

  /*  public SearchAdapter(SearchActivity activity, ArrayList<String> names) {
        this.names = names;
        this.context = activity;
    }

    public SearchAdapter(SearchActivity activity, List<String> list) {
        this.names = list;
        this.context = activity;
    }*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout_search, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.country_namee.setText(Html.fromHtml("" + contactList.get(position).get("title"), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.country_namee.setText(Html.fromHtml("" + contactList.get(position).get("title")));
        }*/

        if (history != null && history.size() > 0) {
            try {
                holder.country_namee.setText(history.get(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

      /*  Glide.with(context)
                .load(contactList.get(position).get("photo1"))
                .into(holder.country_photoo);*/
    }

    @Override
    public int getItemCount() {
        return 10;
    }


   /* public void filterList(ArrayList<Search> filterdNames) {

        this.contactList = filterdNames;
        notifyDataSetChanged();
    }*/

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView country_namee;
        ImageView country_photoo;

        ViewHolder(View itemView) {
            super(itemView);

            country_namee = (TextView) itemView.findViewById(R.id.country_namee);
            country_photoo = (ImageView) itemView.findViewById(R.id.country_photoo);
        }
    }

}
