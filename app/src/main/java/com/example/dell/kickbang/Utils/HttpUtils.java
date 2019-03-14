package com.example.dell.kickbang.Utils;

import com.example.dell.kickbang.Resours.Resource;


/**
 * Created by dell on 2019/3/13 0013.
 */

public class HttpUtils {
	private Resource res;

	public HttpUtils() {
		res = new Resource();
	}
	public String getLoginurl(String username,String pwd){
		return res.LOCALOHST+res.LOGIN+res.USERNAME+username+res.AND+res.PASSWORD+pwd;
	}

	public String getRegistUrl(String username,String pwd){
		return res.LOCALOHST+res.REGIST+res.USERNAME+username+res.AND+res.PASSWORD+pwd;
	}
	public String getQueryuserUrl(String key,String value){
		return res.LOCALOHST+res.QUERY+res.KEY+key+res.AND+res.VALUE+value;
	}
	public String getQueryTeamUrl(String key,String value){
		return res.LOCALOHST+res.QUERYTEAM+res.KEY+key+res.AND+res.VALUE+value;
	}
	public String getQueryTeamPlayerUrl(String tid){
		return res.LOCALOHST+res.QUERTTEAMPLAYER+res.TID+tid;
	}
	public String getJoinTeamUrl(String uid,String tid){
		return res.LOCALOHST+res.JOINTEAM+res.UID+uid+res.AND+res.TID+tid;
	}
	public String getQueryFieldUrl(){
		return res.LOCALOHST+res.QUERYFIELD;
	}
	public String getChangelvUrl(String uid,String lv){
		return res.LOCALOHST+res.CHANGELV+res.UID+uid+res.AND+res.LV+lv;
	}
	public final String getUPDATAMSGUrl(String uid,String num,String pos){
		return res.LOCALOHST+res.UPDATAMSG+res.UID+uid+res.AND+res.NUM+num+res.AND+res.POS+pos;
	}
	public final String getCreateTeamUrl(String tname){
		return res.LOCALOHST+res.CTEATETEAM+res.TNAME+tname;
	}
}
