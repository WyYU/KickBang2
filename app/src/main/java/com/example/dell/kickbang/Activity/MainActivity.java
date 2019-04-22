package com.example.dell.kickbang.Activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.dell.kickbang.Adapter.TabAdapter;
import com.example.dell.kickbang.Fragment.MainFragment;
import com.example.dell.kickbang.Fragment.MyInfoFragment;
import com.example.dell.kickbang.Fragment.TeamFragment;
import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Resours.Resource;
import com.example.dell.kickbang.Service.MyService;
import com.example.dell.kickbang.Service.RecverNotServer;
import com.example.dell.kickbang.Utils.HttpUtils;
import com.example.dell.kickbang.Utils.PreferencesFactory;
import com.example.dell.kickbang.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private static String Tag = "MainActivity";
	Resource res;
	Presenter presenter;
	HttpUtils httpUtils;
	Utils utils;
	private RecverNotServer recverNotServer;
	private TabLayout tabLayout;
	private ViewPager viewPager;
	private List<android.support.v4.app.Fragment> list;
	private NotiReciver notiReciver;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		presenter = new Presenter(this);
		utils = Utils.getInstance();
		res = Resource.getInstance();
		httpUtils = HttpUtils.getHttpUtils();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		preferences = PreferencesFactory.getInstance(this);
		editor = preferences.edit();
		//prepareData();
		initService();
		initView();
		initEvent();
	}

	private void initService() {
		Log.e(Tag,"Bind Service");
		Intent intent = getIntent();
		User user = (User) intent.getSerializableExtra("User");
		Log.e("AAAAAAAAAAAAAAAAAAAAAAAA", String.valueOf(user.getTid()));
		Intent notifiService = new Intent(MainActivity.this,MyService.class);
		notifiService.putExtra("User",user);
		notifiService.putExtra("AA","sadsa");
		startService(notifiService);
		bindService(notifiService,serviceConnection,BIND_AUTO_CREATE);
		notiReciver = new NotiReciver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.example.dell.kickbang.NotiReciver");
		this.registerReceiver(notiReciver,intentFilter);
	}

	private void initView() {
		initTabList();
		viewPager = findViewById(R.id.view_pager);
		tabLayout = findViewById(R.id.tab_layout);
		tabLayout.setTabMode(TabLayout.MODE_FIXED);
		TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
		adapter.setData(list);
		viewPager.setAdapter(adapter);
		tabLayout.setupWithViewPager(viewPager);
		tabLayout.getTabAt(0).setText("主页").setIcon(R.mipmap.ic_explore_white_48dp);
		tabLayout.getTabAt(1).setText("球队").setIcon(R.mipmap.ic_people_white_48dp);
		tabLayout.getTabAt(2).setText("我的").setIcon(R.mipmap.ic_account_circle_white_48dp);
		tabLayout.setSelectedTabIndicatorColor(Color.BLACK);
	}

	private void initEvent() {

	}

	private void initTabList() {
		list = new ArrayList<>();
		list.add(new MainFragment());
		list.add(new TeamFragment());
		list.add(new MyInfoFragment());
	}

	ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MyService.Mybinder mybinder= (MyService.Mybinder) service;
			((MyService.Mybinder) service).updataNoti(String.valueOf(14));
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	};

	class NotiReciver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			String count = bundle.getString("count");
			Log.e("Receiver count", String.valueOf(count));
			ImageView imageView = findViewById(R.id.Notifi_num_icon);
			//writenewestnid(count);
			switch (count) {
				case "0" :imageView.setVisibility(View.INVISIBLE);
					imageView.refreshDrawableState();
				break;

				case "1" :imageView.setVisibility(View.INVISIBLE);
					imageView.refreshDrawableState();
					break;

				case "2" :imageView.setImageResource(R.mipmap.ic_filter_1_white_48dp);
					imageView.setVisibility(View.VISIBLE);
					imageView.refreshDrawableState();
					break;
				case "3" :imageView.setImageResource(R.mipmap.ic_filter_2_white_48dp);
					imageView.setVisibility(View.VISIBLE);
					imageView.refreshDrawableState();
					break;
				case "4" :imageView.setImageResource(R.mipmap.ic_filter_3_white_48dp);
					imageView.setVisibility(View.VISIBLE);
					imageView.refreshDrawableState();
					break;
				case "5" :imageView.setImageResource(R.mipmap.ic_filter_4_white_48dp);
					imageView.setVisibility(View.VISIBLE);
					imageView.refreshDrawableState();
					break;
				case "6" :imageView.setImageResource(R.mipmap.ic_filter_5_white_48dp);
					imageView.setVisibility(View.VISIBLE);
					imageView.refreshDrawableState();
				break;
				case "7" :imageView.setImageResource(R.mipmap.ic_filter_6_white_48dp);
					imageView.setVisibility(View.VISIBLE);
					imageView.refreshDrawableState();
				case "8" :imageView.setImageResource(R.mipmap.ic_filter_7_white_48dp);
					imageView.setVisibility(View.VISIBLE);
					imageView.refreshDrawableState();
					break;
				default:
					imageView.setImageResource(R.mipmap.ic_filter_9_plus_white_48dp);
					imageView.refreshDrawableState();
					break;
			}
		}
	}

	private void writenewestnid(String count) {
		editor.putString("lastnid",count);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode){
			case 4:
				ImageView imageView = findViewById(R.id.Notifi_num_icon);
				imageView.setVisibility(View.INVISIBLE);
		}
	}
}
