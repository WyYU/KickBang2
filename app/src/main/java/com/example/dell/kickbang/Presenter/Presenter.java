package com.example.dell.kickbang.Presenter;

import android.util.Log;

import com.example.dell.kickbang.Resours.Resource;
import com.example.dell.kickbang.Utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dell on 2019/3/14 0014.
 */

public class Presenter {
	private String TAG = "Presenter";
	Resource resource;
	HttpUtils httpUtils;
	public Presenter(){
		resource = new Resource();
		httpUtils = new HttpUtils();
	}
	public String login(String uname,String pwd){
		OkHttpClient client = new OkHttpClient();
		String loginurl = httpUtils.getLoginurl(uname,pwd);
		String res = "0";
		Request request = new Request.Builder().url(loginurl).build();
		try {
			Response response = client.newCall(request).execute();
			String result = response.body().string();
			Log.e("TAG",result);
			JSONObject jsonObject = new JSONObject(result);
			res = jsonObject.getString("result");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return res;
	}
}
