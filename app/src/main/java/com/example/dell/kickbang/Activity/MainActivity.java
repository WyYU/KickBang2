package com.example.dell.kickbang.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.dell.kickbang.Model.Team;
import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Resours.Resource;
import com.example.dell.kickbang.Utils.HttpUtils;
import com.example.dell.kickbang.Utils.MyClient;
import com.example.dell.kickbang.Utils.ThreadPoolService;
import com.example.dell.kickbang.Utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
	private static String Tag = "MainActivity" ;
	Resource res;
	Presenter presenter;
	HttpUtils httpUtils;
	Utils utils;
	User user;
	List<User> teammate;
	Team team;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		presenter = new Presenter();
		utils = Utils.getInstance();
		res = new Resource();
		httpUtils = HttpUtils.getHttpUtils();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		prepareData();
	}
	private void prepareData() {
		Intent intent = getIntent();
		final String uname = intent.getStringExtra("username");
		try {
			user = presenter.queryUser(uname);
			team = presenter.queryteam(String.valueOf(user.getTid()));
			if (user.getTid()!=26){
				teammate = presenter.queryteamplayer(String.valueOf(user.getTid()));
				//Log.e(Tag,teammate.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			utils.showNormalDialog(MainActivity.this,"数据初始化错误请检查网络！");
		}

	}
}
