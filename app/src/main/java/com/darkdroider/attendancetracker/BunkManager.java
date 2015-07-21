package com.darkdroider.attendancetracker;

import java.util.ArrayList;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.darkdroider.models.Subject;

public class BunkManager extends Fragment implements OnItemSelectedListener 
{
	static Spinner dropdown;
	DatabaseHandler db;
	ListView list;
	ArrayList<Subject> subjects;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		
		View rootView = inflater.inflate(R.layout.bunkmanager, container, false);
		list = (ListView)rootView.findViewById(R.id.listView1);
		dropdown = (Spinner)rootView.findViewById(R.id.spinner1);
		dropdown.setOnItemSelectedListener(this);
		TextView tv1 = (TextView)rootView.findViewById(R.id.textView1);
		
		Typeface font1 = Typeface.createFromAsset(getActivity().getAssets(), "SketchRockwell-Bold.ttf");
		tv1.setTypeface(font1);
		
		ArrayList<String> numbers = new ArrayList<String>();
		for(int i=1; i<=5; i++)
		{
			if(i==1)
				numbers.add(Integer.toString(i)+" lecture");
			else
				numbers.add(Integer.toString(i)+" lectures");
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, numbers);
		dropdown.setAdapter(adapter);
		db=new DatabaseHandler(getActivity());
		subjects = new ArrayList<Subject>();
		subjects = db.getAllSubjects();
		CustomBunkManagerAdapter ad = new CustomBunkManagerAdapter(getActivity(),subjects);
		list.setAdapter(ad);
		return rootView;
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) 
	{
		CustomBunkManagerAdapter ad = new CustomBunkManagerAdapter(getActivity(),subjects);
		list.setAdapter(ad);
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) 
	{
		// TODO Auto-generated method stub
		
	}
	

}
