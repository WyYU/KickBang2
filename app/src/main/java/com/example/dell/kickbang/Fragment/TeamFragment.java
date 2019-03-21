package com.example.dell.kickbang.Fragment;

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

import com.example.dell.kickbang.Adapter.PlayerAdapter;
import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by dell on 2019/3/20 0020.
 */

public class TeamFragment extends Fragment {
	RecyclerView recyclerView;
	private View view;
	private User user;
	private List<User> teammate;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_team, null);
		prepareData();
		initView();
		return view;
	}

	private void prepareData() {
		Presenter presenter = new Presenter();
		Intent intent = getActivity().getIntent();
		final String uname = intent.getStringExtra("username");
		try {
			user = presenter.queryUser(uname);
			if (user.getTid() != 26) {
				teammate = presenter.queryteamplayer(String.valueOf(user.getTid()));
			}
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		teammate = presenter.queryteamplayer(String.valueOf(user.getTid()));
		Log.e("d",teammate.toString());
	}

	private void initView() {
		recyclerView = view.findViewById(R.id.player_recycler_view);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		recyclerView.setLayoutManager(linearLayoutManager);
		PlayerAdapter playerAdapter = new PlayerAdapter(teammate);
		recyclerView.setAdapter(playerAdapter);
	}

}
