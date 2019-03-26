package com.example.dell.kickbang.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Resours.Resource;
import com.szysky.customize.siv.SImageView;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by dell on 2019/3/26 0026.
 */

public class MyInfoFragment extends Fragment {
	User user ;
	View view;
	SImageView head;
	ImageView back;
	Resource resource;
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
		head = view.findViewById(R.id.h_head);
		back = view.findViewById(R.id.h_back);
		head.setImageUrls(resource.LOCALOHST+user.getImagepatch());;
		Glide.with(getActivity()).load(R.drawable.touxiang1).bitmapTransform(new BlurTransformation(getActivity(),25),new CenterCrop(getActivity())).into(back);

	}

}
