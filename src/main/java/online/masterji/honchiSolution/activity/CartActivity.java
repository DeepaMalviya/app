package online.masterji.honchiSolution.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import online.masterji.honchiSolution.R;
import online.masterji.honchiSolution.adapter.CartProductAdapter;
import online.masterji.honchiSolution.constant.Constants;
import online.masterji.honchiSolution.domain.Cart;
import online.masterji.honchiSolution.util.SnackBarUtil;
import online.masterji.honchiSolution.util.WaitDialog;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CartActivity";

    LinearLayout parentLayout, subParentLayout;
    ListView cartProductList;
    TextView tvPriceItemCount, tvEmptyCart, tvPriceItemAmount, tvDeliveryCharges, tvAmountPayable, tvFinalPriceBottom, tvViewPriceDetail;
    ImageView ivEmptyCart;
    WaitDialog waitDialog;
    CartProductAdapter adapter;
    double price = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();

        waitDialog = new WaitDialog(this);

    }


    private void initView() {

        subParentLayout = findViewById(R.id.subParentLayout);
        ivEmptyCart = findViewById(R.id.ivEmptyCart);
        tvEmptyCart = findViewById(R.id.tvEmptyCart);

        if (Constants.cartList.isEmpty()) {
            subParentLayout.setVisibility(View.GONE);
            ivEmptyCart.setVisibility(View.VISIBLE);
            tvEmptyCart.setVisibility(View.VISIBLE);
            return;
        }

        parentLayout = findViewById(R.id.parentLayout);
        cartProductList = findViewById(R.id.cartProductList);

        tvFinalPriceBottom = findViewById(R.id.tvFinalPriceBottom);
        tvViewPriceDetail = findViewById(R.id.tvViewPriceDetail);

        Button btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(this);


        View footerView = View.inflate(this, R.layout.item_price_detail, null);
        tvPriceItemCount = footerView.findViewById(R.id.tvPriceItemCount);
        tvPriceItemAmount = footerView.findViewById(R.id.tvPriceItemAmount);
        tvDeliveryCharges = footerView.findViewById(R.id.tvDeliveryCharges);
        tvAmountPayable = footerView.findViewById(R.id.tvAmountPayable);

        cartProductList.addFooterView(footerView);

        CartProductAdapter.CallBack callBack = new CartProductAdapter.CallBack() {
            @Override
            public void removeItem(int position) {
                removeProduct(position);
            }
        };

        adapter = new CartProductAdapter(this, Constants.cartList, callBack);
        cartProductList.setAdapter(adapter);


        for (Cart cart : Constants.cartList) {

            try {
                if (cart.getPrice() != null && cart.getPrice().isEmpty()) {
                    price = price + Double.valueOf(cart.getPrice());
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }

        tvPriceItemCount.setText("Price (" + Constants.cartList.size() + " items)");
        tvPriceItemAmount.setText(price + "");
        tvAmountPayable.setText(price + "");
        tvFinalPriceBottom.setText(price + "");


    }


    private void removeProduct(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Remove?")
                /*.setMessage("Are you sure want to remove this product?")*/
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        waitDialog.show();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("MJ_Users").document(Constants.Uid).collection("Cart").document(Constants.cartList.get(position).getId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Log.d(TAG, "DocumentSnapshot successfully deleted!");

                                        price = price - Double.valueOf(Constants.cartList.get(position).getPrice());
                                        tvPriceItemCount.setText("Price (" + Constants.cartList.size() + " items)");
                                        tvPriceItemAmount.setText(price + "");
                                        tvAmountPayable.setText(price + "");
                                        tvFinalPriceBottom.setText(price + "");

                                        Constants.cartList.remove(position);
                                        adapter.notifyDataSetChanged();

                                        waitDialog.dismiss();
                                        SnackBarUtil.showSuccess(CartActivity.this, parentLayout, "Product Removed");

                                        if (Constants.cartList.isEmpty()) {
                                            subParentLayout.setVisibility(View.GONE);
                                            ivEmptyCart.setVisibility(View.VISIBLE);
                                        }

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        waitDialog.dismiss();
                                        Log.w(TAG, "Error deleting document", e);
                                        waitDialog.dismiss();
                                        SnackBarUtil.showError(CartActivity.this, parentLayout, "Product Not Removed");
                                    }
                                });

                    }
                })
                .setNegativeButton("Cancele", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnContinue:
                startActivity(new Intent(this, PlaceOrderActivity.class));
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
