package com.agile.datastore;

import javax.persistence.Id;

public class CustomerPojo {
	@Id Long id;

	private int cusid;
	private String cusname;
	private String place;
	public int getCusid() {
		return cusid;
	}
	public void setCusid(int cusid) {
		this.cusid = cusid;
	}
	public String getCusname() {
		return cusname;
	}
	public void setCusname(String cusname) {
		this.cusname = cusname;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	
	
	
}
