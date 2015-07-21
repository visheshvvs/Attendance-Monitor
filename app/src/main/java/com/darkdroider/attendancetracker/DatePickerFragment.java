package com.darkdroider.attendancetracker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment
implements DatePickerDialog.OnDateSetListener 
{
	File  filesdcard=Environment.getExternalStorageDirectory();
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
final Calendar c = Calendar.getInstance();

int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH);
int day = c.get(Calendar.DAY_OF_MONTH);
// Create a new instance of DatePickerDialog and return it
DatePickerDialog d = new DatePickerDialog(getActivity(), this, year, month, day);
d.setTitle("Enter SEM start date");
return d;
}


public void onDateSet(DatePicker view, int year, int month, int day) 
{
	String date = Integer.toString(day)+"-"+Integer.toString(month+1)+"-"+Integer.toString(year);
	File file = new File(filesdcard,"hello.txt");
	try 
	{
		file.createNewFile();
		FileOutputStream output = new FileOutputStream(file);
		output.write(date.getBytes());
		output.close();
		Intent intent=new Intent(getActivity(), NewEditActivity.class);
    	startActivity(intent);
		
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
}
