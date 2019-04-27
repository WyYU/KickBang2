package com.example.dell.kickbang.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dell.kickbang.Adapter.NotifiAdapter;
import com.example.dell.kickbang.Model.Notification;
import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Utils.PreferencesFactory;
import com.example.dell.kickbang.Utils.Utils;

import java.util.Arrays;
import java.util.List;

public class TeamDynamicActivity extends AppCompatActivity implements View.OnClickListener {
	private String Tag = "TeamDynamicActivity";
	private List<Notification> notificationList;
	private String userlv;
	private String lastid;
	private String tid ;
	private Presenter presenter;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private RecyclerView recyclerView;
	private NotifiAdapter adapter;
	private FloatingActionButton editbutton;
	private Utils utils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_dynamic);
		preferences = PreferencesFactory.getInstance(getApplication());
		editor = preferences.edit();
		utils = Utils.getInstance();
		prepareData();
		initview();
		initEvent();
	}

	private void initEvent() {
		editbutton.setOnClickListener(this);
	}

	private void initview() {
		recyclerView = findViewById(R.id.team_noti_recyclerview);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(linearLayoutManager);
		adapter = new NotifiAdapter(this,notificationList);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
		if ((notificationList!=null)&&notificationList.size()>0) {
			writelastnid(notificationList.get(0).getId());
		}
		editbutton = findViewById(R.id.team_noti_editbtn);
	}

	private void writelastnid(int id) {
		editor.putString("lastnid", String.valueOf(id));
		editor.apply();
		Log.e(Tag,"put num"+id);
	}

	private void prepareData() {
		presenter = new Presenter(this);
		tid = preferences.getString("usertid","0");
		notificationList = presenter.getTeamNoti(tid);
		Log.e(Tag, notificationList.toString());
	}

	@Override
	public void onBackPressed() {
		Intent intent = getIntent();
		Log.e(Tag,intent.toString());
		setResult(4,intent);
		super.onBackPressed();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.team_noti_editbtn:
				String ulv = preferences.getString("userlv","1");
				Log.e(Tag,"ClickAAAA"+ulv);
				if (Integer.parseInt(ulv)<4){
					utils.showDelFromTeamDialog(this,"您的级别低，不能发布新的消息").show();
				}else{
					Intent intent = new Intent(TeamDynamicActivity.this,ReleaseNotiActivity.class);
					startActivityForResult(intent,5);
				}

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode){
			case 5:
				if (requestCode == 5){
					String s = data.getStringExtra("context");
					String res = presenter.releaseNewNoti(tid,s);
					Log.e(Tag,res);
					if (res.equals("null")||res.equals("0")){
						utils.showDelFromTeamDialog(this,"发布失败,请稍后再试").show();
					}
				}
				break;
		}
	}

}
