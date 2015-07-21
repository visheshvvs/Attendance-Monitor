package com.darkdroider.attendancetracker;

import java.util.ArrayList;

import com.darkdroider.models.ListItem;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter implements OnClickListener
{
	ArrayList<String> p,a,c;
	Context ctx;
	LayoutInflater lInflater;
	ArrayList<ListItem> lectures;
	RadioButton pr=null,ar=null,cr=null;
	RadioGroup rg;
	ImageView img;
	
	CustomAdapter(Context context, ArrayList<ListItem> lectur)
	{
		ctx=context;
		lectures=lectur;
		lInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lectures.size();
	}

	@Override
	public Object getItem(int arg0) {
		return lectures.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
//	@Override
//    public int getViewTypeCount() {                 
//                  //Count=Size of ArrayList.
//        return lectures.size();
//    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View view = convertView;
//		 for(int i=0;i<lectures.size();i++)
//	        	System.out.print(flag[i]+" ");
//	        System.out.println("");
		if (view == null) 
		{
			view = lInflater.inflate(R.layout.list_row_mark, parent, false);
		}
		ListItem l = (ListItem)getItem(position);
		((TextView) view.findViewById(R.id.listlec)).setText(l.text);
		rg = (RadioGroup) view.findViewById(R.id.radioGroup1);
		pr=(RadioButton)view.findViewById(R.id.present);
		ar=(RadioButton)view.findViewById(R.id.absent);
		cr=(RadioButton)view.findViewById(R.id.cancelled);
		img=(ImageView)view.findViewById(R.id.image1);
		ar.setOnClickListener(this);
		cr.setOnClickListener(this);
		pr.setOnClickListener(this);
		pr.setTag(position);
		ar.setTag(position);
		cr.setTag(position);
		if(l.present==true)
		{
			pr.setChecked(true);
			ar.setChecked(false);
			cr.setChecked(false);
			img.setBackgroundResource(R.drawable.tick);
			notifyDataSetChanged();
		}
		else if(l.absent==true)
		{
			pr.setChecked(false);
			ar.setChecked(true);
			cr.setChecked(false);
			img.setBackgroundResource(R.drawable.cross);
			notifyDataSetChanged();
		}
		else if(l.cancelled==true)
		{
			pr.setChecked(false);
			ar.setChecked(false);
			cr.setChecked(true);
			img.setBackgroundResource(0);
			notifyDataSetChanged();
		}
		else
		{
			rg.clearCheck();
			notifyDataSetChanged();
			
		}
		
		
		return view;
	}
	
	ListItem getListItem(int position)
	{
		return (ListItem)getItem(position);
	}
	public int[] getList()
	{
		int flag[] = new int[lectures.size()];
		for(int i=0;i<lectures.size();i++)
		{
			if(getListItem(i).present==true)
				flag[i]=1;
			else if(getListItem(i).absent==true)
				flag[i]=2;
			else if(getListItem(i).cancelled==true)
				flag[i]=3;
			
		}
		return flag;
			
	}
	@Override
	public void onClick(View v) 
	{
		if(v.getId()==R.id.present)
		{
			getListItem((Integer)v.getTag()).setPresent();
			notifyDataSetChanged();
			
		}
		else if(v.getId()==R.id.absent)
		{
			getListItem((Integer)v.getTag()).setAbsent();
			notifyDataSetChanged();
			
		}
		else if(v.getId()==R.id.cancelled)
		{
			getListItem((Integer)v.getTag()).setCancelled();
			notifyDataSetChanged();
		}
		
	}
	

}
