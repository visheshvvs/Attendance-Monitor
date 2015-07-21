package com.darkdroider.models;

public class ListItem 
{
	public String text;
	public Boolean present,absent,cancelled;
	
	public ListItem(String txt, int sel)
	{
		text=txt;
		if(sel==0)
		{
			present=false;
			absent=false;
			cancelled=false;
		}
		else if(sel==1)
		{
			present=true;
			absent=false;
			cancelled=false;
		}
		else if(sel==2)
		{
			present=false;
			absent=true;
			cancelled=false;
		}
		else if(sel==3)
		{
			present=false;
			absent=false;
			cancelled=true;
		}
	}
	public void setPresent()
	{
		present=true;
		absent=false;
		cancelled=false;
	}
	public void setAbsent()
	{
		present=false;
		absent=true;
		cancelled=false;
	}
	public void setCancelled()
	{
		present=false;
		absent=false;
		cancelled=true;
	}
}
