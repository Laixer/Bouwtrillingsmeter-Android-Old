package gemeenterotterdam.trillingmeterapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Marijn Otte on 28-9-2017.
 * Activity handles different fragements to swipe between graphs and dashboard
 */

public class ScreenSlidePagerActivity extends AppCompatActivity{
    private static final int NUM_PAGES = 6;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private NumericalFragment numericalFragment;
    private FdomGraphFragment fdomGraphFragment;
    private AcceleroGraphFragment acceleroGraphFragment;
    private VelocityGraphFragment velocityGraphFragment;
    private VfGraphFragment vfGraphFragment;
    private VFdomGraphFragment vfdomGraphFragment;
    private AcceleroMeter acceleroMeter;
    private boolean onForeground;

    /**
     *
     * @param savedInstanceState
     * Dashboard is first fragment showed
     * pager used to swipe between fragments
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        getSupportActionBar().setTitle(getResources().getString(R.string.Dashboard));
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //not used
            }

            /**
             *
             * @param position position of selected fragment.
             *                 Set title of corresponding actionbar
             */
            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:
                        getSupportActionBar().setTitle(getResources().getString(R.string.Dashboard));
                        break;
                    case 1:
                        getSupportActionBar().setTitle(getResources().getString(R.string.accelerationgraph));
                        break;
                    case 2:
                        getSupportActionBar().setTitle(getResources().getString(R.string.fdomgraph));
                        break;
                    case 3:
                        getSupportActionBar().setTitle(getResources().getString(R.string.velocitygraph));
                        break;
                    case 4:
                        getSupportActionBar().setTitle(getResources().getString(R.string.vfgraph));
                        break;
                    case 5:
                        getSupportActionBar().setTitle(getResources().getString(R.string.vfdomgraph));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                    //not used
            }
        });
        acceleroMeter = new AcceleroMeter(this);
    }

    /**
     * function used to determine if application is on foreground
     */
    @Override
    public void onResume(){
        onForeground = true;
        super.onResume();

    }

    /**
     * function used to determine if application is on foreground
     */
    public void onPause(){
        onForeground = false;
        super.onPause();
    }

    /**
     * The following update functions update the data of the dashboard and graphs
     * when new data is gathered / calculated
     */
    public void updateFdom(Fdom fdom) {
        if(fdomGraphFragment != null) {
            fdomGraphFragment.update(fdom);
        }
        if(velocityGraphFragment != null) {
            velocityGraphFragment.update(fdom);
        }
        if(numericalFragment != null){
            numericalFragment.updateFdomData(fdom);
        }

        if(vfdomGraphFragment != null){
            vfdomGraphFragment.update(fdom);
        }


        /**
         * If dominant frequency exceeded and application on foreground, show toast
         */
        if(onForeground && (fdom.exceed[0] || fdom.exceed[1] || fdom.exceed[2])){
            Toast.makeText(this, "OVERSCHRIJDING", Toast.LENGTH_LONG).show();
        }
    }


    public void updateAccelerationData(float[] maxAcceleration) {
        if(acceleroGraphFragment != null) {
            acceleroGraphFragment.update(maxAcceleration);
        }
        if(numericalFragment != null){
            numericalFragment.updateAccelarationData(maxAcceleration);
        }
    }


    public void updateVelocityData(float[] maxVelocity) {
        if(numericalFragment != null){
            numericalFragment.updateVelocityData(maxVelocity);
        }
    }


    public void updateFrequencyData(int[] maxFrequency) {
        if(numericalFragment != null){
            numericalFragment.updateFrequencyData(maxFrequency);
        }
    }

    public void updateVFData(ArrayList<DataPoint<int[]>> velocityFrequency){
        if(vfGraphFragment != null){
            vfGraphFragment.update(velocityFrequency);
        }
    }

    /**
     * Adapter to handle different fragments
     */
    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new NumericalFragment();
                case 1:
                    return new AcceleroGraphFragment();
                case 2:
                    return new FdomGraphFragment();
                case 3:
                    return new VelocityGraphFragment();
                case 4:
                    return new VfGraphFragment();
                case 5:
                    return new VFdomGraphFragment();
            }
            return null;
        }

        /**
         *
         * @return number of fragments
         */
        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        /**
         *
         * @param container
         * @param position of current fragment
         * @return fragment corresponding to position
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            // save the appropriate reference depending on position
            switch (position) {
                case 0:
                    numericalFragment = (NumericalFragment) createdFragment;
                    break;
                case 1:
                    acceleroGraphFragment = (AcceleroGraphFragment) createdFragment;
                    break;

                case 2:
                    fdomGraphFragment = (FdomGraphFragment) createdFragment;
                    break;

                case 3:
                    velocityGraphFragment = (VelocityGraphFragment) createdFragment;
                    break;
                case 4:
                    vfGraphFragment = (VfGraphFragment) createdFragment;
                    break;
                case 5:
                    vfdomGraphFragment = (VFdomGraphFragment) createdFragment;
                    break;
            }
            return createdFragment;
        }

    }
}
