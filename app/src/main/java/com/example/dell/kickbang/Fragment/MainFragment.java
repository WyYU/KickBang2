package com.example.dell.kickbang.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.kickbang.Activity.MainActivity;
import com.example.dell.kickbang.Model.Field;
import com.example.dell.kickbang.Model.Team;
import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Resours.Resource;
import com.example.dell.kickbang.Utils.HttpUtils;
import com.example.dell.kickbang.Utils.Utils;
import com.szysky.customize.siv.SImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2019/3/20 0020.
 */

public class MainFragment extends Fragment {
	Resource res;
	Presenter presenter;
	HttpUtils httpUtils;
	Utils utils;
	User user;
	List<User> teammate;
	List<Field> fields;
	Team team;
	SImageView imageView;
	TextView username;
	TextView userid;
	TextView teamTextView;
	TextView goalTextView;
	TextView assTextview;
	private TabLayout tabLayout;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private ArrayList<android.app.Fragment> fragments = new ArrayList<>();
	private String[] title = new String[]{"a", "b", "c"};
	private static String Tag = "Fragment";
	public View thisview;
	private List<android.support.v4.app.Fragment> list;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		thisview = inflater.inflate(R.layout.fragment_main, null);
		Log.e(Tag, String.valueOf(thisview));
		username = thisview.findViewById(R.id.main_username_textview);
		userid = thisview.findViewById(R.id.main_uid_texiview);
		teamTextView = thisview.findViewById(R.id.main_teamname_textview);
		goalTextView = thisview.findViewById(R.id.main_goal_textview);
		assTextview = thisview.findViewById(R.id.main_ass_textview);
		username.setText("  用户名："+user.getUsername());
		userid.setText("  id:"+user.getId());
		if (user.getTid()==26){
			teamTextView.setText("无");
		}else {teamTextView.setText(team.getTname());}
		goalTextView.setText(String.valueOf(user.getGoal()));
		assTextview.setText(String.valueOf(user.getAssisting()));
		return thisview;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		presenter = new Presenter();
		utils = Utils.getInstance();
		res = new Resource();
		httpUtils = HttpUtils.getHttpUtils();
		super.onCreate(savedInstanceState);
		prepareData();
	}

	private void prepareData() {
		Intent intent = getActivity().getIntent();
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
			//utils.showNormalDialog(MainActivity.this, "数据初始化错误请检查网络！");
		}

	}
}
