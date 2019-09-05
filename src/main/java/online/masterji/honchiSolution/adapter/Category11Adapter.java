package online.masterji.honchiSolution.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.domain.Images;


public class Category11Adapter extends BaseAdapter {
    private Context mContext;
    private List<Images> dataList;


    // Constructor
    public Category11Adapter(Context c, List<Images> dataList) {
        mContext = c;
        this.dataList = dataList;
    }

    public int getCount() {
        return dataList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.category1_item, null);
        }
        ImageView ivPhoto = convertView.findViewById(R.id.ivPhoto);
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);

        Images data = dataList.get(position);
        tvTitle.setText(data.getTitle());

        Glide.with(mContext)
                .load(data.getPhoto())
                .centerInside()
                .into(ivPhoto);


        return convertView;
    }

}
