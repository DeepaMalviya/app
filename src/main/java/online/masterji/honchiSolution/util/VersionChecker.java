package online.masterji.honchiSolution.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import org.jsoup.Jsoup;

import java.io.IOException;

public class VersionChecker {
    public static boolean checkVersionIsUpdated(Context context) {
        try {
            versionChecker VersionChecker = new versionChecker(context);
            String versionUpdated = VersionChecker.execute().get().toString();
            PackageInfo packageInfo = null;
            String version_name = null;
            try {
                packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//                int version_code = packageInfo.versionCode;
                version_name = packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return !versionUpdated.equals(version_name);
        } catch (Exception e) {
            e.getStackTrace();
            return false;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private static class versionChecker extends AsyncTask<String, String, String> {
        Context context;
        String newVersion;

        versionChecker(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + context.getPackageName() + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .get()
                        .select(".hAyfc .htlgb")
                        .get(7)
                        .ownText();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return newVersion;
        }

    }
}
