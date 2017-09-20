package org.changs.spprovider.debug;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.changs.spprovider.SpContentProvider;

/**
 * Created by yincs on 2017/9/19.
 */


/**
 * 超简单模拟dagger。
 */
public class AndroidInjection {
    private static final AndroidInjection sAndroidInjection = new AndroidInjection();

    private AndroidInjection() {
    }

    public static AndroidInjection get() {
        return sAndroidInjection;
    }

    private SharedPreferences sharedPreferences;

    public SharedPreferences getSharedPreferences(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static void inject(SpContentProvider spContentProvider) {
        spContentProvider.sharedPreferences = get().getSharedPreferences(spContentProvider.getContext());
    }


    public static void log(String msg) {
        Log.d("SpProvider", msg);
    }
}
