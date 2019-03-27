package com.example.dell.kickbang.Resours;

/**
 * Created by dell on 2019/3/13 0013.
 */

public class Resource {
	private static Resource resource;
	public static synchronized Resource getInstance(){
		if (resource==null){
			resource = new Resource();
		}
		return resource;
	}
	private Resource(){};
	public final String LOCALOHST = "http://192.168.2.118:8080/";
	public final String imagePath = "";
	public final String AND = "&";

	public final String LOGIN = "login?";
	public final String REGIST = "regist?";
	public final String QUERY = "queryuser?";
	public final String QUERTTEAMPLAYER = "queryteamplayer?";
	public final String QUERYTEAM = "queryteam?";
	public final String CHANGELV = "changelv?";
	public final String JOINTEAM = "jointeam?";
	public final String DELUSER = "deluser?";
	public final String UPDATAMSG = "updatamsg?";
	public final String CTEATETEAM = "createteam?";
	public final String QUERYFIELD = "queryfield";
	public static final String UPDATAHEAD = "updatahead";


	public final String USERNAME = "username=";
	public final String PASSWORD = "pwd=";
	public final String UID = "uid=";
	public final String TID = "tid=";
	public final String KEY = "key=";
	public final String VALUE = "value=";
	public final String LV = "lv=";
	public final String MSG= "msg=";
	public final String TNAME = "tname=";
	public final String POS = "pos=";
	public final String NUM = "num=";

	public final int NULL_TEAM_CODE = 26;
}
