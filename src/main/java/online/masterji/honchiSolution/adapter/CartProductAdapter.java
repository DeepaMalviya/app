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
import online.masterji.honchiSolution.domain.Cart;

public class CartProductAdapter extends BaseAdapter {

    Context mContext;
    List<Cart> mdataset;
    CallBack mCallBack;


    public interface CallBack {
        void removeItem(int position);
    }

    public CartProductAdapter(Context context, List<Cart> list, CallBack callBack) {
        mContext = context;
        mdataset = list;
        mCallBack = callBack;

    }

    @Override
    public int getCount() {
        return mdataset.size();
    }

    @Override
    public Object getItem(int position) {
        return mdataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_cart, null);
        }
        TextView tvtitle = convertView.findViewById(R.id.tvtitle);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        TextView tvDeliveryDescription = convertView.findViewById(R.id.tvDeliveryDescription);
        ImageView ivProductPhoto = convertView.findViewById(R.id.ivProductPhoto);
        ImageView ivProductRemove = convertView.findViewById(R.id.ivProductRemove);

        ivProductRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //removeProduct((int) v.getTag());
                mCallBack.removeItem(position);
            }
        });

        Cart cart = mdataset.get(position);
        tvtitle.setText(cart.getTitle());
        tvPrice.setText(cart.getPrice());
        Glide.with(mContext)
                .load(cart.getPhoto())
                .centerInside()
                .placeholder(R.drawable.ic_image_placeholder)
                .into(ivProductPhoto);

        return convertView;
    }
}
