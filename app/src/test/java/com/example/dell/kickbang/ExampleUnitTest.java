package com.example.dell.kickbang;

import com.example.dell.kickbang.Utils.HttpUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
	@Test
	public void addition_isCorrect() throws Exception {
		assertEquals(4, 2 + 2);
	}
	@Test
	public  void Test(){
		HttpUtils httpUtils = new HttpUtils();
		System.out.println(httpUtils.getLoginurl("wyy","961213"));
		System.out.println(httpUtils.getRegistUrl("wyy","961213"));
		System.out.println(httpUtils.getQueryuserUrl("id","15"));
		System.out.println(httpUtils.getQueryTeamPlayerUrl("14"));
		System.out.println(httpUtils.getQueryTeamUrl("tname","111"));
		System.out.println(httpUtils.getQueryFieldUrl());
		System.out.println(httpUtils.getUPDATAMSGUrl("13","4","FORWARD"));
		System.out.println(httpUtils.getCreateTeamUrl("XXXXXXXXXXX"));
		System.out.println(httpUtils.getChangelvUrl("15","4"));
	}
}