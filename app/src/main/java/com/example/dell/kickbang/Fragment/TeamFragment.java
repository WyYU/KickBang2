package com.example.dell.kickbang.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.dell.kickbang.Activity.SearchActivity;
import com.example.dell.kickbang.Activity.TeamDynamicActivity;
import com.example.dell.kickbang.Adapter.PlayerAdapter;
import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Resours.Resource;

import java.util.List;

/**
 * Created by dell on 2019/3/20 0020.
 */

public class TeamFragment extends Fragment implements View.OnClickListener {
	RecyclerView recyclerView;
	Resource resource;
	private View view;
	private User user;
	private List<User> teammate;
	ImageButton adduserBtn;
	ImageButton SearchBtn;
	ImageButton NotifcationBtn;

	@Override
	public void onAttach(Context context) {
		Log.e(getClass().getName(),"onAttach");
		super.onAttach(context);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		Log.e(getClass().getName(),"onCreate");
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		Log.e(getClass().getName(),"onCreateView");
		view = inflater.inflate(R.layout.fragment_team, null);
		resource = Resource.getInstance();
		if (user ==null || teammate ==null){
			prepareData();
		}
		initView();
		initEvent();
		return view;
	}

	private void initEvent() {
		adduserBtn.setOnClickListener(this);
		SearchBtn.setOnClickListener(this);
		NotifcationBtn.setOnClickListener(this);
	}

	private void prepareData() {
		Presenter presenter = new Presenter();
		Intent intent = getActivity().getIntent();
		try {
			user = (User) intent.getSerializableExtra("User");
			if (user.getTid() != resource.NULL_TEAM_CODE) {
				teammate = presenter.queryteamplayer(String.valueOf(user.getTid()));
			}
		}catch (Exception e){

		}
	}

	private void initView() {
		adduserBtn = view.findViewById(R.id.adduser_btn);
		SearchBtn = view.findViewById(R.id.search_button);
		NotifcationBtn = view.findViewById(R.id.Notification_btn);
		recyclerView = view.findViewById(R.id.player_recycler_view);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		recyclerView.setLayoutManager(linearLayoutManager);
		PlayerAdapter playerAdapter = new PlayerAdapter(getContext(), teammate, new PlayerAdapter.OnItemClickCallback() {
			@Override
			public void onClick(View view, Object info) {
				
			}

			@Override
			public void onLongClick(View view, Object info) {

			}
		});
		recyclerView.setAdapter(playerAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.adduser_btn:
				break;
			case R.id.search_button:
				Intent intent = new Intent(getContext(), SearchActivity.class);
				startActivity(intent);
				break;
			case R.id.Notification_btn:
				Intent intent1 = new Intent(getContext(), TeamDynamicActivity.class);
				startActivity(intent1);
				Toast.makeText(getContext(),"球队通知",Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
		}
	}
}
