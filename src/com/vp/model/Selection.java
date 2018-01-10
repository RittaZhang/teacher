package com.vp.model;

public class Selection {
	private int id;
	private String groupname;
	private int optionorder;
	private String optionvalue ;
	private String remarks;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public String getGroupname()
	{
		return groupname;
	}
	public void setGroupname(String groupname)
	{
		this.groupname=groupname;
	}
	public int getOptionorder()
	{
		return optionorder;
	}
	public void setOptionorder(int optionorder)
	{
		this.optionorder=optionorder;
	}
	public String getOptionvalue()
	{
		return optionvalue;
	}
	public void setOptionvalue(String optionvalue)
	{
		this.optionvalue=optionvalue;
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
