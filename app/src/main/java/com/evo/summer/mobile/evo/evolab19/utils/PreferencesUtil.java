package com.evo.summer.mobile.evo.evolab19.utils;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

public class PreferencesUtil {
    private static final String TAG = "PreferencesUtil";
    private static final String PREF_SORT_QUERY = "sortQuery";
    private static final String PREF_FIRST_OPEN = "isFirstOpen";
    private static final String PREF_SEARCH_QUERY = "searchQuery";
    private static final String PREF_GENERATE_DATA = "generateData";


    public static boolean getSortQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_SORT_QUERY, false);
    }


    public static void setSortQuery(Context context, boolean b) {
//        if b == true ASC else DESC
        Log.v(TAG, String.valueOf(b));

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_SORT_QUERY, b)
                .apply();
    }


    public static boolean getCheckFirstOpen(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_FIRST_OPEN, true);
    }


    public static void setCheckFirstOpen(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_FIRST_OPEN, false)
                .apply();
    }

    public static boolean getGenerateData(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_GENERATE_DATA, false);
    }


    public static void setGenerateData(Context context, boolean b) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_GENERATE_DATA, b)
                .apply();
    }

    public static String getStoreQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SEARCH_QUERY, "");
    }

    public static void setStoredQuery(Context context, String query) {
        Log.v(TAG, query + " context = " + context);

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();
    }
}
