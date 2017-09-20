package changs.app2;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.changs.spprovider.SpProvider;
import org.changs.spprovider.debug.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void read(View view) {
        Uri uri = Uri.parse("content://org.changs.provider.SpContentProvider");
        SpProvider spProvider = new SpProvider(this, uri, "key1", "key2", "key3");
        String v1 = spProvider.getString("key1", null);
        AndroidInjection.log("v1 = " + v1);
        boolean v2 = spProvider.getBoolean("key2", false);
        AndroidInjection.log("v2 = " + v2);
        int v3 = spProvider.getInt("key3", 0);
        AndroidInjection.log("v3 = " + v3);
    }

    public void white(View view) {
        Uri uri = Uri.parse("content://org.changs.provider.SpContentProvider");
        new SpProvider(this, uri)
                .edit()
                .putString("key1", "testSpProviderEdit")
                .putInt("key3", 1314)
                .apply();
    }
}
