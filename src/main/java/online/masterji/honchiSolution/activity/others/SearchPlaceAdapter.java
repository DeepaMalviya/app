package online.masterji.honchiSolution.activity.others;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.domain.Product;

public class SearchPlaceAdapter extends RecyclerView.Adapter<SearchPlaceAdapter.SearchPlaceAdapterViewHolder> implements Filterable {

    Context mCntx;
    public ArrayList<Product> arrayList;
    public ArrayList<Product> arrayListFiltered;

    public SearchPlaceAdapter(Context mCntx, ArrayList<Product> arrayList) {
        this.mCntx = mCntx;
        this.arrayList = arrayList;
        this.arrayListFiltered = new ArrayList<>(arrayList);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public SearchPlaceAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout_search, parent, false);

        SearchPlaceAdapterViewHolder viewHolder = new SearchPlaceAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchPlaceAdapterViewHolder holder, final int position) {
        final Product place = arrayList.get(position);

      /*  holder.txtPlace.setText(arrayList.get(position).getBuilding());

        Picasso.with(mCntx).load(place.getPlaceImg()).into(holder.image);//using picasso to load image

        holder.cardPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mCntx, CardviewSearch.class);
                intent.putExtra("placeId", String.valueOf(place.getPlaceId()));
                intent.putExtra("building", String.valueOf(place.getBuilding()));
                intent.putExtra("street", String.valueOf(place.getStreet()));
                intent.putExtra("imgurl", String.valueOf(place.getPlaceImg()));
                mCntx.startActivity(intent);

            }
        });*/
    }

    @Override
    public Filter getFilter() {
        return null;
    }


    public class SearchPlaceAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txtPlace;
        ImageView image;
        CardView cardPlace;

        public SearchPlaceAdapterViewHolder(View itemView) {
            super(itemView);
          //  txtPlace = (TextView) itemView.findViewById(R.id.txtPlace);
            image = (ImageView) itemView.findViewById(R.id.image);
           // cardPlace = (CardView) itemView.findViewById(R.id.cardPlace);
        }
    }

  /*  public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Place> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(arrayListFiltered);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Place item : arrayListFiltered) {
                    if (item.getBuilding().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };*/
}