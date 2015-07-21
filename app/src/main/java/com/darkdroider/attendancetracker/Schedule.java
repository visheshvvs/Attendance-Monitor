package com.darkdroider.attendancetracker;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.darkdroider.models.Timing;

public class Schedule extends Activity implements OnClickListener 
{
	Button add;
	EditText day,lectime;
	ListView leclist;
	DatabaseHandler db;
	String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		Intent intent = getIntent();
		name = intent.getStringExtra("name");
		add = (Button)findViewById(R.id.add);
		day = (EditText)findViewById(R.id.subjectname);
		lectime = (EditText)findViewById(R.id.editText2);
		leclist = (ListView)findViewById(R.id.list);
		
		db = new DatabaseHandler(this);
		add.setOnClickListener(this);
		updateList();
		
	}

	@Override
	public void onClick(View v) 
	{
		if(v.getId()==R.id.add)
		{
			if(day.getText().toString().isEmpty()||lectime.getText().toString().isEmpty())
				Toast.makeText(this, "please dont leave empty", Toast.LENGTH_SHORT).show();
			else
			{
				int time = 0;
				if(day.getText().toString().equalsIgnoreCase("monday"))
					time=10000;
				else if(day.getText().toString().equalsIgnoreCase("tuesday"))
					time=20000;
				else if(day.getText().toString().equalsIgnoreCase("wednesday"))
					time=30000;
				else if(day.getText().toString().equalsIgnoreCase("thursday"))
					time=40000;
				else if(day.getText().toString().equalsIgnoreCase("friday"))
					time=50000;
				else if(day.getText().toString().equalsIgnoreCase("saturday"))
					time=60000;
				else if(day.getText().toString().equalsIgnoreCase("sunday"))
					time=70000;
				time+=(Integer.parseInt(lectime.getText().toString())*100);
				Timing timing = new Timing(time);
				db.addTiming(name, timing);
				day.setText("");
				lectime.setText("");
				updateList();
			}
		}
		
	}
	public void updateList()
	{
		ArrayList<Timing> l = new ArrayList<Timing>();
		l=db.getAllTimings(name);
		ArrayList<String> lecture = new ArrayList<String>();
		for(int i=0; i<l.size();i++)
		{
			String timee="";
			int t=l.get(i).getTiming();
			int min = t%100;
			t=t/100;
			int hrs=t%100;
			int day = t/100;
			if(day==1)
				timee="Monday "+Integer.toString(hrs)+":"+Integer.toString(min);
			else if(day==2)
				timee="Tuesday "+Integer.toString(hrs)+":"+Integer.toString(min);
			else if(day==3)
				timee="Wednesday "+Integer.toString(hrs)+":"+Integer.toString(min);
			else if(day==4)
				timee="Thursday "+Integer.toString(hrs)+":"+Integer.toString(min);
			else if(day==5)
				timee="Friday "+Integer.toString(hrs)+":"+Integer.toString(min);
			else if(day==6)
				timee="Saturday "+Integer.toString(hrs)+":"+Integer.toString(min);
			else if(day==7)
				timee="Sunday "+Integer.toString(hrs)+":"+Integer.toString(min);
			lecture.add(timee);
		}
		ArrayAdapter ad= new ArrayAdapter(this, R.layout.list_row, lecture);
		leclist.setAdapter(ad);
	}


}
