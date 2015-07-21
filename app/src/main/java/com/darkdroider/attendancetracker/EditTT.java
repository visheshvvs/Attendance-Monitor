package com.darkdroider.attendancetracker;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.darkdroider.models.Subject;

public class EditTT extends Fragment {
	ViewGroup mView;
	ArrayList<Subject> subj;
	DatabaseHandler db;
	Button img;
	int i;
	EditText search;
	String subjstr;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.edittt, container,false);
		db=new DatabaseHandler(getActivity());
		subj=db.getAllSubjects();
		mView=(ViewGroup) rootView.findViewById(R.id.container1);
		
		
		search=(EditText) rootView.findViewById(R.id.editsearch);
		img=(Button) rootView.findViewById(R.id.plussign1);
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				// TODO Auto-generated method stub
				subjstr=search.getText().toString();
				if(subjstr.isEmpty())
				{
					Toast.makeText(getActivity(), "ENTER A SUBJECT", Toast.LENGTH_SHORT).show();
				}
				else {
					Subject subject = new Subject(search.getText().toString().replace(" ", "_"));
					db.addSubject(subject);	
		    		System.out.println("SUBJECT ENTERED BITCHESS BITCHEZZZZZ");
		    		final ViewGroup newView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(
		                    R.layout.list_item_example, mView, false);
					newView.setBackgroundResource(R.drawable.viewgrouplistitemdark);
		    		((TextView)newView.findViewById(android.R.id.text1)).setText(subjstr);
		    		mView.addView(newView, 0);
		    		search.setText("");
		    		newView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
		              @Override
		              public void onClick(View view) {
		                  // Remove the row from its parent (the container view).
		                  // Because mContainerView has android:animateLayoutChanges set to true,
		                  // this removal is automatically animated.
		                  mView.removeView(newView);
		                  db.deletesubject(((TextView)newView.findViewById(android.R.id.text1)).getText().toString());
		                  // If there are no rows remaining, show the empty view.
//		                  if (mview.getChildCount() == 0) {
//		                      rootView.findViewById(R.id.hello).setVisibility(View.VISIBLE);
//		                  }
		              }
		          });
				//updateDropDown();
			}
				
			}
		});
//		newView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Remove the row from its parent (the container view).
//                // Because mContainerView has android:animateLayoutChanges set to true,
//                // this removal is automatically animated.
//                mView.removeView(newView);
//                db.deletesubject(((TextView)newView.findViewById(android.R.id.text1)).getText().toString());
//                // If there are no rows remaining, show the empty view.
////                if (mview.getChildCount() == 0) {
////                    rootView.findViewById(R.id.hello).setVisibility(View.VISIBLE);
////                }
//            }
//        });
		
		for(i=0;i<subj.size();i++)
		{
			System.out.println(subj.get(i).getName());
			final ViewGroup newView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(
	                R.layout.list_item_example, mView, false);
			newView.setBackgroundResource(R.drawable.viewgroupbglight);
		((TextView)newView.findViewById(android.R.id.text1)).setText(subj.get(i).getName());
		mView.addView(newView, 0);
		newView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Remove the row from its parent (the container view).
                // Because mContainerView has android:animateLayoutChanges set to true,
                // this removal is automatically animated.
                mView.removeView(newView);
                db.deletesubject(((TextView)newView.findViewById(android.R.id.text1)).getText().toString());
		}
		});
		}
            
		
		return rootView;
		
	}
	

}
