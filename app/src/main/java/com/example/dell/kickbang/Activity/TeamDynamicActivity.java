package com.example.dell.kickbang.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.dell.kickbang.Adapter.NotifiAdapter;
import com.example.dell.kickbang.Model.Notification;
import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Utils.PreferencesFactory;

import java.util.Arrays;
import java.util.List;

public class TeamDynamicActivity extends AppCompatActivity {
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_dynamic);
		preferences = PreferencesFactory.getInstance(getApplication());
		editor = preferences.edit();
		prepareData();
		initview();
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
			Intent intent = getIntent();
			setResult(4,intent);
		}

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
}
