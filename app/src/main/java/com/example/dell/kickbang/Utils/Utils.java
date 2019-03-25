package com.example.dell.kickbang.Utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.dell.kickbang.Model.Field;
import com.example.dell.kickbang.Model.Team;
import com.example.dell.kickbang.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2019/3/15 0015.
 */

public class Utils {
	private static Utils utils;
	public static synchronized Utils getInstance(){
		if(utils==null){
			utils = new Utils();
		}
		return utils;
	}
	private Utils(){

	}
	public void showNormalDialog(Context context, String msg){
		final AlertDialog.Builder normalDialog =
				new AlertDialog.Builder(context);
		normalDialog.setTitle("Alert");
		normalDialog.setMessage(msg);
		normalDialog.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		// 显示
		normalDialog.show();
	}

	public AlertDialog.Builder showDelFromTeamDialog(Context context, String msg){
		final AlertDialog.Builder normalDialog =
				new AlertDialog.Builder(context);
		normalDialog.setTitle("Alert");
		normalDialog.setMessage(msg);
		return normalDialog;
	}

	public ProgressDialog showProgressDialog(Context context,String msg){
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setMessage(msg);
		return dialog;
	}


	/**
	 * @param json
	 * @return 对应用户类
	 */
	public User jsontoUser(String json){
		User user = new User();
		try {
			JSONObject jsonObject = new JSONObject(json);
			try {
				String result = jsonObject.getString("result");
				if (result.equals("0")){
					return null;
				}
			}catch (Exception e){

			}
			user.setUsername(jsonObject.getString("username"));
			user.setId(Integer.parseInt(jsonObject.getString("id")));
			user.setPosition(jsonObject.getString("pos").toString());
			user.setTid(jsonObject.getInt("team"));
			user.setLevel(jsonObject.getInt("lv"));
			user.setGoal(jsonObject.getInt("goal"));
			user.setAssisting(jsonObject.getInt("ass"));
			user.setNum(jsonObject.getInt("num"));
			user.setImagepatch(jsonObject.getString("imagepath"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

	public Team jsontoTeam(String res) {
		Team team = new Team();
		try {
			JSONObject jsonObject = new JSONObject(res);
			if (jsonObject.getString("result").equals("0")){
				return null;
			}
			team.setTid(jsonObject.getInt("tid"));
			team.setTname(jsonObject.getString("teamname"));
			String data = jsonObject.getString("createtime");
			team.setCreateTime(Date.valueOf(data));
			team.setColorcode(jsonObject.getString("colorcode"));
			team.setIntroduce(jsonObject.getString("introduce"));
			team.setIconpath(jsonObject.getString("imagepath"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
			return team;
	}

	public List<User> jsontoUserlist(String resjson) {
		List<User> list =new ArrayList<>();
		try {
			JSONArray jsonArray = new JSONArray(resjson);
			JSONObject res = jsonArray.getJSONObject(0);
			if(res.getString("result").equals(0)){
				return null;
			}
			JSONArray players = jsonArray.getJSONArray(1);
			for (int i = 0;i<players.length();i++){
				JSONObject jsonObject = players.getJSONObject(i);
				User user = jsontoUser(String.valueOf(jsonObject));
				user.setUsername(jsonObject.getString("username"));
				user.setId(Integer.parseInt(jsonObject.getString("id")));
				user.setPosition(jsonObject.getString("pos").toString());
				user.setTid(jsonObject.getInt("team"));
				user.setLevel(jsonObject.getInt("lv"));
				user.setGoal(jsonObject.getInt("goal"));
				user.setAssisting(jsonObject.getInt("ass"));
				user.setNum(jsonObject.getInt("num"));
				user.setImagepatch(jsonObject.getString("imagepath"));
				list.add(user);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//Log.e("asd", list.toString());
		return list;
	}

	public List<Field> jsontoField(String string) {
		List<Field> list = new ArrayList();
		try {
			JSONArray resultArray = new JSONArray(string);
			JSONObject jsonObject = resultArray.getJSONObject(0);
			if (jsonObject.getString("result").equals("0")){
				return null;
			}
			JSONArray fields = resultArray.getJSONArray(1);
			for(int i = 0; i<fields.length();i++){
				Field field = new Field();
				JSONObject object = fields.getJSONObject(i);
				field.setName(object.getString("fname"));
				field.setAddress(object.getString("address"));
				field.setPrive(object.getDouble("private"));
				field.setRate(object.getString("rate"));
				list.add(field);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}



}
