package com.example.dell.kickbang;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.dell.kickbang.Model.Team;
import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.Resours.Resource;
import com.example.dell.kickbang.Utils.HttpUtils;
import com.example.dell.kickbang.Utils.MyClient;
import com.example.dell.kickbang.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
	private static String Tag = "MainActivity" ;
	Resource res;
	HttpUtils httpUtils;
	Utils utils ;
	User user;
	Team team;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		utils = Utils.getInstance();
		res = new Resource();
		httpUtils = HttpUtils.getHttpUtils();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		prepareData();
	}

	private void prepareData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Intent intent = getIntent();
				user = quertUser(intent.getStringExtra("username"));
				team = queryteam(String.valueOf(user.getTid()));
				Log.e(Tag,user.toString());
				Log.e(Tag,team.getTname());
			}
		}).start();
	}

	public Team queryteam(String tid) {
		Team team = null;
		String queryTeamUrl = httpUtils.getQueryTeamUrl("id",tid);
		Request teamrequest = new Request.Builder().url(queryTeamUrl).build();
		MyClient client = MyClient.getInstance();
		Call qtcall = client.newCall(teamrequest);
		try {
			Response teamRes = qtcall.execute();
			String res= teamRes.body().string();
			team = utils.jsontoTeam(res);
			if (team ==null) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			utils.showNormalDialog(MainActivity.this,"初始化信息失败，请检查网络");
		}
		return team;
	}

	@NonNull
	public User quertUser(String name) {
		User user = null;
		Response userRes;
		//加载用户信息
		httpUtils = HttpUtils.getHttpUtils();
		MyClient client = MyClient.getInstance();
		String queryuserUrl = httpUtils.getQueryuserUrl("uname", name);
		Request request = new Request.Builder().url(queryuserUrl).build();
		Call call = client.newCall(request);
		try {
			userRes = call.execute();
			String result = userRes.body().string();
			user = utils.jsontoUser(result);
		} catch (IOException e) {
			utils.showNormalDialog(MainActivity.this, "初始化信息失败，请检查网络");
		}
		return user;
	}
}
