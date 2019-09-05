package online.masterji.honchiSolution.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import online.masterji.honchiSolution.R;


public class SnackBarUtil {

    private static Snackbar snackbar;

    public interface SnackBarActionListner {
        void onInternetRetry();
    }

    public static void dismissSnackbar() {
        try {
            if (snackbar != null)
                snackbar.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Snackbar showSuccess(Context context, View view, String msg) {
        dismissSnackbar();
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.success));
        snackbar.show();
        return snackbar;
    }

    public static Snackbar showError(Context context, View view, String msg) {
        dismissSnackbar();
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.fail));
        snackbar.show();
        return snackbar;
    }

    public static Snackbar showWarning(Context context, View view, String msg) {
        dismissSnackbar();
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.warning));
        snackbar.show();
        return snackbar;
    }

    public static Snackbar showInfo(Context context, View view, String msg, int duration) {
        dismissSnackbar();
        Snackbar snackbar;
        if (duration != -1)
            snackbar = Snackbar.make(view, msg, duration);
        else
            snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.info));
        snackbar.show();
        return snackbar;
    }

    public static Snackbar showTokenWarning(Context context, View view) {
        dismissSnackbar();
        snackbar = Snackbar.make(view, context.getString(R.string.token_expired)
                , Snackbar.LENGTH_INDEFINITE);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.fail));
        snackbar.show();
        return snackbar;
    }

    public static Snackbar showInternetWarning(Context context, View view) {
        dismissSnackbar();
        snackbar = Snackbar.make(view
                , "No Network connection!"
                , Snackbar.LENGTH_INDEFINITE);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.info));
        /*SnackBar Text Customization code*/
        TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_signal_wifi_off, 0, 0, 0);
        textView.setCompoundDrawablePadding(context.getResources().getDimensionPixelOffset(R.dimen._10sdp));
        snackbar.show();
        /*tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setTextSize(R.dimen._20sdp);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
        }*/
        return snackbar;
    }

    public static Snackbar showInternetWarningWithRetry(Context context, View view, final SnackBarActionListner actionListner) {
        dismissSnackbar();
        snackbar = Snackbar.make(view
                , "No Network connection!"
                , Snackbar.LENGTH_INDEFINITE);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.info));
        /*SnackBar Text Customization code*/
        TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_signal_wifi_off, 0, 0, 0);
        textView.setCompoundDrawablePadding(context.getResources().getDimensionPixelOffset(R.dimen._10sdp));

        snackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListner.onInternetRetry();
            }
        });

        snackbar.show();

        return snackbar;
    }

    public static Snackbar showInternetAvailable(Context context, View view) {
        dismissSnackbar();
        Snackbar snackbar = Snackbar.make(view
                , "Internet Available "
                , Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.info));
        /*SnackBar Text Customization code*/
        TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_signal_wifi, 0, 0, 0);
        textView.setCompoundDrawablePadding(context.getResources().getDimensionPixelOffset(R.dimen._10sdp));
        snackbar.show();
        return snackbar;

    }

    public static Snackbar showProgressCircle(Context context, View view, String msg) {
        dismissSnackbar();
        snackbar = Snackbar.make(view
                , msg
                , Snackbar.LENGTH_INDEFINITE);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.info));
        /*SnackBar Text Customization code*/
        TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        ViewGroup viewGroup = (ViewGroup) textView.getParent();
        View view1 = View.inflate(context, R.layout.progressbar_view, null);
        viewGroup.addView(view1, 0);
        snackbar.show();
        return snackbar;
    }


}
