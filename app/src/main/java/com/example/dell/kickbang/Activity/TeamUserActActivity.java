package com.example.dell.kickbang.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.R;

public class TeamUserActActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_user_act);
		Intent intent = getIntent();
		User user = (User) intent.getSerializableExtra("User");
		Log.e("",user.toString());
	}
}
