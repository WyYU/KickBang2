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
import android.support.design.widget.TextInputEditText;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dell.kickbang.Presenter.Presenter;
import com.example.dell.kickbang.R;
import com.example.dell.kickbang.Utils.Utils;
import com.szysky.customize.siv.SImageView;

import java.util.ArrayList;
import java.util.List;

public class CreateNewTeamActivity extends AppCompatActivity implements View.OnClickListener {
	private final int CHOOSE_PHOTO = 2;
	private SImageView teamiconview;
	private EditText teamintroEditView;
	private EditText teamnameEditView;
	private Spinner teamcolor;
	private ArrayList<String> colorlist;
	private AlertDialog.Builder builder;
	private Utils utils;
	private String imagePath;
	private Bitmap bitmap;
	private Presenter presenter;
	private Button conButton;
	private Button cancelButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_team);
		init();
		initView();
		initEvent();
	}

	private void init() {
		utils = Utils.getInstance();
		presenter = new Presenter(this);
	}

	private void initEvent() {
		teamiconview.setOnClickListener(this);
	}

	private void initView() {
		teamiconview = findViewById(R.id.team_headedit_image);
		teamintroEditView = findViewById(R.id.team_intro_edit);
		teamnameEditView = findViewById(R.id.team_name_edit);
		teamcolor = findViewById(R.id.team_color_choose);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreateNewTeamActivity.this, android.R.layout.simple_spinner_dropdown_item, getData());
		teamcolor.setAdapter(adapter);
		teamcolor.setSelection(0);
		conButton = findViewById(R.id.teamedit_conf_btn);
		cancelButton = findViewById(R.id.teamedit_cancel_btn);
	}

	public List<String> getData() {
		List<String> dataList = new ArrayList<String>();
		dataList.add("红");
		dataList.add("黄");
		dataList.add("蓝");
		dataList.add("绿");
		dataList.add("黑");
		dataList.add("白");
		return dataList;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.team_headedit_image:
				buildDialog();
				builder.show();
				break;
			case R.id.teamedit_cancel_btn:
				finish();
				break;
			case R.id.teamedit_conf_btn:
				presenter.createnewteam(teamnameEditView.getText().toString(),teamintroEditView.getText().toString(),1);

		}
	}


	private void buildDialog() {
		builder = utils.buildChooseHeadDialog(this);
		final String[] choose = {"拍一张"};
		builder.setItems(choose, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (ContextCompat.checkSelfPermission(CreateNewTeamActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
					ActivityCompat.requestPermissions(CreateNewTeamActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
				} else {
					openAlbun();
				}
			}
		});
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

	private void displayimage(String imagePath) {
		if (imagePath!= null) {
			bitmap = BitmapFactory.decodeFile(imagePath);
			teamiconview.setBitmap(bitmap);
		}
		else {
			Toast.makeText(this,"获取图片失败",Toast.LENGTH_SHORT).show();
		}
	}

	private void updataimage(String imagePath) {
		if (imagePath != null) {
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			presenter.updatateamimage(bitmap);
		}else {
			Toast.makeText(this,"获取图片失败",Toast.LENGTH_SHORT).show();
		}
	}


}
