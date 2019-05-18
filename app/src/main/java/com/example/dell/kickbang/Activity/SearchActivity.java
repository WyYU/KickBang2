package com.example.dell.kickbang.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.kickbang.Adapter.PlayerAdapter;
import com.example.dell.kickbang.Model.Team;
import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity {
	private String TAG = "SearchActivity";
	private SearchView searchView;
	private RecyclerView recyclerView;
	private List<User> searchresult;
	private List<Team> searchteamresult;
	private RecyclerView.Adapter adapter;
	private Presenter presenter;
	private TextView utitleview;
	private TextView ttitleview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		presenter = new Presenter(SearchActivity.this);
		searchresult = new ArrayList<>();
		searchteamresult = new ArrayList<>();
		initView();
		initEvent();
	}

	private void initEvent() {
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				Toast.makeText(SearchActivity.this,"搜索"+searchView.getQuery().toString(),Toast.LENGTH_SHORT).show();
				searchresult = new ArrayList<>();
				String stext = query;
				boolean checkres = checktext(stext);
				if (checkres==false){
					return false;
				}
				searchresult.clear();
				try {
					User resultbyid = presenter.queryUserbyid(stext);
					if (resultbyid != null){
						searchresult.add(resultbyid);
					}
					List<User> users = presenter.searchUserbyname(stext);
					if (users != null||users.size()>0){
						for(int i = 0; i<users.size();i++){
							searchresult.add(users.get(i));
						}
					}
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				adapter.notifyDataSetChanged();
				initView();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {

				return false;
			}
		});
	}

	private boolean checktext(String stext) {
		if ("".equals(stext)){
			return false;
		}
		return true;
	}

	private void initView() {
		utitleview = findViewById(R.id.s_usertitle);
		ttitleview = findViewById(R.id.s_teamtitle);
		if (searchresult.size()<=0){
			utitleview.setVisibility(View.INVISIBLE);
		}else utitleview.setVisibility(View.VISIBLE);
		if (searchteamresult.size()<=0){
			ttitleview.setVisibility(View.INVISIBLE);
		}else ttitleview.setVisibility(View.VISIBLE);
		searchView = findViewById(R.id.s_search_view);
		searchView.setIconifiedByDefault(false);
		recyclerView = findViewById(R.id.search_userresult);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
		adapter = new PlayerAdapter(this, searchresult, new PlayerAdapter.OnItemClickCallback() {
			@Override
			public void onClick(View view, int position) {
				Toast.makeText(SearchActivity.this,"  "+position,Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(SearchActivity.this,TeamUserActActivity.class);
				intent.putExtra("User",searchresult.get(position));
				startActivityForResult(intent,3);
			}
		});
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.setAdapter(adapter);
	}


}
