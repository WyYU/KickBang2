package com.example.dell.kickbang.Model;

import java.sql.Date;

/**
 * Created by dell on 2019/3/24 0024.
 */
public class Notification {
	private int tid;
	private String context;
	private Date data;
	private int id;

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return getId()+" "+getData()+"  "+getContext();
	}
}
