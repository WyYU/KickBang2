package com.example.dell.kickbang.Presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.dell.kickbang.Activity.LoginActivity;
import com.example.dell.kickbang.Model.Field;
import com.example.dell.kickbang.Model.Team;
import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.Resours.Resource;
import com.example.dell.kickbang.Utils.HttpUtils;
import com.example.dell.kickbang.Utils.MyClient;
import com.example.dell.kickbang.Utils.ThreadPoolService;
import com.example.dell.kickbang.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dell on 2019/3/14 0014.
 */

public class Presenter {
	private String TAG = "Presenter";
	HttpUtils httpUtils;
	Utils utils;
	OkHttpClient client;
	Handler handler;
	public String registres;
	private ThreadPoolExecutor threadPoolService;
	private Context context;
	public Presenter(Context context){
		utils = Utils.getInstance();
		httpUtils = HttpUtils.getHttpUtils();
		client = MyClient.getInstance();
	}

	public String  regist(final String uname, final String pwd) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				String url = httpUtils.getRegistUrl(uname,pwd);
				Request request = new Request.Builder().url(url).build();
				Call call = client.newCall(request);
				try {
					Response response = call.execute();
					String body = response.body().string();
					Log.e(TAG,body);
					JSONObject jsonObject = new JSONObject(body);
					String result = jsonObject.getString("result");
					registres = result;
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).start();
		return registres;
	}

	@NonNull
	public User queryUser(final String name) throws ExecutionException, InterruptedException {
		threadPoolService = ThreadPoolService.getInstance();
		Future<User> future = threadPoolService.submit(new Callable<User>() {
			@Override
			public User call() throws Exception {
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
					//utils.showNormalDialog(getBaseContext(), "初始化信息失败，请检查网络");
				}
				return user;
			}
		});
		User user = future.get();
		return user;
	}

	public Team queryteam (final String tid) throws ExecutionException, InterruptedException {
		threadPoolService = ThreadPoolService.getInstance();
		Future<Team> future = threadPoolService.submit(new Callable<Team>() {
			@Override
			public Team call() throws Exception {
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
					utils.showNormalDialog(context,"初始化信息失败，请检查网络");
					return null;
				}
				return team;
			}
		});
		Team team = future.get();
		return team;
	}

	public List<User> queryteamplayer(final String tid){
		threadPoolService = ThreadPoolService.getInstance();
		Future<List<User>> future = threadPoolService.submit(new Callable<List<User>>() {
			@Override
			public List<User> call() throws Exception {
				String queryplayerUrl = httpUtils.getQueryTeamPlayerUrl(tid);
				Request request = new Request.Builder().url(queryplayerUrl).build();
				Call call = client.newCall(request);
				Response response = call.execute();
				String resjson = response.body().string();
				List<User> list1 = utils.jsontoUserlist(resjson);
				return list1;
			}
		});
		try {
			return future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<Field> queryField(){
		threadPoolService = ThreadPoolService.getInstance();
		Future<List<Field>> future = threadPoolService.submit(new Callable<List<Field>>() {
			@Override
			public List<Field> call() throws Exception {
				String qfiledurl = httpUtils.getQueryFieldUrl();
				Request request = new Request.Builder().url(qfiledurl).build();
				Call call = client.newCall(request);
				Response response = call.execute();
				List<Field> list = utils.jsontoField(response.body().string());
				return list;
			}
		});

		try {
			return future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String login(final String username, final String password){ 
		threadPoolService = ThreadPoolService.getInstance();
		Future<String> future = threadPoolService.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				String result;
				try {
					String loginurl = httpUtils.getLoginurl(username, password);
					Request request = new Request.Builder().url(loginurl).build();
					Call call = client.newCall(request);
					Response response = call.execute();
					String resjson = response.body().string();
					JSONObject jsonObject = new JSONObject(resjson);
					result = jsonObject.getString("result");
				} catch (Exception e){
					e.printStackTrace();
					return "0";
				}
				return result;
			}
		});
		try {
			return future.get().toString();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return "0";
	}

	public String removeuser(final int id) {
		threadPoolService = ThreadPoolService.getInstance();
		Future<String> future = threadPoolService.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				String result ;
				try {
					String removeuserurl = httpUtils.getJoinTeamUrl(String.valueOf(id),String.valueOf(26));
					Request request = new Request.Builder().url(removeuserurl).build();
					Call call = client.newCall(request);
					Response response = call.execute();
					String res = response.body().string();
					JSONObject jsonObject = new JSONObject(res);
					result = jsonObject.getString("result");
					Log.e("",removeuserurl);
					return result;
				}catch (Exception e) {
					return "0";
				}
			}
		});
		try {
			return future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return "0";
	}
}
