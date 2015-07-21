package com.darkdroider.attendancetracker;



import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.darkdroider.models.Subject;

public class ViewHistory extends Fragment
{
	SectionsPagerAdapter mSectionsPagerAdapter;
	static DatabaseHandler  db;

	//int count = db.getSubjectsCount();
	ViewPager mViewPager;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		
		View rootView = inflater.inflate(R.layout.activity_view_tt, container, false);
		mSectionsPagerAdapter = new SectionsPagerAdapter(this.getFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        db=new DatabaseHandler(getActivity());
        return rootView;
	}
	public static class SectionsPagerAdapter extends FragmentStatePagerAdapter 
	{
		

        public SectionsPagerAdapter(FragmentManager fm) 
        {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) 
        {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment = new DummySectionFragment1();
            Bundle args = new Bundle();
            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() 
        {
          
          return DrawerActivity.total;
        }

        @Override
        public CharSequence getPageTitle(int position) 
        {
        	
            ArrayList<Subject> list = new ArrayList<Subject>();
            list=db.getAllSubjects();
            if(list.size()==0)
            	return null;
            return list.get(position).getName();
        }
    
    }


}
