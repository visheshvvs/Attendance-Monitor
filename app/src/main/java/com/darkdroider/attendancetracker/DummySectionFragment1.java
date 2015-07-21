package com.darkdroider.attendancetracker;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.darkdroider.models.Subject;

public class DummySectionFragment1 extends Fragment
{
	public static final String ARG_SECTION_NUMBER = "section_number";
	int flag=0;
	public DummySectionFragment1() {
    }
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) 
	 {
		 flag=getArguments().getInt(ARG_SECTION_NUMBER);
		 ArrayList<Subject> subs = new ArrayList<Subject>();
		 DatabaseHandler db = new DatabaseHandler(getActivity());
		 subs=db.getAllSubjects();
		 ArrayList<String> finalList = db.getAllHistory(subs.get(flag).getName());
     	ListView list = new ListView(getActivity());
     	ArrayAdapter ad= new ArrayAdapter(getActivity(),R.layout.history_row, finalList);
     	list.setAdapter(ad);
     	return list;
	 }
	
}
