package com.example.dell.kickbang.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dell.kickbang.Model.Field;
import com.example.dell.kickbang.R;

import java.util.List;

/**
 * Created by dell on 2019/3/21 0021.
 */

public class FieldAdapter extends ArrayAdapter<Field> {
	private int resourceId;

	public FieldAdapter(Context context, int field_item, List<Field> fields) {
		super(context, field_item);
		resourceId = field_item;
	}
	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		Field field = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
		TextView fnametextView = view.findViewById(R.id.field_name_view);
		RatingBar ratingBar = view.findViewById(R.id.field_ratingBar);
		TextView address = view.findViewById(R.id.field_address_view);
		fnametextView.setText(field.getName());
		ratingBar.setNumStars(Integer.parseInt(field.getRate()));
		address.setText(field.getAddress());
		return view;
	}
}
