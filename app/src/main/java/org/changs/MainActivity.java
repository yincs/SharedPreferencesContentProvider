package org.changs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.changs.spprovider.debug.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidInjection.get().getSharedPreferences(this)
                .edit()
                .putString("key1", "测试数据")
                .putBoolean("key2", true)
                .apply();
    }
}
