package com.example.dell.kickbang.Utils;

import com.example.dell.kickbang.Resours.Resource;


/**
 * Created by dell on 2019/3/13 0013.
 */

public class HttpUtils {
	public static HttpUtils httpUtils;
	private Resource res;

	private HttpUtils() {
		res = Resource.getInstance();
	}
	public static synchronized HttpUtils getHttpUtils(){
		if (httpUtils == null){
			httpUtils = new HttpUtils();
		}
		return httpUtils;
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
	public final String getCreateTeamUrl(String uid,String tname,String intro){
		return res.LOCALOHST+res.CTEATETEAM+res.UID+uid+res.AND+res.TNAME+tname+res.AND+"desc="+intro;
	}
	public final String getuploadheadimage(String uid){
		return res.LOCALOHST+res.UPDATAHEAD+"?uid="+uid;
	}

	public final String getuploadteamicon(String teamid){
		return res.LOCALOHST+res.UPDATATEAMIMG+"tid="+teamid;
	}
	public final String getteamnotifi(String uid){
		return res.LOCALOHST+res.UPDATANOTIFI+"tid="+uid;
		}
	public final String getteamnotifinum(String tid ,String lid ){
		return res.LOCALOHST+res.GETNOTIFINUM+res.TID+tid+res.AND+res.LID+lid;
	}
	public final String getrelasnewnoti(String tid,String context){
		return res.LOCALOHST+res.CREATENEWNOTI+res.TID+tid+res.AND+res.CONTEXT+context;
	}
	public final String getSearchuser(String uname){
		return res.LOCALOHST+"searchuser?value="+uname;
	}

}
