package com.vp.model;

import java.util.Date;

public class Train {

	private int id;
	private int teacher_id;
	private String type;
	private String hostunit;
	private String way;
	private String place;
	private Date startdate;
	private Date enddate;
	private String result;
	private String remarks;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public int getTeacher_id()
	{
		return teacher_id;
	}
	public void setTeacher_id(int teacher_id)
	{
		this.teacher_id=teacher_id;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type=type;
	}
	public String getHostunit()
	{
		return hostunit;
	}
	public void setHostunit(String hostunit)
	{
		this.hostunit=hostunit;
	}
	public String getWay()
	{
		return way;
	}
	public void setWay(String way)
	{
		this.way=way;
	}
	public String getPlace()
	{
		return place;
	}
	public void setPlace(String place)
	{
		this.place=place;
	}
	public Date getStartdate()
	{
		return startdate;
	}
	public void setStartdate(Date startdate)
	{
		this.startdate=startdate;
	}
	public Date getEnddate()
	{
		return enddate;
	}
	public void setEnddate(Date enddate)
	{
		this.enddate=enddate;
	}
	public String getResult()
	{
		return result;
	}
	public void setResult(String result)
	{
		this.result=result;
	}
	public String getRemarks()
	{
		return remarks;
	}
	public void setRemarks(String remarks)
	{
		this.remarks=remarks;
	}
	
}
