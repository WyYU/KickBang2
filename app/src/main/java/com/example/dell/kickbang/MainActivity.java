package com.example.dell.kickbang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.dell.kickbang.Resours.Resource;
import com.example.dell.kickbang.Utils.HttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
	Resource res;
	HttpUtils httpUtils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		res = new Resource();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}
}
