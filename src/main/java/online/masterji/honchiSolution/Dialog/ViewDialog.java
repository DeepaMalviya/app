package online.masterji.honchiSolution.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import online.masterji.honchiSolution.R;

public class ViewDialog {

    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialoge_permission);

        //dialog.setTitle("Sample");
        final TextView tvGive = (TextView) dialog.findViewById(R.id.tvGive);
        final TextView Tvlater = (TextView) dialog.findViewById(R.id.Tvlater);
        Tvlater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        tvGive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


            }
        });
        dialog.show();


    }
}
