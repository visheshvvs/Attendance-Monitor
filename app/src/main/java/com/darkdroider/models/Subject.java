package com.darkdroider.models;

import java.io.Serializable;

public class Subject implements Serializable
{
	
	int _id;
	String _name;
	int _present;
	int _absent;
	
	public Subject()
	{
		
	}
	public Subject(String name)
	{
		this._name=name;
		this._present=0;
		this._absent=0;
	}
	public void setId(int id)
	{
		this._id=id;
	}
	
	public void setName(String name)
	{
		this._name=name;
	}
	
	public void setPresents(int presents)
	{
		this._present=presents;
	}
	
	public void setAbsents(int absents)
	{
		this._absent=absents;
	}
	
	public String getName()
	{
		return this._name;
	}
	public int getPresents()
	{
		return this._present;
	}
	public int getAbsents()
	{
		return this._absent;
	}
	

}
