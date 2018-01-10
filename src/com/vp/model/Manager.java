package com.vp.model;

public class Manager {

	
	private int id ;
	private String account;
	private String password;
	private String power;
	
	public Manager(){
		
	}
	
	public Manager(int id, String account, String password,String power) {
		//super();
		this.id = id;
		this.account = account;
		this.password = password;
		this.power = power;
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public String getAccount()
	{
		return account;
	}
	public void setAccount(String account)
	{
		this.account=account;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password=password;
	}
	public String getPower()
	{
		return power;
	}
	public void setPower(String power)
	{
		this.power=power;
	}
}
