package com.example.dell.kickbang.Activity;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.kickbang.MainActivity;
import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;

import java.util.concurrent.ThreadPoolExecutor;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
	Presenter presenter;
	private TextView uname_text ;
	private TextView pwd_text;
	private EditText name_edit;
	private EditText pwd_edit;
	private Button login_btn;
	private Button regist_btn;

	private int REQUESTCODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		presenter = new Presenter();
		initView();
	}

	private void initView() {
		name_edit = findViewById(R.id.uname_editview);
		pwd_edit = findViewById(R.id.upwd_editview);
		login_btn = findViewById(R.id.login_btn);
		regist_btn = findViewById(R.id.regist_btn);
		login_btn.setOnClickListener(this);
		regist_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.login_btn:
				new Thread(new Runnable() {
					@Override
					public void run() {
						Looper.prepare();
						String code = presenter.login(name_edit.getText().toString(),pwd_edit.getText().toString());
						if (code.equals("1")){
							Intent intent = new Intent(LoginActivity.this, MainActivity.class);
							startActivity(intent);
							finish();
						}else {
							Toast.makeText(getBaseContext(),"用户名不存在或密码错误！！", Toast.LENGTH_SHORT).show();
						}
					}
				}).start();
				break;
			case R.id.regist_btn:
				Intent intent = new Intent(LoginActivity.this,RegistActivity.class);
				startActivityForResult(intent,REQUESTCODE);
				break;
			default:
				break;
		}
	}
}
