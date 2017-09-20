# SharedPreferencesContentProvider
封装的跨进程的SharedPreferences。用android原生的SharedPreferences和ContentProvider实现。

sp_provider:核心库，只有两个类，实际使用时可以直接复制过去
app：要共享配置的app
app2：跨进程使用实例


    /**
     * 跨进程写例子
     */
    public void white(View view) {
        Uri uri = Uri.parse("content://org.changs.provider.SpContentProvider");
        new SpProvider(this, uri)
                .edit()
                .putString("key1", "testSpProviderEdit")
                .putInt("key3", 1314)
                .apply();
    }
    
    
    
     /**
     * 跨进程读例子
     */
    public void read(View view) {
        SpProvider spProvider = new SpProvider(this, Uri.parse("content://org.changs.provider.SpContentProvider"),
                "key1", "key2", "key3");
        String v1 = spProvider.getString("key1", null);
        AndroidInjection.log("v1 = " + v1);
        boolean v2 = spProvider.getBoolean("key2", false);
        AndroidInjection.log("v2 = " + v2);
        int v3 = spProvider.getInt("key3", 0);
        AndroidInjection.log("v3 = " + v3);
    }

    
