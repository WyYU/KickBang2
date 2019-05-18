package com.example.dell.kickbang.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dell.kickbang.Model.Team;
import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Resours.Resource;
import com.example.dell.kickbang.Utils.PreferencesFactory;
import com.example.dell.kickbang.Utils.Utils;
import com.szysky.customize.siv.SImageView;

import java.util.concurrent.ExecutionException;

public class TeamMsgActivity extends AppCompatActivity implements View.OnClickListener {
	private String TAG = "TeamMsgActivity";
	private Team team ;
	private SImageView teampic;
	private TextView teamnametext;
	private TextView cdatatext;
	private TextView tcolortext;
	private TextView tintrotext;
	private Presenter presenter;

	private String tid ;
	private String uid ;
	private String ulv ;

	private SharedPreferences preferences;
	private Resource resource;
	private FloatingActionButton editbutton;
	private FloatingActionButton delbutton;
	private FloatingActionButton adduserbutton;

	private Utils utils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_msg);
		init();
		initData();
		initView();
		initEvent();
	}

	private void initEvent() {
		editbutton.setOnClickListener(this);
		delbutton.setOnClickListener(this);
		adduserbutton.setOnClickListener(this);
	}

	private void init() {
		resource = Resource.getInstance();
		presenter = new Presenter(this);
		utils = Utils.getInstance();
		preferences = PreferencesFactory.getInstance(this);
		tid = preferences.getString("usertid","14");
		ulv = preferences.getString("userlv","0");
		Log.e(TAG,"ulv " +ulv);
	}

	private void initView() {
		teampic = findViewById(R.id.team_icon_image);
		teamnametext = findViewById(R.id.t_tname_textview);
		cdatatext = findViewById(R.id.t_tcreatedata_textview);
		tcolortext = findViewById(R.id.t_color_textview);
		tintrotext = findViewById(R.id.t_tintrod_textview);
		editbutton = findViewById(R.id.t_edifab);
		delbutton = findViewById(R.id.t_delfab);
		adduserbutton = findViewById(R.id.t_addteamfab);

		teamnametext.setText("球队名  "+team.getTname());
		cdatatext.setText("创建时间  "+team.getCreateTime());
		tintrotext.setText("球队简介  "+team.getIntroduce());
		teampic.setImageUrls(resource.LOCALOHST+team.getIconpath());
	}

	private void initData() {
		try {
			team = presenter.queryteam(tid);
			Log.e(TAG,"team " +team.getTname());
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.t_edifab:
				Log.e(TAG,"click edit");
				if (Integer.parseInt(ulv)<4) {
					utils.showNormalDialog(TeamMsgActivity.this,"您的权限不够！请联系管理员");
				}
				break;
			case R.id.t_addteamfab:
				if (Integer.parseInt(ulv)<4) {
					utils.showNormalDialog(TeamMsgActivity.this,"您的权限不够！请联系管理员");
				}
				break;
			case R.id.t_delfab:
				if (Integer.parseInt(ulv)<4) {
					utils.showNormalDialog(TeamMsgActivity.this,"您的权限不够！请联系管理员");
				}
				break;
			default:
				break;
		}
	}
}
