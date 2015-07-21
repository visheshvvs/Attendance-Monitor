package com.darkdroider.attendancetracker;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomAdapterViewTt extends BaseAdapter {
	Context ctx;
	ArrayList<String> subjects;
	LinearLayout l;
	LayoutInflater lInflater;
	CustomAdapterViewTt(Context context,ArrayList<String> lectur)
	{
		ctx=context;
		subjects=lectur;
		lInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return subjects.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return subjects.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup group) {
		// TODO Auto-generated method stub
		View view = arg1;
		if (view == null) 
		{
			view = lInflater.inflate(R.layout.list_row, group , false);
		}
		TextView name = (TextView) view.findViewById(R.id.textView1);
		l = (LinearLayout)view.findViewById(R.id.listlayoutvaali);
		if(position==0)
			l.setBackgroundResource(R.drawable.listitemfirst);
		else if(position%2==0)
			l.setBackgroundResource(R.drawable.listitemdark);
		else
			l.setBackgroundResource(R.drawable.listitemlight);
		Typeface font = Typeface.createFromAsset(ctx.getAssets(), "Age.otf");  
		name.setTypeface(font);
		name.setText(subjects.get(position));
		
		return view;
	}

}
