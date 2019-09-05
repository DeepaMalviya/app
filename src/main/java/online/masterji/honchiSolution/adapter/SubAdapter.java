package online.masterji.honchiSolution.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.Recycler.SubActivity;
import online.masterji.honchiSolution.domain.Category;
import online.masterji.honchiSolution.domain.Product;

import static online.masterji.honchiSolution.util.Utility.splitCamelCase;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.MyViewHolder> {
    private static final String TAG = "SubAdapter";
    Context mContext;
    int sizee;
    RequestOptions requestOptions;
    private List<Product> moviesList;
    private List<Category> categoryList;
    LinearLayout layout, linearSub;
 /*   public SubAdapter(SubActivity subActivity, ArrayList<Item> items) {
        this.moviesList = items;
        this.mContext = subActivity;

    }*/


    public SubAdapter(SubActivity subActivity, List<Product> dataList, int size) {
        this.moviesList = dataList;
        this.mContext = subActivity;
        this.sizee = size;
    }

   /* public SubAdapter(SubActivity subActivity, List<Category> dataList, int size) {
        this.categoryList = dataList;
        this.mContext = subActivity;
        this.sizee = size;
    }*/

    public SubAdapter(SubActivity subActivity, List<Category> categoryList) {
        this.categoryList = categoryList;
        this.mContext = subActivity;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, tvNumber, genre;
        ImageView imag4, imag1, imag2, imag3;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.titlee);
            imag1 = view.findViewById(R.id.imag1);
            imag2 = view.findViewById(R.id.imag2);
            imag3 = view.findViewById(R.id.imag3);
            imag4 = view.findViewById(R.id.imag4);
            layout = view.findViewById(R.id.layout);
            linearSub = view.findViewById(R.id.linearSub);
            tvNumber = view.findViewById(R.id.tvNumber);

        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_sub_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Product movie = moviesList.get(position);
        Category movie = categoryList.get(position);
        holder.title.setText(splitCamelCase(movie.getId()));
       /* for (String w : movie.getId().split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
            System.out.println(w);
        }*/


        //  holder.tvNumber.setText("More");
        /*holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SubSubActivity.class);
                intent.putExtra("category", "Male Category");
                intent.putExtra("product", moviesList.get(position).getTitle());
                intent.putExtra("productId", moviesList.get(position).getId());
                mContext.startActivity(intent);
            }
        });*/

        requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);


        Glide.with(mContext)
                .load(movie.getData().getPhoto())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_placeholder)
                .apply(requestOptions).into(holder.imag1);


        Glide.with(mContext)
                .load(movie.getData().getPhoto1())
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_placeholder)
                .apply(requestOptions).into(holder.imag2);

        Glide.with(mContext)
                .load(movie.getData().getPhoto2())
                .centerInside()
                .placeholder(R.drawable.ic_image_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(requestOptions).into(holder.imag3);



        /*  Glide.with(mContext)
                .asBitmap()
                .load(movie.getPhoto())
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.imag1.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });*/
       /* Glide.with(mContext)
                .asBitmap()
                .load(movie.getPhoto1())
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.imag2.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
        Glide.with(mContext)
                .asBitmap()
                .load(movie.getPhoto2())

                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.imag3.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });*/

/*

        Picasso.get()
                .load(movie.getPhoto())
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .into(holder.imag1);
        Picasso.get()
                .load(movie.getPhoto1())
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .into(holder.imag2);

        Picasso.get()
                .load((movie.getPhoto2()))
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .into(holder.imag3);
*/

        if (!TextUtils.isEmpty(movie.getData().getPhoto3())) {

            ConvertUriToDrawable(movie.getData().getPhoto3(), holder.imag4);

        }

        /*holder.tvNumber.setText(" + " + SubActivity.size);
        try {
            if (sizee == 0) {

                int size = Integer.parseInt(String.valueOf(moviesList.size()));
                Log.e(TAG, "onBindViewHolder: else" + size);
                holder.tvNumber.setText(" + " + size);
            } else if (sizee > 4) {

                int size = sizee - 4;
                holder.tvNumber.setText(" + " + size);
            } else if (sizee < 4) {

                holder.tvNumber.setText(" + " + sizee);
            } else {
                int size = Integer.parseInt(String.valueOf(moviesList.size() - 4));
                Log.e(TAG, "onBindViewHolder: else" + size);
                holder.tvNumber.setText(" + " + size);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }*/

        /* else {
            int size = Integer.parseInt(String.valueOf(moviesList.size() - 4));
            Log.e(TAG, "onBindViewHolder: else" + size);
            holder.tvNumber.setText(" + " + size);
        }*/
    }

    private void ConvertUriToDrawable(final String photo3, final ImageView imag4) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bitmap myImage = getBitmapFromURL(photo3);
        Drawable dr = new BitmapDrawable(myImage);
        linearSub.setBackgroundDrawable(dr);


    }

    public Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}