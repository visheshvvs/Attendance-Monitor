package com.darkdroider.attendancetracker;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.view.Menu;
import android.view.MenuItem;

public class NewEditActivity extends FragmentActivity implements OnBackStackChangedListener {

	private Handler mHandler=new Handler();
	private boolean backshow=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.neweditactivity);
		System.out.println("saved instance state ===  "+savedInstanceState);
		if(savedInstanceState==null)
		{
			getSupportFragmentManager().beginTransaction().add(R.id.mycontainer,new EditTT()).commit();
		}
		else{
			backshow = (getSupportFragmentManager().getBackStackEntryCount() > 0);
			System.out.println("CHALA BHAIYOOONN CHALLLAAAA   !!!!!1");
		}
		getSupportFragmentManager().addOnBackStackChangedListener(this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
        //getMenuInflater().inflate(R.menu.activity_screen_slide, menu);
//        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
//                (backshow?R.string.back:R.string.action_finish));
//        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//		MenuItem item = menu.add(Menu.NONE, R.id.action_flip, Menu.NONE,
//                backshow
//                        ? R.string.action_photo
//                        : R.string.action_info);
//        item.setIcon(backshow
//                ? R.drawable.ic_action_photo
//                : R.drawable.ic_action_info);
//        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case android.R.id.home:
			//NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            return true;
		case R.id.action_next:
			flipCard();
		}
		// TODO Auto-generated method stub
//		switch (item.getItemId()) {
//        case android.R.id.home:
//            // Navigate "up" the demo structure to the launchpad activity.
//            // See http://developer.android.com/design/patterns/navigation.html for more.
//            NavUtils.navigateUpTo(this, new Intent(this, DrawerActivity.class));
//            return true;
//
//        case R.id.action_flip:
//            flipCard();
//            return true;
//            }

        return super.onOptionsItemSelected(item);
	}
	private void flipCard() {
		// TODO Auto-generated method stub
		if(backshow)
		{
			getSupportFragmentManager().popBackStack();
			return;
		}
		backshow=true;
		
		getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein,R.anim.fadeout).replace(R.id.mycontainer, new EditTimings()).addToBackStack(null).commit();
		//getFragmentManager().beginTransaction().replace(R.id.container, new EditTimings()).addToBackStack(null).commit();
		mHandler.post(new Runnable() {
            @Override
            public void run() {
                supportInvalidateOptionsMenu();
            }
        });
	}
	
	@Override
	public void onBackStackChanged() {
		// TODO Auto-generated method stub
		backshow=(getSupportFragmentManager().getBackStackEntryCount() > 0);
		supportInvalidateOptionsMenu();
		
	}
	

}
