package com.darkdroider.models;

public class Attendance 
{
	int _id;
	int _date;
	
	
	public Attendance(int date)
	{
		this._date=date;
	}
	
	public int getDate()
	{
		return this._date;
	}
}
