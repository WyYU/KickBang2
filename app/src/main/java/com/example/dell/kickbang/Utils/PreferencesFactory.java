package com.example.dell.kickbang.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by dell on 2019/3/26 0026.
 */

public class PreferencesFactory {
	private static SharedPreferences preferences;
	public static  synchronized SharedPreferences getInstance(Context context){
		if (preferences == null) {
			preferences = PreferenceManager.getDefaultSharedPreferences(context);
		}
		return preferences;
	}
	private PreferencesFactory(){};
}
