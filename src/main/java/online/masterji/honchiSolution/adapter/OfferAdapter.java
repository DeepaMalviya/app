package online.masterji.honchiSolution.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.OffersActivity;
import online.masterji.honchiSolution.domain.Data;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MovieViewHolder> {

    private List<Data> movies;
    private Context context;

    public OfferAdapter(OffersActivity offersActivity, List<Data> images) {
        this.context = offersActivity;
        this.movies = images;
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;
        ImageView ivPhoto;

        public MovieViewHolder(View v) {
            super(v);

            ivPhoto = v.findViewById(R.id.ivOffers);
        }
    }

    public OfferAdapter(List<Data> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.context = context;
    }

    @Override
    public OfferAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_offers, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        //holder.movieTitle.setText(movies.get(position).getTitle());
        Glide.with(context)
                .load(movies.get(position).getPhoto())
                .centerInside()
                .placeholder(R.drawable.ic_image_placeholder)
                .into(holder.ivPhoto);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
