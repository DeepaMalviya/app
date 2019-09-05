package online.masterji.honchiSolution.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;



import java.util.Objects;

import online.masterji.honchiSolution.R;

public class WaitDialog extends Dialog {

    private static WaitDialog theWaitDialog = null;
    private TextView mTxtMessage;

    public WaitDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_loader);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        //mTxtMessage = this.findViewById(R.id.txt_message);

        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    public void setMessage(int resId) {
        mTxtMessage.setText(resId);
    }

    public void setMessage(String message) {
        mTxtMessage.setText(message);
    }

    public static void showDialog(Context context) {
        if (theWaitDialog != null) {
            theWaitDialog.dismiss();
        }
        theWaitDialog = new WaitDialog(context);
        theWaitDialog.setCancelable(false);
        theWaitDialog.show();
    }

    public static void hideDialog() {
        if (theWaitDialog == null)
            return;
        theWaitDialog.dismiss();
    }
}
