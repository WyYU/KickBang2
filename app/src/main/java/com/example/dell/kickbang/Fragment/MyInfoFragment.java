package com.example.dell.kickbang.Fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.dell.kickbang.Activity.AboutActivity;
import com.example.dell.kickbang.Activity.EditinfoActivity;
import com.example.dell.kickbang.Activity.LoginActivity;
import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Resours.Resource;
import com.szysky.customize.siv.SImageView;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by dell on 2019/3/26 0026.
 */

public class MyInfoFragment extends Fragment {
	private final int EDIT = 2;
	User user ;
	View view;
	SImageView head;
	ImageView imageView;
	ImageView back;
	Resource resource;
	TextView edittext;
	TextView abouttext;
	Button exitButton;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		resource =Resource.getInstance();
		prepareData();
		view = inflater.inflate(R.layout.fragment_myinfo,null);
		initView();
		return view;
	}

	private void prepareData() {
		Intent intent =getActivity().getIntent();
		intent.getSerializableExtra("user");
		user = (User) intent.getSerializableExtra("User");
	}

	private void initView() {
		head = null;
		exitButton = view.findViewById(R.id.exit_btn);
		edittext = view.findViewById(R.id.u_info_edit);
		head = view.findViewById(R.id.h_head);
		head.mUrlLoading.clear();
		back = view.findViewById(R.id.h_back);
		abouttext = view.findViewById(R.id.u_info_about);
		head.setImageUrls(resource.LOCALOHST+user.getImagepatch());
		Glide.with(getActivity()).load(R.drawable.touxiang1).bitmapTransform(new BlurTransformation(getActivity(),25),new CenterCrop(getActivity())).into(back);
		edittext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), EditinfoActivity.class);
				intent.putExtra("EditUser",user);
				startActivityForResult(intent,EDIT);
			}
		});
		exitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),LoginActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
		abouttext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),AboutActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("result", String.valueOf(requestCode));
		switch (requestCode) {
			case EDIT:
				if (resultCode == -1)
				{
				head.mUrlLoading.clear();
				head.setImageUrls(user.getImagepatch());
				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
