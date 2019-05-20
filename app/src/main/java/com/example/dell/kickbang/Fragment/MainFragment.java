package com.example.dell.kickbang.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.kickbang.Activity.EditinfoActivity;
import com.example.dell.kickbang.Activity.LoginActivity;
import com.example.dell.kickbang.Activity.MainActivity;
import com.example.dell.kickbang.Activity.SearchActivity;
import com.example.dell.kickbang.Activity.TeamMsgActivity;
import com.example.dell.kickbang.Adapter.FieldAdapter;
import com.example.dell.kickbang.Model.Field;
import com.example.dell.kickbang.Model.Team;
import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Resours.Resource;
import com.example.dell.kickbang.Utils.HttpUtils;
import com.example.dell.kickbang.Utils.Utils;
import com.szysky.customize.siv.SImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2019/3/20 0020.
 */

public class MainFragment extends Fragment {
	Resource res;
	Presenter presenter;
	HttpUtils httpUtils;
	Utils utils;
	User user;
	List<User> teammate;
	ArrayList<Field> fields;
	Team team;
	SImageView imageView;
	TextView username;
	TextView userid;
	TextView teamTextView;
	TextView goalTextView;
	TextView assTextview;
	Resource resource;
	TextView teamnametextview;
	TextView teamDataView;
	TextView teamcountView;
	SImageView teamImage;
	CardView teamcardView;
	ListView fieldLIstView;
	android.widget.SearchView searchView;
	private static String Tag = "Fragment";
	public View view;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_main, null);
		initView();
		initUserCard();
		initTeamCard();
		initFieldCard();
		return view;
	}

	private void initFieldCard() {
		FieldAdapter fieldAdapter = new FieldAdapter(getContext(),R.layout.field_item,fields);
		fieldLIstView.setAdapter(fieldAdapter);
	}

	public void initTeamCard() {
		if (user.getTid() == resource.NULL_TEAM_CODE ){
			teamnametextview.setText(" 无球队 请先加入！");
			teamDataView.setText("别看了赶紧加个球队吧！");
			teamcountView.setText("0");
		}else {
			teamnametextview .setText(team.getTname());
			teamDataView.setText(team.getCreateTime().toString());
			teamImage.setImageUrls(resource.LOCALOHST+team.getIconpath());
			//teamcountView.setText(String.valueOf(teammate.size()));
		}
		teamImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), TeamMsgActivity.class);
				int tid = team.getTid();
				if (tid!=26){
					startActivity(intent);
				}
			}
		});
	}

	public void initUserCard() {
 		username.setText("  用户名："+user.getUsername());
		userid.setText("  id:"+user.getId());
		if (user.getTid()==resource.NULL_TEAM_CODE){
			teamTextView.setText("无");
		}else {teamTextView.setText(team.getTname());}
		goalTextView.setText(String.valueOf(user.getGoal()));
		assTextview.setText(String.valueOf(user.getAssisting()));
		final String imageurl = resource.LOCALOHST+user.getImagepatch();
		for (int i = 0;i<imageView.mUrlLoading.size();i++){
			Log.e("aaaaaaaaa",imageView.mUrlLoading.get(i).toString());
		}
		imageView.setImageUrls(imageurl);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), EditinfoActivity.class);
				startActivity(intent);
			}
		});
	}

	public void initView() {
		//searchView = view.findViewById(R.id.SearchView);
		teamcardView = view.findViewById(R.id.team_cardView);
		imageView = view.findViewById(R.id.user_icon_image);
		username = view.findViewById(R.id.main_username_textview);
		userid = view.findViewById(R.id.main_uid_texiview);
		teamTextView = view.findViewById(R.id.main_teamname_textview);
		goalTextView = view.findViewById(R.id.main_goal_textview);
		assTextview = view.findViewById(R.id.main_ass_textview);
		teamnametextview = view.findViewById(R.id.teamname_showview);
		teamDataView = view.findViewById(R.id.teamData_view);
		teamcountView = view.findViewById(R.id.teamcount_view);
		teamImage = view.findViewById(R.id.team_icon_image);
		fieldLIstView = view.findViewById(R.id.field_list);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		resource = Resource.getInstance();
		presenter = new Presenter(getActivity());
		utils = Utils.getInstance();
		httpUtils = HttpUtils.getHttpUtils();
		super.onCreate(savedInstanceState);
		prepareData();
	}

	private void prepareData() {
		Intent intent = getActivity().getIntent();
		try {
			user = (User) intent.getSerializableExtra("User");
			Log.e("",user.toString());
			team = (Team) intent.getSerializableExtra("Team");
			Log.e("TEAMTEAM",team.toString());
			fields = (ArrayList<Field>) presenter.queryField();
		} catch (Exception e) {
			e.printStackTrace();
			utils.showNormalDialog(getActivity(), "数据初始化错误请检查网络！");
		}
	}
}
