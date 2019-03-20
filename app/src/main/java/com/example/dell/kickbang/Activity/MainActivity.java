package com.example.dell.kickbang.Activity;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.dell.kickbang.Adapter.TabAdapter;
import com.example.dell.kickbang.Fragment.MainFragment;
import com.example.dell.kickbang.Fragment.TeamFragment;
import com.example.dell.kickbang.Model.Field;
import com.example.dell.kickbang.Model.Team;
import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Resours.Resource;
import com.example.dell.kickbang.Utils.HttpUtils;
import com.example.dell.kickbang.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private static String Tag = "MainActivity";
	Resource res;
	Presenter presenter;
	HttpUtils httpUtils;
	Utils utils;
	User user;
	List<User> teammate;
	List<Field> fields;
	Team team;
	private TabLayout tabLayout;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private ArrayList<Fragment> fragments = new ArrayList<>();
	private List<android.support.v4.app.Fragment> list;
	private String[] title = new String[]{"a", "b", "c"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		presenter = new Presenter();
		utils = Utils.getInstance();
		res = new Resource();
		httpUtils = HttpUtils.getHttpUtils();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		prepareData();
		initView();
		initEvent();
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
//		tabLayout.addTab(tabLayout.newTab());
//		tabLayout.addTab(tabLayout.newTab());
//		tabLayout.addTab(tabLayout.newTab());
		tabLayout.getTabAt(0).setText("主页").setIcon(R.mipmap.ic_explore_white_48dp);
		tabLayout.getTabAt(1).setText("球队").setIcon(R.mipmap.ic_people_white_48dp);
		tabLayout.getTabAt(2).setText("我的").setIcon(R.mipmap.ic_account_circle_white_48dp);
		tabLayout.setBackgroundColor(Color.GREEN);
		tabLayout.setSelectedTabIndicatorColor(Color.BLACK);
		tabLayout.setSelectedTabIndicatorHeight(tabLayout.getHeight() - 10);
	}

	private void initEvent() {

	}

	private void initTabList() {
		list = new ArrayList<>();
		list.add(new MainFragment());
		list.add(new TeamFragment());
		list.add(new TeamFragment());
	}

	private void prepareData() {
		Intent intent = getIntent();
		final String uname = intent.getStringExtra("username");
		try {
			user = presenter.queryUser(uname);
			team = presenter.queryteam(String.valueOf(user.getTid()));
			fields = presenter.queryField();

			if (user.getTid() != 26) {
				teammate = presenter.queryteamplayer(String.valueOf(user.getTid()));
				//Log.e(Tag,teammate.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			utils.showNormalDialog(MainActivity.this, "数据初始化错误请检查网络！");
		}

	}
}
