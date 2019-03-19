package com.example.dell.kickbang.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Utils.Utils;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {
	Utils utils;
	Presenter presenter;
	EditText username_edit;
	EditText pwd_edit;
	EditText rpwd_edit;
	Button regist_Btn;
	Button cleanup_Btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		utils = Utils.getInstance();
		presenter = new Presenter();
		initView();
	}

	private void initView() {
		username_edit = getUserEdit();
		pwd_edit = getPwdEdit();
		rpwd_edit = getRPwdEdit();
		regist_Btn = getRegistBtn();
		cleanup_Btn = getCleanBtn();
	}

	public EditText getUserEdit() {
		username_edit = findViewById(R.id.uname_regist_editview);
		return username_edit;
	}

	public EditText getPwdEdit() {
		pwd_edit = findViewById(R.id.upwd_regist_editview);
		return pwd_edit;
	}

	public EditText getRPwdEdit() {
		rpwd_edit = findViewById(R.id.rpwd_regist_editview);
		return rpwd_edit;
	}

	public Button getRegistBtn() {
		regist_Btn = findViewById(R.id.cofrm_regist_btn);
		regist_Btn.setOnClickListener(this);
		return regist_Btn;
	}

	public Button getCleanBtn() {
		cleanup_Btn = findViewById(R.id.cleanup_regist_btn);
		cleanup_Btn.setOnClickListener(this);
		return cleanup_Btn;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.cofrm_regist_btn:
				String pwd = getPwdEdit().getText().toString().trim();
				String rpwd = getRPwdEdit().getText().toString().trim();
				if (username_edit.getText().toString().length()<=0){
					utils.showNormalDialog(RegistActivity.this,"用户名不能为空");
				}
				if (pwd.length()<8||pwd.length()>20) {
					utils.showNormalDialog(RegistActivity.this,"密码长度不得小于8位或大于20位！");
				}
				if (!pwd.equals(rpwd)){
					utils.showNormalDialog(RegistActivity.this,"两次输入密码不同！");
					cleanup();
				}else {
					presenter.regist(username_edit.getText().toString(),pwd);
				}

				break;
			case R.id.cleanup_regist_btn:
				cleanup();
		}
	}

	private void cleanup() {
		getPwdEdit().setText("");
		getRPwdEdit().setText("");
	}
}
