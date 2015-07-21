package com.darkdroider.attendancetracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.darkdroider.models.ListItem;
import com.darkdroider.models.Subject;
import com.darkdroider.models.Timing;

public class MarkAtt extends Fragment implements OnItemSelectedListener, OnClickListener
{
	Spinner dropdown;
	ListView list;
	static int flag=-1;
	Button save;
	
	CustomAdapter c;
	ArrayList<String> finalList;
	
	 Calendar first = Calendar.getInstance();
	
	File  filesdcard=Environment.getExternalStorageDirectory();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		
		View rootView = inflater.inflate(R.layout.activity_mark_att, container, false);
		list = (ListView)rootView.findViewById(R.id.listView1);
		dropdown = (Spinner)rootView.findViewById(R.id.dropdownlist2);
		dropdown.setOnItemSelectedListener(this);
		save = (Button)rootView.findViewById(R.id.save);
		save.setOnClickListener(this);
    	updateDropDown();
		return rootView;
		
	}

	
	
	
	
	public void updateDropDown()
	{
		File file = new File(filesdcard,"hello.txt");
		
		
		StringBuilder builder=null;
		String line = null;
		try {
			
		FileInputStream input = new FileInputStream(file);
	
		
		 BufferedReader	reader = new BufferedReader(new FileReader(file));
	        builder = new StringBuilder();
	        
	        while ((line = reader.readLine()) != null) 
	        {
	            builder.append(line);
			}
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	String date = builder.toString();	
	
	SimpleDateFormat dfDate  = new SimpleDateFormat("dd-MM-yyyy");
    java.util.Date d = null;
    java.util.Date d1 = null;
    Calendar cal = Calendar.getInstance();
    try {
        d = dfDate.parse(date);
        d1 = dfDate.parse(dfDate.format(cal.getTime()));
    } catch (java.text.ParseException e) {
        e.printStackTrace();
    }
    System.out.println(d);
    Calendar cals = Calendar.getInstance();
    cals.setTime(d);
    first.setTime(d);
    Calendar cale = Calendar.getInstance();
    cale.setTime(d1);
    cale.add(Calendar.DAY_OF_YEAR, 1);
    ArrayList<String> dates = new ArrayList<String>();
    
    while (cals.before(cale)) 
    {
    	int day = cals.get(Calendar.DAY_OF_WEEK);
    	if(day==1)
    		dates.add(dfDate.format(cals.getTime())+" Sunday");
    	else if(day==2)
    		dates.add(dfDate.format(cals.getTime())+" Monday");
    	else if(day==3)
    		dates.add(dfDate.format(cals.getTime())+" Tuesday");
    	else if(day==4)
    		dates.add(dfDate.format(cals.getTime())+" Wednesday");
    	else if(day==5)
    		dates.add(dfDate.format(cals.getTime())+" Thursday");
    	else if(day==6)
    		dates.add(dfDate.format(cals.getTime())+" Friday");
    	else if(day==7)
    		dates.add(dfDate.format(cals.getTime())+" Saturday");
        
        cals.add(Calendar.DAY_OF_YEAR, 1);
        
    }
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, dates);
	dropdown.setAdapter(adapter);
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
	{
		String name = arg0.getSelectedItem().toString().substring(11);
		if(name.equalsIgnoreCase("monday"))
			flag=1;
		else if(name.equalsIgnoreCase("tuesday"))
			flag=2;
		else if(name.equalsIgnoreCase("wednesday"))
			flag=3;
		else if(name.equalsIgnoreCase("thursday"))
			flag=4;
		else if(name.equalsIgnoreCase("friday"))
			flag=5;
		else if(name.equalsIgnoreCase("saturday"))
			flag=6;
		else if(name.equalsIgnoreCase("sunday"))
			flag=7;
		updateList();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	private void updateList()
	{
		DatabaseHandler db = new DatabaseHandler(getActivity());
    	ArrayList<Subject> allsubs = new ArrayList<Subject>();
    	ArrayList<String> times = new ArrayList<String>();
    	allsubs = db.getAllSubjects();
    	for(int i=0;i<allsubs.size();i++)
    	{
    		ArrayList<Timing> timings = new ArrayList<Timing>();
    		timings = db.getAllTimings(allsubs.get(i).getName());
    		for(int j=0;j<timings.size();j++)
    		{
    			if((timings.get(j).getTiming()/10000)%10==flag)
    			{
    				int temp = timings.get(j).getTiming();
    				int mins = temp%100;
    				temp=temp/100;
    				int hrs = temp%100;
    				String hr = Integer.toString(hrs);
    				if(hr.length()==1)
    				{
    					hr="0"+hr;
    				}
    				String min = Integer.toString(mins);
    				if(min.length()==1)
    				{
    					min="0"+min;
    				}
    				String t = hr+":"+min;
    				if(timings.get(j).getTiming()/100000==1)
    					t=t+" Lec.";
    				else if(timings.get(j).getTiming()/100000==2)
    					t=t+" Tut.";
    				else if(timings.get(j).getTiming()/100000==3)
    					t=t+" Lab.";
    				t=t+" "+allsubs.get(i).getName();
    				times.add(t);
    				
    			}
    		}
    	}
    	finalList = sort(times);
    	Calendar s = Calendar.getInstance();
    	String selected = dropdown.getSelectedItem().toString().subSequence(0, 10).toString();
		try 
		{
			
			SimpleDateFormat dfDate  = new SimpleDateFormat("dd-MM-yyyy");
			java.util.Date selectedDate = dfDate.parse(selected);
			
			s.setTime(selectedDate);
			
				
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	if(finalList.size()==0 && !first.before(s))
    	{
    		first.add(Calendar.DAY_OF_YEAR, 1);
			File file = new File(filesdcard,"hello.txt");
			try 
			{
				file.createNewFile();
			
				SimpleDateFormat dfDate  = new SimpleDateFormat("dd-MM-yyyy");
				String data=(dfDate.format(first.getTime()));
				
				FileOutputStream output = new FileOutputStream(file);
				output.write(data.getBytes());
				output.close();
				updateDropDown();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	ArrayList<ListItem> passList = new ArrayList<ListItem>();
    	for(int i=0;i<finalList.size();i++)
    	{
    		passList.add(new ListItem(finalList.get(i), 0));
    	}
    	c = new CustomAdapter(getActivity(), passList);
    	list.setAdapter(c);
	}
	
	private ArrayList<String> sort(ArrayList<String> list)
    {
    	int index=0;
    	int j;
    	
    	for(int i=0;i<list.size();i++)
    	{
    		int max = -1;
    		for(j=0;j<list.size()-i;j++)
    		{
    			if(Integer.parseInt(list.get(j).subSequence(0, 2).toString())>max)
    			{
    				max=Integer.parseInt(list.get(j).subSequence(0, 2).toString());
    				index = j;
    			}
    		}
    		
    		String temp;
    		temp = list.get(index);
    		list.set(index,list.get(j-1));
    		list.set(j-1, temp);
    	}
    	return list;
    	
    }

	@Override
	public void onClick(View v) 
	{
		
		if(v.getId()==R.id.save)
		{
			int check=0;
			DatabaseHandler db = new DatabaseHandler(getActivity());
			int flag[] = c.getList();
			
			String selected = dropdown.getSelectedItem().toString().subSequence(0, 10).toString();
			try 
			{
				
				SimpleDateFormat dfDate  = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date selectedDate = dfDate.parse(selected);
				Calendar s = Calendar.getInstance();
				s.setTime(selectedDate);
				if(first.before(s))
				{
					check=1;
					Toast.makeText(getActivity(), "Please Mark Previous Attendance first", Toast.LENGTH_SHORT).show();
				}
					
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			
			for(int i=0;i<finalList.size();i++)
			{
				
				if(flag[i]==0)
				{
					
					Toast.makeText(getActivity(), "Please Mark All Attendance", Toast.LENGTH_SHORT).show();
					
					check=1;
					break;
				}
			}
			if(check==0)
			{
				SimpleDateFormat dfDate  = new SimpleDateFormat("dd-MM-yyyy");
				String data=(dfDate.format(first.getTime()));
			for(int i=0;i<finalList.size();i++)
			{
				
				
				if(flag[i]==1)
				{
					System.out.println(finalList.get(i).substring(6, 11));
					
					if(finalList.get(i).substring(6,11).compareToIgnoreCase("Lab.")==0)
					{
						db.setPresent(finalList.get(i).substring(11));
					}
					db.setPresent(finalList.get(i).substring(11));
					db.addHistory(finalList.get(i).substring(11), data+": Present");
					
					
				}
				else if(flag[i]==2)
				{
					if(finalList.get(i).substring(6,11).compareToIgnoreCase("Lab.")==0)
					{
						db.setAbsent(finalList.get(i).substring(11));
					}
					db.setAbsent(finalList.get(i).substring(11));
					db.addHistory(finalList.get(i).substring(11), data+": Absent");
				}
				else if(flag[i]==3)
				{
					db.addHistory(finalList.get(i).substring(11), data+": Cancelled");
				}
			}
			first.add(Calendar.DAY_OF_YEAR, 1);
			File file = new File(filesdcard,"hello.txt");
			try 
			{
				file.createNewFile();
				
				String data1=(dfDate.format(first.getTime()));
				FileOutputStream output = new FileOutputStream(file);
				output.write(data1.getBytes());
				output.close();
				updateDropDown();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
			
		}
		
	}
	

}
