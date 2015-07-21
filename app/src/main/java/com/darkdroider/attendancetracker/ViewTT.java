package com.darkdroider.attendancetracker;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewTT extends Fragment
{
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		
		rootView = inflater.inflate(R.layout.activity_view_tt, container, false);
		mSectionsPagerAdapter = new SectionsPagerAdapter(this.getFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK); 
        if(day==1)
        	day=6;
        else
        	day-=2;
        mViewPager.setCurrentItem(day);
        
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
            Fragment fragment = new DummySectionFragment();
            Bundle args = new Bundle();
            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 7 total pages.
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
            case 0:
              return "Monday";
          case 1:
              return "Tuesday";
          case 2:
              return "Wednesday";
          case 3:
              return "Thursday";
          case 4:
              return "Friday";
          case 5:
              return "Saturday";
         
            }
            return null;
        }
    
    }

	

}
