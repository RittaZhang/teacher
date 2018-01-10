package com.vp.model;

public class Bonuspenalty {

	private int id;
	private Boolean bpflag;
	private String bpcontent;
	private int teacher_id;
	private String remarks;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public Boolean getBpflag()
	{
		return bpflag;
	}
	public void setBpflag(Boolean bpflag)
	{
		this.bpflag=bpflag;
	}
	public String getBpcontent()
	{
		return bpcontent;
	}
	public void setBpcontent(String bpcontent)
	{
		this.bpcontent=bpcontent;
	}	
	public int getTeacher_id()
	{
		return teacher_id;
	}
	public void setTeacher_id(int teacher_id)
	{
		this.teacher_id = teacher_id;
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
