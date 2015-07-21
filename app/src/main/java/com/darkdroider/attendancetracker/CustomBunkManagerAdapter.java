package com.darkdroider.attendancetracker;

import java.util.ArrayList;

import com.darkdroider.models.Subject;

import android.content.Context;
import android.graphics.Typeface;
import android.os.DropBoxManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomBunkManagerAdapter extends BaseAdapter
{
	Context ctx;
	ArrayList<Subject> subjects;
	LayoutInflater lInflater;
	CustomBunkManagerAdapter(Context context, ArrayList<Subject> lectur)
	{
		ctx=context;
		subjects=lectur;
		lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	
	@Override
	public int getCount() 
	{
		return subjects.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return subjects.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		
		View view = convertView;
		if (view == null) 
		{
			view = lInflater.inflate(R.layout.list_home_item, parent, false);
		}
		Subject s = (Subject)getItem(position);
		LinearLayout l = (LinearLayout)view.findViewById(R.id.listlayout);
		if(position==0)
			l.setBackgroundResource(R.drawable.listitemfirst);
		else if(position%2==0)
			l.setBackgroundResource(R.drawable.listitemdark);
		else
			l.setBackgroundResource(R.drawable.listitemlight);
		int p = s.getPresents();
		int a = s.getAbsents();
		float pf = (float) (p);
		float af = (float)(a);
		TextView name = (TextView) view.findViewById(R.id.textView1);
		Typeface font = Typeface.createFromAsset(ctx.getAssets(), "Age.otf");  
		name.setTypeface(font);
		name.setText(s.getName());
		TextView percent = (TextView) view.findViewById(R.id.textView2);
		Typeface font1 = Typeface.createFromAsset(ctx.getAssets(), "SketchRockwell-Bold.ttf");  
		percent.setTypeface(font1);
		Float f = pf/(pf+af+Float.parseFloat(BunkManager.dropdown.getSelectedItem().toString().substring(0, 1)))*100;
		float mkm= (float)(Math.round(f*100))/100;
		percent.setText(Float.toString(mkm)+"%");
		ImageView img = (ImageView)view.findViewById(R.id.imageView2);
		if(f>=80)
		{
			
			img.setBackgroundResource(R.drawable.greater80);
		}
		else if(f<80 && f>=75)
		{
			
			img.setBackgroundResource(R.drawable.danger);
		}
		else if(f<75)
		{
			
			img.setBackgroundResource(R.drawable.shorthogai);
		
		}
		
		return view;
	}
	
}
