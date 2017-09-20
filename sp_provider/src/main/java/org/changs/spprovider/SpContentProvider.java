package org.changs.spprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.changs.spprovider.debug.AndroidInjection;

import java.util.Map;
import java.util.Set;

/**
 * Created by yincs on 2017/9/18.
 */

public class SpContentProvider extends ContentProvider {
    private static final String TAG = "MyApp";
    //    @Inject 使用dagger时可以使用Inject
    public SharedPreferences sharedPreferences;

    @Override
    public boolean onCreate() {
        AndroidInjection.inject(this);
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.item/sharedPreferences";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        throw new UnsupportedOperationException("不支持该操作");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("不支持该操作");
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (projection == null || projection.length == 0) return null;
        Object[] values = new Object[projection.length];
        Map<String, ?> all = sharedPreferences.getAll();
        for (int i = 0; i < projection.length; i++) {
            values[i] = String.valueOf(all.get(projection[i]));
        }
        MatrixCursor matrixCursor = new MatrixCursor(projection);
        matrixCursor.addRow(values);
        return matrixCursor;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (values == null) return -1;
        Set<String> keys = values.keySet();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (String key : keys) {
            put(editor, key, values.get(key));
        }
        editor.apply();
        return keys.size();
    }

    private void put(SharedPreferences.Editor editor, String key, Object value) {
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            throw new IllegalArgumentException("不支持的类型");
        }
    }
}
