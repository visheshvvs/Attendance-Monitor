package com.darkdroider.attendancetracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ListSub extends Activity 
{
	DatabaseHandler db;
	LinearLayout outer;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_sub);
		outer = (LinearLayout)findViewById(R.id.LinearLayout1);
		db=new DatabaseHandler(this);
		int count = db.getSubjectsCount();
		for(int i=1;i<=count;i++)
		{
			LinearLayout l = new LinearLayout(this);
			l.setOrientation(LinearLayout.HORIZONTAL);
			final Button sub;
			sub = new Button(this);
			
			sub.setText(db.getSubject(i).getName());
			
			sub.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{
					Intent intent = new Intent(ListSub.this,Schedule.class);
					intent.putExtra("name", sub.getText().toString());
					startActivity(intent);
					
				}
			});

			l.addView(sub);
			
			outer.addView(l);
		}
	}


}
