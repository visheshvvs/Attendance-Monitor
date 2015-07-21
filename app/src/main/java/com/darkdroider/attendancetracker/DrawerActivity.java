package com.darkdroider.attendancetracker;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.darkdroider.models.NavDrawerItem;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DrawerActivity extends FragmentActivity implements Home.onButtonClickListener {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private PendingIntent pendingIntent;
	private ActionBarDrawerToggle mDrawerToggle;
	Fragment fragment = null;
	static int total;
	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawer);
		
		
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		System.out.println(day+" " +month+" "+year);
		Calendar cala = Calendar.getInstance();
		cala.set(Calendar.HOUR_OF_DAY,22);
		cala.set(Calendar.MINUTE,00);
		cala.set(Calendar.SECOND, 00);
		cala.set(Calendar.DAY_OF_MONTH,day);
		cala.set(Calendar.MONTH,month);
		cala.set(Calendar.YEAR,year);
		if(cala.before(cal))
		{
			cala.add(Calendar.DAY_OF_YEAR,1);
			
		}
		SimpleDateFormat dfDate  = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date d1 = null;
		try {
			d1 = dfDate.parse(dfDate.format(cala.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	      Intent myIntent = new Intent(DrawerActivity.this, MyReceiver.class);
	      pendingIntent = PendingIntent.getBroadcast(DrawerActivity.this, 123456, myIntent,0);
	     
	      AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
	      alarmManager.setRepeating(AlarmManager.RTC, cala.getTimeInMillis(),60*1000, pendingIntent);
	      
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		// What's hot, We  will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(5, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(5, -1)));
		

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);
		
		if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.HONEYCOMB)
		{    
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setHomeButtonEnabled(true);
		}
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) 
			{
				if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.HONEYCOMB)
				{  
				getActionBar().setTitle(mTitle);
				}
				// calling onPrepareOptionsMenu() to show action bar icons
				supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) 
			{
				if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.HONEYCOMB)
				{  
				getActionBar().setTitle(mDrawerTitle);
				}
				// calling onPrepareOptionsMenu() to hide action bar icons
				supportInvalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
		Intent i = getIntent();
		if(i!=null && i.getIntExtra("notify", -1)==1453)
			displayView(2);
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	
	public void displayView(int position) {
		// update the main content by replacing fragments
		
		switch (position) {
		case 0:
			fragment = new Home();
			break;
		case 1:
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			alertDialogBuilder.setMessage("Choose one of the following options!?").setCancelable(false).setPositiveButton("New Sem",
					  new DialogInterface.OnClickListener() 
					{
					    public void onClick(DialogInterface dialog,int id) 
					    {
					    	DatabaseHandler db = new DatabaseHandler(DrawerActivity.this);
					    	db.deleteAllTables();
					    	DialogFragment newFragment = new DatePickerFragment();
						    newFragment.show(getSupportFragmentManager(), "Enter Sem Start Date");
						    
					    	
					    }
					  })
					.setNegativeButton("Edit Sem",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) 
					    {
					    	Intent intent=new Intent(DrawerActivity.this, NewEditActivity.class);
					    	startActivity(intent);
							//displayView(0);
					    }
					  });

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				try {
				      alertDialog.show();
				 } catch(Exception e){
				   // WindowManager$BadTokenException will be caught and the app would not display 
				   // the 'Force Close' message
				 }

//			Intent intent=new Intent(this, NewEditActivity.class);
//			startActivity(intent);
			break;

		case 2:
			fragment = new MarkAtt();
			break;
		case 3:
			fragment = new ViewTT();
			break;
		case 4:
			//fragment = new PagesFragment();
			break;
		case 5:
			DatabaseHandler db = new DatabaseHandler(DrawerActivity.this);
			total=db.getSubjectsCount();
			fragment= new ViewHistory();
			break;
		case 6:
			fragment = new BunkManager();

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.HONEYCOMB)
		{  
		getActionBar().setTitle(mTitle);
		}
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void openFragment(int pos) 
	{
		System.out.println(pos);
		displayView(pos);
		
	}
	

}
