package com.darkdroider.attendancetracker;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
                            
 
public class MyAlarmService extends Service 
{
	int notificationID = 1;
	File  filesdcard=Environment.getExternalStorageDirectory();
   private NotificationManager mManager;
 
    @Override
    public IBinder onBind(Intent arg0)
    {
       // TODO Auto-generated method stub
        return null;
    }
 
    @Override
    public void onCreate() 
    {
       // TODO Auto-generated method stub  
       super.onCreate();
    }
 
   
    @Override
    	public int onStartCommand(Intent intent, int flags, int startId) 
    {
    		
File file = new File(filesdcard,"hello.txt");
		
		
		StringBuilder builder=null;
		String line = null;
		try {
			
		FileInputStream input = new FileInputStream(file);
	
		
		 BufferedReader	reader = new BufferedReader(new FileReader(file));
	        builder = new StringBuilder();
	        
	        while ((line = reader.readLine()) != null) 
	        {
	            builder.append(line);
			}
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	String date = builder.toString();	
	
	SimpleDateFormat dfDate  = new SimpleDateFormat("dd-MM-yyyy");
    java.util.Date d = null;
    java.util.Date d1 = null;
    Calendar cal = Calendar.getInstance();
    try {
        d = dfDate.parse(date);
        d1 = dfDate.parse(dfDate.format(cal.getTime()));
    } catch (java.text.ParseException e) {
        e.printStackTrace();
    }
   
    Calendar cals = Calendar.getInstance();
    cals.setTime(d);
    Calendar cale = Calendar.getInstance();
    cale.setTime(d1);
    cale.add(Calendar.DAY_OF_YEAR, 1);
    	
    	if(cals.before(cale))
    	{
    	Intent i = new Intent(this.getApplicationContext(), DrawerActivity.class);
    	i.putExtra("notificationID", notificationID);
    	PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
    	mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
    	Intent intent1 = new Intent(this.getApplicationContext(),DrawerActivity.class);
    	intent1.putExtra("notify", 1453);
        
        Notification notification = new Notification(R.drawable.ic_launcher,"Mark Your Attendance", System.currentTimeMillis());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
  
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
        notification.setLatestEventInfo(this.getApplicationContext(), "Mark Your Attendance", "This is a test message!", pendingNotificationIntent);
        notification.vibrate = new long[] { 100, 250, 100, 500};
        Uri ringURI =
        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notification.sound = ringURI;
        mManager.notify(0, notification);
    	}
    	
    		return super.onStartCommand(intent, flags, startId);
    }
 
 
    @Override
    public void onDestroy() 
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
 
}
