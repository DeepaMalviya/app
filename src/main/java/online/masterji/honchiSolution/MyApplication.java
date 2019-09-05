package online.masterji.honchiSolution;

import android.content.Context;
import android.support.multidex.MultiDexApplication;


public class MyApplication extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);*/
        //MultiDex.install(this); LeakSentry.config = LeakSentry.config.copy(watchFragmentViews = false)
    }
}
