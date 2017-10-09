package gemeenterotterdam.trillingmeterapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import static gemeenterotterdam.trillingmeterapp.R.id.add;

/**
 * Created by Marijn Otte on 28-9-2017.
 * Activity handles different fragements to swipe between graphs
 */

public class ScreenSlidePagerActivity extends FragmentActivity {
    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private StartFragment startFragment;
    private FdomGraphFragment fdomGraphFragment;
    private AcceleroGraphFragment acceleroGraphFragment;
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
        if(fdomGraphFragment != null) {
            fdomGraphFragment.update(fdom);
        }
    }

    public void updateAccelerationData(float[] maxAcceleration) {
        if(acceleroGraphFragment != null) {
            acceleroGraphFragment.update(maxAcceleration);
        }
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
            switch(position){
                case 0: return new StartFragment();
                case 1: return new AcceleroGraphFragment();
                case 2: return new FdomGraphFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            // save the appropriate reference depending on position
            switch (position) {
                case 0:
                    startFragment = (StartFragment) createdFragment;
                    break;
                case 1:
                    acceleroGraphFragment = (AcceleroGraphFragment) createdFragment;
                    break;
                case 2:
                    fdomGraphFragment = (FdomGraphFragment) createdFragment;
            }
            return createdFragment;
        }
    }
}
