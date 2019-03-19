package com.example.dell.kickbang.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Utils.Utils;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
	Presenter presenter;
	Utils utils;
	private EditText name_edit;
	private EditText pwd_edit;
	private Button login_btn;
	private Button regist_btn;
	private CheckBox remwd;
	private CheckBox autologin;
	private int REQUESTCODE = 1;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	public Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					Log.e("login",msg.getData().getString("code"));
					if (msg.getData().getString("code").trim().equals("1")){
						Intent intent = new Intent(LoginActivity.this, MainActivity.class);
						intent.putExtra("username",name_edit.getText().toString());
						startActivity(intent);
						editor = preferences.edit();
						if(remwd.isChecked()){
							editor.putString("username",name_edit.getText().toString().trim());
							editor.putString("pwd",pwd_edit.getText().toString().trim());
							editor.putBoolean("remember_password",true);
							if (autologin.isChecked()){
								editor.putBoolean("autologin",true);
							}else {editor.putBoolean("autologin",false);}
						}else {
							editor.clear();
						}
						editor.apply();
						finish();
					} else {
						utils.showNormalDialog(LoginActivity.this,"用户不存在或密码错误");
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								name_edit.setText("");
								pwd_edit.setText("");
							}
						});
					}
					break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
		initView();
	}

	private void init() {
		presenter = new Presenter();
		utils = Utils.getInstance();
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
	}

	private void initView() {
		name_edit = findViewById(R.id.uname_editview);
		pwd_edit = findViewById(R.id.upwd_editview);
		login_btn = findViewById(R.id.login_btn);
		regist_btn = findViewById(R.id.regist_btn);
		login_btn.setOnClickListener(this);
		regist_btn.setOnClickListener(this);
		remwd = findViewById(R.id.rempwd_cbx);
		autologin = findViewById(R.id.autologin_cbx);
		remwd.setOnClickListener(this);
		autologin.setOnClickListener(this);
		boolean isRemember = preferences.getBoolean("remember_password",false);
		if(isRemember){
			remwd.setChecked(true);
			String name = preferences.getString("username","");
			String pwd = preferences.getString("pwd","");
			name_edit.setText(name);
			pwd_edit.setText(pwd);
			if (preferences.getBoolean("autologin",false)){
				Toast.makeText(LoginActivity.this,"自动登录",Toast.LENGTH_SHORT).show();
				autologin.setChecked(true);
				login();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.login_btn:
				login();
				break;
			case R.id.regist_btn:
				Intent intent = new Intent(LoginActivity.this,RegistActivity.class);
				startActivityForResult(intent,REQUESTCODE);
				break;
			case R.id.rempwd_cbx:
				if (!remwd.isChecked()){
					autologin.setClickable(false);
				} else {autologin.setClickable(true);}
				break;
			case R.id.autologin_cbx:
				if(!remwd.isChecked()){
					Toast.makeText(LoginActivity.this,"自动登录请先勾选记住密码",Toast.LENGTH_SHORT).show();
					autologin.setChecked(false);
				}
				if (autologin.isChecked()){}
				break;
			default:
				break;
		}
	}

	public void login() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();
				String code = presenter.login(name_edit.getText().toString(), pwd_edit.getText().toString());
				Message message = new Message();
				Bundle bundle = new Bundle();
				bundle.putString("code", " " + code);
				message.setData(bundle);
				message.what = 1;
				handler.sendMessage(message);
			}
		}).start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode){
			case 4:
				if(data.getStringExtra("result").equals("1")){
					utils.showNormalDialog(LoginActivity.this,"注册成功！");
					name_edit.setText(data.getStringExtra("username"));
				}
				break;
			default:
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
