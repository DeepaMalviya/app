/*
package online.masterji.honchiSolution.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;
import online.masterji.honchiSolution.R;

public class NavActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "NavActivity";
    LinearLayout layoutHome, layoutSHare, layoutpayment, layoutMyOrder, layoutabout, layouthelp;
    CircleImageView civ;
    ImageView ivClosee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        ivClosee = findViewById(R.id.ivClosee);
        civ = findViewById(R.id.civ);
        layoutabout = findViewById(R.id.layoutabout);
        layoutHome = findViewById(R.id.layoutHome);
        layoutSHare = findViewById(R.id.layoutSHare);
        layoutpayment = findViewById(R.id.layoutpayment);
        layoutMyOrder = findViewById(R.id.layoutMyOrder);
        layouthelp = findViewById(R.id.layouthelp);

        layoutabout.setOnClickListener(this);
        layoutHome.setOnClickListener(this);
        layoutSHare.setOnClickListener(this);
        layoutpayment.setOnClickListener(this);
        layoutMyOrder.setOnClickListener(this);
        layouthelp.setOnClickListener(this);
        civ.setOnClickListener(this);
        ivClosee.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivClosee:
                finish();
                break;
            case R.id.civ:
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(this, ProfileActivity.class));
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
                break;
            case R.id.layoutabout:
                startActivity(new Intent(this, AboutUsActivity.class));

                break;
            case R.id.layoutHome:
                startActivity(new Intent(this, HomeActivity.class));

                break;
            case R.id.layoutSHare:
                shareMasterji();
                break;
            case R.id.layoutpayment:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://imjo.in/taNnZD")));

                break;
            case R.id.layoutMyOrder:

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(this, MyOrderActivity.class));
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
                break;
            case R.id.layouthelp:
                callToHelpCenter();
                break;
        }
    }

    private void shareMasterji() {
        try {
            String text = "MasterJi is custom crafted, bespoke tailoring application intended to offer quality sewed pieces of clothing, dress modifications, with guaranteed bother free fitting at moderate costs with free get and conveyance administrations."
                    + "\nDownload Android app now "
                    + "\nhttps://play.google.com/store/apps/details?id=online.masterji.honchi&hl=en";

            Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(),
                    BitmapFactory.decodeResource(getResources(), R.drawable.masterji_logo_for_share), null, null));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.setType("image/*");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share images..."));


        } catch (Exception e) {
            Log.e(TAG, "Can not Share ", e);
        }
    }

    private void callToHelpCenter() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + "+91-9902127313"));
        startActivity(intent);
    }
}
*/
