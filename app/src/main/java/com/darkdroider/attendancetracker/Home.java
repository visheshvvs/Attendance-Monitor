package com.darkdroider.attendancetracker;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.darkdroider.models.Subject;

public class Home extends Fragment implements OnClickListener
{
	onButtonClickListener callback;
	public interface onButtonClickListener{
		public void openFragment(int pos);
	}
	 @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        
	        // This makes sure that the container activity has implemented
	        // the callback interface. If not, it throws an exception
	        try {
	            callback = (onButtonClickListener) activity;
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement OnHeadlineSelectedListener");
	        }
	    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		
		View rootView = inflater.inflate(R.layout.activity_home, container, false);
		DatabaseHandler db = new DatabaseHandler(getActivity());
		ArrayList<Subject> subs = new ArrayList<Subject>();
		subs = db.getAllSubjects();
		CustomAdapterHome ad= new CustomAdapterHome(getActivity(), subs);
		ListView list = (ListView)rootView.findViewById(R.id.listView1);
		list.setAdapter(ad);
		TextView tv = new TextView(getActivity());
		LinearLayout ll = (LinearLayout)rootView.findViewById(R.id.llhome);
		Button b1 = (Button)rootView.findViewById(R.id.button1);
		Button b2 = (Button)rootView.findViewById(R.id.button2);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
//		if(subs.size()==0)
//		{
//			tv.setText("Start over, by chosing the 'New Sem' option from the menu. \n Swipe right from the left edge" );
//			ll.addView(tv);
//		}
		return rootView;
	}

	@Override
	public void onClick(View v) 
	{
		if(v.getId()==R.id.button2)
		{
			callback.openFragment(6);
		}
		else 
		{
			callback.openFragment(3);
		}
		
	}

}
