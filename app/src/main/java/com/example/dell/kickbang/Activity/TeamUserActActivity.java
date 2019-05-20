package com.example.dell.kickbang.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Resours.Resource;
import com.example.dell.kickbang.Utils.Utils;
import com.szysky.customize.siv.SImageView;

public class TeamUserActActivity extends AppCompatActivity implements View.OnClickListener {
	Boolean ActivityResult =false;
	User user ;
	SImageView headicon;
	TextView username;
	TextView teamname;
	TextView goaltextview;
	TextView asstextview;
	TextView posview;
	TextView lv;
	FloatingActionButton delfab;
	FloatingActionButton editfab;
	FloatingActionButton addfab;
	Integer loginuserlv;
	Integer loginusertid;
	Integer loginuserid;
	Utils utils;
	Presenter presenter;
	private Resource resource;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_user_act);
		utils = Utils.getInstance();
		presenter = new Presenter(TeamUserActActivity.this);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		Intent intent = getIntent();
		user = (User) intent.getSerializableExtra("User");
		loginuserlv = Integer.valueOf(preferences.getString("userlv",null));
		loginusertid = Integer.valueOf(preferences.getString("usertid",null));
		loginuserid = Integer.valueOf(preferences.getString("useruid",null));
		resource = Resource.getInstance();
		initView();
		inputdata();
		initEvent();
		Log.e("",loginuserlv.toString());
	}

	private void initEvent() {
		delfab.setOnClickListener(this);
		addfab.setOnClickListener(this);
		editfab.setOnClickListener(this);
	}

	private void initView() {
		headicon = findViewById(R.id.player_act_image);
		username = findViewById(R.id.u_uname_textview);
		teamname = findViewById(R.id.u_uteam_textview);
		goaltextview = findViewById(R.id.u_goal_textview);
		asstextview = findViewById(R.id.u_ass_textview);
		posview = findViewById(R.id.u_pos_textview);
		lv = findViewById(R.id.u_lv_textview);
		delfab = findViewById(R.id.delfab);
		editfab = findViewById(R.id.edifab);
		addfab = findViewById(R.id.addteamfab);
		initfab();
	}

	private void initfab() {
		Log.e("ARAR", String.valueOf(user.getId())+"loginid "+loginuserid );
		if (user.getTid().equals(loginusertid)){
			addfab.setVisibility(View.INVISIBLE);
		}
		if (loginuserlv.equals(5)&&loginuserlv>user.getLevel()){
			delfab.setVisibility(View.VISIBLE);
			editfab.setVisibility(View.VISIBLE);
		} if (loginuserlv.equals(4)&&loginuserlv>=user.getLevel()) {
			delfab.setVisibility(View.INVISIBLE);
			editfab.setVisibility(View.VISIBLE);
		} if (loginuserlv<user.getLevel()) {
			delfab.setVisibility(View.INVISIBLE);
			editfab.setVisibility(View.INVISIBLE);
		}
		if (!user.getTid().equals(loginusertid)){
			editfab.setVisibility(View.INVISIBLE);
			delfab.setVisibility(View.INVISIBLE);
		}
		if (user.getId()==loginuserid){
			addfab.setVisibility(View.INVISIBLE);
			delfab.setVisibility(View.INVISIBLE);
		}

	}

	private void inputdata() {
		headicon.setImageUrls(Resource.getInstance().LOCALOHST+user.getImagepatch());
		username.setText("用户 "+user.getUsername());
		teamname.setText("球队 "+user.getTid());
		goaltextview.setText("进球 "+user.getGoal());
		asstextview.setText("助攻 "+user.getAssisting());
		posview.setText("位置 "+user.getPosition());
		lv.setText("球队角色"+user.getLevel());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.delfab:
				android.support.v7.app.AlertDialog.Builder a = utils.showDelFromTeamDialog(this,"是否将"+user.getUsername()+"从队伍中移除？");
				a.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.e("da","remove");
						String res = presenter.removeuser(user.getId());
						ActivityResult = true;
						Intent intent = getIntent();
						intent.putExtra("result",true);
						setResult(3,intent);
						finish();
					}
				});
				a.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				a.show();
				break;
			case R.id.addteamfab:
				int tid = user.getTid();
				if (tid!=resource.NULL_TEAM_CODE){
					utils.showNormalDialog(this,"该用户已经加入其他球队...");
				}else {
					AlertDialog.Builder alertDialog = utils.showchooswDialog(this,"确认该用户加入您的球队?");
					alertDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							presenter.jointeam(String.valueOf(user.getId()),String.valueOf(loginusertid));
							Intent intent = getIntent();
							setResult(7,intent);
					}
					});
					alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
					alertDialog.show();
				}
				break;
		}
	}

	@Override
	public void onBackPressed() {
		Log.e("da","backed");
		Intent intent = getIntent();
		intent.putExtra("result",true);
		setResult(3,intent);
		super.onBackPressed();
	}
}
