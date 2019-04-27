package com.example.dell.kickbang.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dell.kickbang.R;

public class ReleaseNotiActivity extends AppCompatActivity implements View.OnClickListener {
	private Button button;
	private EditText editText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_release_noti);
		initView();
		initEvent();
	}

	private void initEvent() {
		button.setOnClickListener(this);
	}

	private void initView() {
		editText = findViewById(R.id.et_comment);
		button = findViewById(R.id.btn_submit);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_submit:
				String s = String.valueOf(editText.getText());
				Intent i = getIntent();
				i.putExtra("context",s);
				setResult(5,i);
				finish();
				break;
		}
	}
}
