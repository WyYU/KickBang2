package com.example.dell.kickbang.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.Toast;

import com.example.dell.kickbang.Activity.MainActivity;
import com.example.dell.kickbang.Activity.SearchActivity;
import com.example.dell.kickbang.Activity.TeamDynamicActivity;
import com.example.dell.kickbang.Activity.TeamUserActActivity;
import com.example.dell.kickbang.Adapter.PlayerAdapter;
import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Resours.Resource;
import com.example.dell.kickbang.Service.MyService;
import com.example.dell.kickbang.Utils.PreferencesFactory;

import java.util.List;

/**
 * Created by dell on 2019/3/20 0020.
 */

public class TeamFragment extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
	private final String  TAG = "TeamFragment";
	PlayerAdapter playerAdapter;
	RecyclerView recyclerView;
	Resource resource;
	private View view;
	private User user;
	private List<User> teammate;
	ImageButton adduserBtn;
	ImageButton SearchBtn;
	ImageButton NotifcationBtn;
	Presenter presenter;
	SharedPreferences preferences;
	private String ulv;
	private String utid;
	@Override
	public void onAttach(Context context) {
		Log.e(getClass().getName(),"onAttach");
		super.onAttach(context);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		preferences = PreferencesFactory.getInstance(getActivity());
		ulv = preferences.getString("userlv","0");
		utid = preferences.getString("usertid","26");
		super.onCreate(savedInstanceState);

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
		presenter = new Presenter(getActivity());
		Intent intent = getActivity().getIntent();
		try {
			user = (User) intent.getSerializableExtra("User");
			teammate = presenter.queryteamplayer(String.valueOf(user.getTid()));
			if (user.getTid() == resource.NULL_TEAM_CODE) {
				teammate.clear();
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
		recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
		playerAdapter = new PlayerAdapter(getContext(), teammate, new PlayerAdapter.OnItemClickCallback() {
			@Override
			public void onClick(View view, int position) {
				Toast.makeText(getContext(),"  "+position,Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getActivity(),TeamUserActActivity.class);
				intent.putExtra("User",teammate.get(position));
				startActivityForResult(intent,3);
			}
		});
		recyclerView.setAdapter(playerAdapter);
		adduserBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.adduser_btn:
				PopupMenu menu = new PopupMenu(getActivity(),v);
				MenuInflater inflater = menu.getMenuInflater();
				inflater.inflate(R.menu.main,menu.getMenu());
				menu.setOnMenuItemClickListener(this);
				menu.show();
				break;
			case R.id.search_button:
				Intent intent = new Intent(getContext(), SearchActivity.class);
				startActivity(intent);
				break;
			case R.id.Notification_btn:
				Intent intent1 = new Intent(getContext(), TeamDynamicActivity.class);
				startActivityForResult(intent1,4);
				Toast.makeText(getContext(),"球队通知",Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
		}
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()){
			case R.id.addplayer:
				if (Integer.parseInt(utid)==26){
					Toast.makeText(getActivity(),"你还没有球队",Toast.LENGTH_SHORT).show();
					return false;
				}
				if (Integer.parseInt(ulv)<3){
					Toast.makeText(getActivity(),"您的等级不够",Toast.LENGTH_SHORT).show();
				}
		}
		return false;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("ASSSSSSSSSSSSSSSSSSSSSSSSSS","requestCode "+requestCode+"resultcode "+resultCode);
		switch (resultCode) {
			case 3:
				teammate = presenter.queryteamplayer(String.valueOf(user.getTid()));
				Log.e(" sads",teammate.toString());
				initView();
				playerAdapter.notifyDataSetChanged();
				break;
			case 4:
//				Intent intent = getActivity().getIntent();
//				user = (User) intent.getSerializableExtra("User");
//				Intent notifiService = new Intent(getActivity(),MyService.class);
//				notifiService.putExtra("User",user);
//				getActivity().stopService(notifiService);
//				getActivity().startService(notifiService);
//				Log.e(TAG,"restartservice");
				break;

		}
	}

}
