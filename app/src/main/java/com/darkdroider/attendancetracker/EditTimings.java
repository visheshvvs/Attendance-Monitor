package com.darkdroider.attendancetracker;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.darkdroider.models.Subject;
import com.darkdroider.models.Timing;

public class EditTimings extends Fragment implements OnClickListener, OnItemSelectedListener, OnCheckedChangeListener {
	Spinner dropdown;
	RadioGroup days;
	RadioGroup type;
	static Button addLec,time;
	DatabaseHandler db1;
	int lectime=110000;
	int day=1;
	int typeofclass=1;
	View rootView;
	ArrayList<Timing> timings;
	ArrayAdapter<String> adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootView=inflater.inflate(R.layout.activity_add_lectures, container,false);
		dropdown = (Spinner)rootView.findViewById(R.id.dropdownlist);
		days = (RadioGroup)rootView.findViewById(R.id.radioGroup1);
		days.setOnCheckedChangeListener(this);
		type = (RadioGroup)rootView.findViewById(R.id.radioGroup2);
		type.setOnCheckedChangeListener(this);
		addLec = (Button)rootView.findViewById(R.id.addLecture);
		addLec.setOnClickListener(this);
		dropdown.setOnItemSelectedListener(this);
		db1 = new DatabaseHandler(getActivity());
		time = (Button)rootView.findViewById(R.id.time);
		time.setOnClickListener(this);
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Subject> subs = new ArrayList<Subject>();
		System.out.println("DROPDOWN DI ID MILGI");
		subs=db1.getAllSubjects();
		for(int i=0;i<subs.size();i++)
		{

    		System.out.println("WE HAVE ENTERED FOR LOOP BITCHEZZZZZ");
			names.add(subs.get(i).getName());
			System.out.println(subs.get(i).getName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, names);
		dropdown.setAdapter(adapter);
		return rootView;
	}
//	else if(mPageNumber==2)
//	{
//		rootView=inflater.inflate(R.layout.finish, container,false);
//	}
	

/**
 * Returns the page number represented by this fragment object.
 */
//public void updateDropDown()
//{
//	ArrayList<String> names = new ArrayList<String>();
//	ArrayList<Subject> subs = new ArrayList<Subject>();
//	System.out.println("WE HAVE ENTERED 3ND FRAGMENT BITCHEZZZZZ");
//	subs=db.getAllSubjects();
//	for(int i=0;i<subs.size();i++)
//	{
//
//		System.out.println("WE HAVE ENTERED FOR LOOP BITCHEZZZZZ");
//		names.add(subs.get(i).getName());
//		System.out.println(subs.get(i).getName());
//	}
//	adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names);
	//dropdown.setAdapter(adapter);
//}
@Override
public void onCheckedChanged(RadioGroup arg0, int checkedId) {
	// TODO Auto-generated method stub
	if(checkedId==R.id.radio0)
		day=1;
	else if(checkedId==R.id.radio1)
		day=2;
	else if(checkedId==R.id.radio2)
		day=3;
	else if(checkedId==R.id.radio3)
		day=4;
	else if(checkedId==R.id.radio4)
		day=5;
	else if(checkedId==R.id.radio5)
		day=6;
	else if(checkedId==R.id.radiol)
		typeofclass=1;
	else if(checkedId==R.id.radiot)
		typeofclass=2;
	else if(checkedId==R.id.radiop)
		typeofclass=3;
	
}

@Override
public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
		long arg3) {
	// TODO Auto-generated method stub
	String name = arg0.getSelectedItem().toString();
	timings = new ArrayList<Timing>();
	timings = db1.getAllTimings(name);
	updateList();	
}

@Override
public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	if(v.getId()==R.id.time)
	{
		showTimePickerDialog(v);
	}
	if(v.getId()==R.id.addLecture)
	{
		int temp;
		temp=typeofclass*100000;
		temp+=(day*10000);
		temp+=(TimePickerFragment.hours*100);
		temp+=TimePickerFragment.minutes;
		String name = dropdown.getSelectedItem().toString();
		Timing timing = new Timing(temp);
		db1.addTiming(name, timing);
		updateList();
		temp=lectime;
	}
}
public static void setTime()
{
	time.setText(TimePickerFragment.hours+":"+TimePickerFragment.minutes);
}

private void updateList() {
	// TODO Auto-generated method stub
	ArrayList<Timing> l = new ArrayList<Timing>();
	String timee="";
	l=db1.getAllTimings(dropdown.getSelectedItem().toString());
	final ViewGroup mview10=(ViewGroup)rootView.findViewById(R.id.container10);
	mview10.removeAllViews();
	ArrayList<String> lecture = new ArrayList<String>();
	for(int i=0; i<l.size();i++)
	{
		
		int t=l.get(i).getTiming();
		int min = t%100;
		t=t/100;
		int hrs=t%100;
		t=t/100;
		int day = t%10;
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
		int type = t/10;
		if(type==1)
			timee=timee+" Lec.";
		else if(type==2)
			timee=timee+" Tut.";
		else if(type==3)
			timee=timee+" Lab";
		lecture.add(timee);
		
		
		final ViewGroup newView10 = (ViewGroup) LayoutInflater.from(getActivity()).inflate(
                R.layout.list_item_example, mview10, false);
		newView10.setBackgroundResource(R.drawable.viewgroupbglight);
		newView10.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remove the row from its parent (the container view).
                // Because mContainerView has android:animateLayoutChanges set to true,
                // this removal is automatically animated.
                mview10.removeView(newView10);
                // If there are no rows remaining, show the empty view.
//                if (mview10.getChildCount() == 0) {
//                    rootView.findViewById(R.id.hello).setVisibility(View.VISIBLE);
//                }
            }
        });
		((TextView)newView10.findViewById(android.R.id.text1)).setText(timee);
		mview10.addView(newView10, 0);
	}
	

	
	//updateDropDown();
}
	//ArrayAdapter ad= new ArrayAdapter(getActivity(), R.layout.addlectlayout, lecture);
	//listTime.setAdapter(ad);


private void showTimePickerDialog(View v) {
	// TODO Auto-generated method stub
	DialogFragment newFragment = new TimePickerFragment();
	newFragment.show(getFragmentManager(), "TimePicker");
}
	}
