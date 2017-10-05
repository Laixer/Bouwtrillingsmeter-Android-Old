package gemeenterotterdam.trillingmeterapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import static gemeenterotterdam.trillingmeterapp.R.id.add;

/**
 * Created by Marijn Otte on 28-9-2017.
 * Activity handles different fragements to swipe between graphs
 */

public class ScreenSlidePagerActivity extends FragmentActivity {
    private static final int NUM_PAGES = 6;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private StartFragment startFragment;
    private AcceleroGraphFragment acceleroFragment;
    private AcceleroMeter acceleroMeter;
    private GyroscopeMeter gyroscopeMeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        acceleroMeter = new AcceleroMeter(this);
        gyroscopeMeter = new GyroscopeMeter(this);
        startFragment = new StartFragment();
        acceleroFragment = new AcceleroGraphFragment();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    public void updateFdom(Fdom fdom) {
        acceleroFragment.update(fdom);
    }


    /**
     * Adapter to handle different fragments
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return startFragment;
            }
            if (position == 1){
                return acceleroFragment;
            }
            else{
                return new AcceleroGraphFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


}
