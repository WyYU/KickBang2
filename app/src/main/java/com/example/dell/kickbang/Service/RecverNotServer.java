package com.example.dell.kickbang.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;


/**
 * Created by dell on 2019/4/1 0001.
 */

public class RecverNotServer extends Service {
	private final String TAG = "RecverNotServer";
	Binder binder;

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
