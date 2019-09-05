package online.masterji.honchiSolution.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.activity.CategoryActivity;
import online.masterji.honchiSolution.activity.ProductListActivity;
import online.masterji.honchiSolution.domain.Category;
import online.masterji.honchiSolution.domain.Product;

public class Category1Adapter extends BaseAdapter {
    private Context mContext;
    List<Category> dataList;

    List<Product> productList;
    // Constructor
    public Category1Adapter(Context c, List<Product> dataList) {
        mContext = c;
        this.productList = dataList;
    }

    public Category1Adapter(CategoryActivity c, List<Category> categoryList) {
        mContext = c;
        this.dataList = categoryList;
    }



    public int getCount() {
        return 4;
    }

    public Object getItem(int position) {
        return 4;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_category1, null);
        }
        ImageView ivPhoto1 = convertView.findViewById(R.id.ivPhoto);

        TextView tvTitle = convertView.findViewById(R.id.tvTitle);

        Category data = dataList.get(position);
        // tvTitle.setText(data.getTitle());

        Glide.with(mContext)
                .load(!TextUtils.isEmpty(data.getData().getPhoto()) ? data.getData().getPhoto1() : data.getData().getPhoto2())
                .centerInside()
                .placeholder(R.drawable.ic_image_placeholder)
                .into(ivPhoto1);


        return convertView;
    }

}
