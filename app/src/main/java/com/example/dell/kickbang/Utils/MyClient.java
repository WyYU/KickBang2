package com.example.dell.kickbang.Utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by dell on 2019/3/17 0017.
 */

public class MyClient extends OkHttpClient{
	private  static MyClient client ;
	private MyClient(){
	}
	public static synchronized MyClient getInstance(){
		if (client == null){
			client = new MyClient();
		}
		return client;
	}
}
