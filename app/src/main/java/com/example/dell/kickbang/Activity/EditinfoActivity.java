package com.example.dell.kickbang.Activity;

import android.Manifest;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dell.kickbang.Model.User;
import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Resours.Resource;
import com.example.dell.kickbang.Utils.HttpUtils;
import com.example.dell.kickbang.Utils.Utils;
import com.szysky.customize.siv.SImageView;

import java.util.ArrayList;
import java.util.List;

public class EditinfoActivity extends AppCompatActivity implements View.OnClickListener {
	private final int CHANGERESULT = 2;
	private User u;
	private Utils utils;
	private SImageView sImageView;
	private EditText posEdit;
	private Spinner posSpinner;
	private EditText numedit;
	private Resource resource;
	private HttpUtils httpUtils;
	private Presenter presenter;
	private AlertDialog.Builder builder;
	private final int CHOOSE_PHOTO = 2;
	private Bitmap bitmap;
	Button Confi_btn;
	Button Cancle_btn;
	private String imagePath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editinfo);
		resource = Resource.getInstance();
		utils = Utils.getInstance();
		presenter = new Presenter(this);
		PrepareData();
		initView();
		initEvent();
	}

	private void initEvent() {
		sImageView.setOnClickListener(this);
		Confi_btn.setOnClickListener(this);
		Cancle_btn.setOnClickListener(this);
	}

	private void initView() {
		//posEdit = findViewById(R.id.user_pos_edit);
		posSpinner = findViewById(R.id.edit_info_spinner);
		numedit = findViewById(R.id.user_num_edit);
		sImageView = findViewById(R.id.user_head_image);
		Confi_btn = findViewById(R.id.edit_conf_btn);
		Cancle_btn = findViewById(R.id.edit_cancel_btn);
		//posEdit.setText(u.getPosition());
		numedit.setText(u.getNum().toString());
		sImageView.setImageUrls(resource.LOCALOHST+u.getImagepatch());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditinfoActivity.this, android.R.layout.simple_spinner_dropdown_item, getData());
		posSpinner.setAdapter(adapter);
		choosepos(u.getPosition());
		buildDialog();
	}

	private void choosepos(String position) {
		switch (position){
			case "null":
				posSpinner.setSelection(0);
				break;
			case "前锋":
				posSpinner.setSelection(1);
				break;
			case "中场":
				posSpinner.setSelection(2);
				break;
			case "后卫":
				posSpinner.setSelection(3);
				break;
			case "门将":
				posSpinner.setSelection(4);
				break;
			default:
				posSpinner.setSelection(0);
				break;
		}
	}

	private List<String> getData() {
		List<String> dataList = new ArrayList<String>();
		dataList.add("null");
		dataList.add("前锋");
		dataList.add("中场");
		dataList.add("后卫");
		dataList.add("门将");
		return dataList;
	}

	private void buildDialog() {
		builder = utils.buildChooseHeadDialog(this,EditinfoActivity.this);
		final String[] choose = {"拍一张", "从相册选择"};
		builder.setItems(choose, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (ContextCompat.checkSelfPermission(EditinfoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
					ActivityCompat.requestPermissions(EditinfoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
				} else {
					openAlbun();
				}
			}
		});
	}

	private void PrepareData() {
		Intent intent = getIntent();
		u = (User) intent.getSerializableExtra("EditUser");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.user_head_image:
				buildDialog();
				builder.show();
				break;
			case R.id.edit_cancel_btn:
				finish();
				break;
			case R.id.edit_conf_btn:
				updataimage(imagePath, String.valueOf(u.getId()));
				Intent i = new Intent();
				i.putExtra("result",CHANGERESULT);
				setResult(RESULT_OK,i);
				finish();
		}
	}
	private void openAlbun() {
		Intent intent = new Intent("android.intent.action.GET_CONTENT");
		intent.setType("image/*");
		startActivityForResult(intent,CHOOSE_PHOTO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode){
			case CHOOSE_PHOTO:
				handleImageonKitKat(data);
				Log.e("data",data.toString());
				break;
			default:
				break;
		}
	}

	private void handleImageonKitKat(Intent data) {
		imagePath = null;
		Uri uri = data.getData();
		if (DocumentsContract.isDocumentUri(this,uri)){
			String docid = DocumentsContract.getDocumentId(uri);
			if("com.android.providers.media.documents".equals(uri.getAuthority())){
				String id = docid.split(":")[1];
				String selection = MediaStore.Images.Media._ID+"="+id;
				imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
			} else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
				Uri uri1 = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docid));
				imagePath = getImagePath(uri1,null);
		}else if("content".equalsIgnoreCase(uri.getScheme())){
				imagePath = getImagePath(uri,null);
			}else if ("file".equalsIgnoreCase(uri.getScheme())){
			imagePath = uri.getPath();
			}
		}
		displayimage(imagePath);
	}

	private void updataimage(String imagePath,String uid) {
		if (imagePath != null) {
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			presenter.updataheadimage(uid,bitmap);
		}else {
			Toast.makeText(this,"获取图片失败",Toast.LENGTH_SHORT).show();
		}
	}

	private void displayimage(String imagePath) {
		if (imagePath!= null) {
			bitmap = BitmapFactory.decodeFile(imagePath);
			sImageView.setBitmap(bitmap);
		}
		else {
			Toast.makeText(this,"获取图片失败",Toast.LENGTH_SHORT).show();
		}
	}

	public String getImagePath(Uri uri,String selection) {
		String path = null;
		Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
		if (cursor != null) {
			if (cursor.moveToFirst()){
			path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
		}
		}
		cursor.close();
		return path;
	}
}
