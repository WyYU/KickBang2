package com.example.dell.kickbang.Adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.kickbang.Model.Notification;
import com.example.dell.kickbang.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by dell on 2019/4/22 0022.
 */

public class NotifiAdapter extends RecyclerView.Adapter<NotifiAdapter.ViewHolder>  {
	private Context context;
	private List<Notification> list;

	public NotifiAdapter(Context context,List data){
		this.list = data;
		this.context = context;
	}
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item,parent,false);
		ViewHolder viewHolder = new ViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Notification notification = list.get(position);
		holder.dateview .setText(notification.getData().toString());
		holder.text.setText(notification.getContext());
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	static class ViewHolder extends RecyclerView.ViewHolder {
		TextView dateview;
		TextView text;
		public ViewHolder(View itemView) {
			super(itemView);
			dateview = itemView.findViewById(R.id.notifi_time_textview);
			text = itemView.findViewById(R.id.notifi_context_textview);
		}
	}
}
