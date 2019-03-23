package com.example.dell.kickbang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.kickbang.Activity.MainActivity;
import com.example.dell.kickbang.Activity.TeamUserActActivity;
import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Resours.Resource;
import com.szysky.customize.siv.SImageView;

import java.util.List;

/**
 * Created by dell on 2019/3/21 0021.
 */

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
	private Context context;
	private LayoutInflater mInflater;
	private OnItemClickCallback callback = null;
	Resource resource;
	public PlayerAdapter(Context context, List<User> list, OnItemClickCallback onItemClickCallback){
		this.context = context;
		this.users = list;
		this.callback = onItemClickCallback;
		resource = Resource.getInstance();
	}

	private List<User> users;
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item,parent,false);
		ViewHolder holder = new ViewHolder(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		User user = users.get(position);
		holder.username.setText("用户:"+user.getUsername());
		holder.number.setText("号码:"+String.valueOf(user.getNum()));
		holder.pos.setText("位置:"+user.getPosition());
		holder.sImageView.setImageUrls(resource.LOCALOHST+user.getImagepatch());
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context," click "+position,Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(context, TeamUserActActivity.class);
				intent.putExtra("User",users.get(position));
				context.startActivity(intent);
			}
		});
	}

	@Override
	public int getItemCount() {
		return users.size();
	}

	static class ViewHolder extends RecyclerView.ViewHolder {
		SImageView sImageView ;
		TextView username;
		TextView pos;
		TextView number;
		public ViewHolder(View itemView) {
			super(itemView);
			username = itemView.findViewById(R.id.team_playrename_textiview);
			pos = itemView.findViewById(R.id.team_num_textiview);
			number = itemView.findViewById(R.id.team_pos_textiview);
			sImageView = itemView.findViewById(R.id.player_image);
		}
	}
	public interface OnItemClickCallback<T> {
		// 点击事件
		void onClick(View view , T info);
		// 长按事件
		void onLongClick(View view , T info);
	}


}
