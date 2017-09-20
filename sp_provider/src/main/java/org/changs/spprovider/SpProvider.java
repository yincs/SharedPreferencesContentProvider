package org.changs.spprovider;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Created by yincs on 2017/9/19.
 */

public class SpProvider implements SharedPreferences {

    private Map<String, String> mMap;
    private Context mContext;
    private Uri mUri;


    public SpProvider(Context context, Uri uri) {
        this.mContext = context;
        this.mUri = uri;
    }

    public SpProvider(Context context, Uri uri, String... keys) {
        this.mContext = context;
        this.mUri = uri;

        readFromContentResolver(keys);
    }

    public void readFromContentResolver(String... keys) {
        if (mMap == null) mMap = new HashMap<>();
        if (mMap.size() != 0) mMap.clear();
        mMap = new HashMap<>();
        if (keys.length == 0) return;
        Cursor cursor = mContext.getContentResolver().query(mUri, keys, null, null, null);
        if (cursor != null) {
            try {
                cursor.moveToFirst();
                for (String key : keys) {
                    mMap.put(key, cursor.getString(cursor.getColumnIndex(key)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        throw new UnsupportedOperationException();
    }

    private void check(String key) {
        if (mMap == null || !mMap.containsKey(key)) {
            readFromContentResolver(key);
        }
    }

    @Override
    public Map<String, ?> getAll() {
        return mMap;
    }

    @Override
    public String getString(String key, @Nullable String defValue) {
        check(key);
        return mMap.get(key);
    }

    @Override
    public int getInt(String key, int defValue) {
        check(key);
        String value = mMap.get(key);
        if (value == null) return defValue;
        return Integer.parseInt(value);
    }

    @Override
    public long getLong(String key, long defValue) {
        check(key);
        String value = mMap.get(key);
        if (value == null) return defValue;
        return Long.parseLong(value);
    }

    @Override
    public float getFloat(String key, float defValue) {
        check(key);
        String value = mMap.get(key);
        if (value == null) return defValue;
        return Float.parseFloat(value);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        check(key);
        String value = mMap.get(key);
        if (value == null) return defValue;
        return Boolean.parseBoolean(value);
    }

    @Override
    public boolean contains(String key) {
        return mMap.containsKey(key);
    }

    public SpProvider refresh() {
        if (mMap == null) mMap = new HashMap<>();
        if (mMap.size() == 0) return this;
        readFromContentResolver((String[]) mMap.keySet().toArray());
        return this;
    }

    @Override
    public Editor edit() {
        return new EditorImpl();
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        throw new UnsupportedOperationException();
    }

    private final class EditorImpl implements Editor {
        private ContentValues mValues = new ContentValues();

        @Override
        public Editor putString(String key, @Nullable String value) {
            mValues.put(key, value);
            return this;
        }

        @Override
        public Editor putStringSet(String key, @Nullable Set<String> values) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Editor putInt(String key, int value) {
            mValues.put(key, value);
            return this;
        }

        @Override
        public Editor putLong(String key, long value) {
            mValues.put(key, value);
            return this;
        }

        @Override
        public Editor putFloat(String key, float value) {
            mValues.put(key, value);
            return this;
        }

        @Override
        public Editor putBoolean(String key, boolean value) {
            mValues.put(key, value);
            return this;
        }

        @Override
        public Editor remove(String key) {
            mValues.remove(key);
            return this;
        }

        @Override
        public Editor clear() {
            mValues.clear();
            return this;
        }

        @Override
        public boolean commit() {
            int update = mContext.getContentResolver().update(mUri, mValues, null, null);
            return update != -1;
        }

        @Override
        public void apply() {
            mContext.getContentResolver().update(mUri, mValues, null, null);
        }
    }

}
