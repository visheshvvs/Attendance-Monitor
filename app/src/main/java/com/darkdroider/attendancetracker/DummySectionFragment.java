package com.darkdroider.attendancetracker;


import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.darkdroider.models.Subject;
import com.darkdroider.models.Timing;


public class DummySectionFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
	int flag=0;
    public static final String ARG_SECTION_NUMBER = "section_number";

    public DummySectionFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
        
        flag=getArguments().getInt(
                ARG_SECTION_NUMBER);
        if(flag==1)
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
        			if((timings.get(j).getTiming()/10000)%10==1)
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
        				String t = hr+":"+min+" "+allsubs.get(i).getName();
        				if(timings.get(j).getTiming()/100000==1)
        					t=t+" Lec.";
        				else if(timings.get(j).getTiming()/100000==2)
        					t=t+" Tut.";
        				else if(timings.get(j).getTiming()/100000==3)
        					t=t+" Lab";
        				times.add(t);
        				
        			}
        		}
        	}
        	ArrayList<String> finalList = sort(times);
        	ListView list = new ListView(getActivity());
        	CustomAdapterViewTt ad= new CustomAdapterViewTt(getActivity(), finalList);
        	list.setAdapter(ad);
        	AnimationSet set1 = new AnimationSet(true);
        	return list;
        	
        	
        }
        if(flag==2)
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
        			if((timings.get(j).getTiming()/10000)%10==2)
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
        				String t = hr+":"+min+" "+allsubs.get(i).getName();
        				if(timings.get(j).getTiming()/100000==1)
        					t=t+" Lec.";
        				else if(timings.get(j).getTiming()/100000==2)
        					t=t+" Tut.";
        				else if(timings.get(j).getTiming()/100000==3)
        					t=t+" Lab";
        				times.add(t);
        				
        			}
        		}
        	}
        	ArrayList<String> finalList = sort(times);
        	ListView list = new ListView(getActivity());
        	CustomAdapterViewTt ad= new CustomAdapterViewTt(getActivity(), finalList);
        	list.setAdapter(ad);
        	return list;
        	
        
        }
        if(flag==3)
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
        			if((timings.get(j).getTiming()/10000)%10==3)
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
        				String t = hr+":"+min+" "+allsubs.get(i).getName();
        				if(timings.get(j).getTiming()/100000==1)
        					t=t+" Lec.";
        				else if(timings.get(j).getTiming()/100000==2)
        					t=t+" Tut.";
        				else if(timings.get(j).getTiming()/100000==3)
        					t=t+" Lab";
        				times.add(t);
        				
        			}
        		}
        	}
        	ArrayList<String> finalList = sort(times);
        	ListView list = new ListView(getActivity());
        	CustomAdapterViewTt ad= new CustomAdapterViewTt(getActivity(), finalList);
        	list.setAdapter(ad);
        	return list;
        }
        if(flag==4)
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
        			if((timings.get(j).getTiming()/10000)%10==4)
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
        				String t = hr+":"+min+" "+allsubs.get(i).getName();
        				if(timings.get(j).getTiming()/100000==1)
        					t=t+" Lec.";
        				else if(timings.get(j).getTiming()/100000==2)
        					t=t+" Tut.";
        				else if(timings.get(j).getTiming()/100000==3)
        					t=t+" Lab";
        				times.add(t);
        				
        			}
        		}
        	}
        	ArrayList<String> finalList = sort(times);
        	ListView list = new ListView(getActivity());
        	CustomAdapterViewTt ad= new CustomAdapterViewTt(getActivity(), finalList);
        	list.setAdapter(ad);
        	return list;
        	
        }
        if(flag==5)
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
        			if((timings.get(j).getTiming()/10000)%10==5)
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
        				String t = hr+":"+min+" "+allsubs.get(i).getName();
        				if(timings.get(j).getTiming()/100000==1)
        					t=t+" Lec.";
        				else if(timings.get(j).getTiming()/100000==2)
        					t=t+" Tut.";
        				else if(timings.get(j).getTiming()/100000==3)
        					t=t+" Lab";
        				times.add(t);
        				
        			}
        		}
        	}
        	ArrayList<String> finalList = sort(times);
        	ListView list = new ListView(getActivity());
        	CustomAdapterViewTt ad= new CustomAdapterViewTt(getActivity(), finalList);
        	list.setAdapter(ad);
        	return list;
        }
        if(flag==6)
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
        			if((timings.get(j).getTiming()/10000)%10==6)
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
        				String t = hr+":"+min+" "+allsubs.get(i).getName();
        				if(timings.get(j).getTiming()/100000==1)
        					t=t+" Lec.";
        				else if(timings.get(j).getTiming()/100000==2)
        					t=t+" Tut.";
        				else if(timings.get(j).getTiming()/100000==3)
        					t=t+" Lab";
        				times.add(t);
        				
        			}
        		}
        	}
        	ArrayList<String> finalList = sort(times);
        	ListView list = new ListView(getActivity());
        	CustomAdapterViewTt ad= new CustomAdapterViewTt(getActivity(), finalList);
        	list.setAdapter(ad);
        	return list;
        }
        
       return null;
    }
}


