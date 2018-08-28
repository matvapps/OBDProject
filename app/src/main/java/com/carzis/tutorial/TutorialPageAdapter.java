package com.carzis.tutorial;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.carzis.R;

/**
 * Created by Alexandr.
 */
public class TutorialPageAdapter extends FragmentPagerAdapter {

    public static int PAGE_COUNT = 3;

    private Context context;

    public TutorialPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                TutorialItemFragment fragment = new TutorialItemFragment();
                fragment.setSubTitle(context.getString(R.string.see_all_your_errors));
                fragment.setTitle(context.getString(R.string.error_list_title));
                if (context.getResources().getConfiguration().orientation
                        != Configuration.ORIENTATION_LANDSCAPE) {
                    fragment.setImageID(R.drawable.tut_vert_1);
                } else {
                    fragment.setImageID(R.drawable.tut_land_1);
                }
                return fragment;
            }
            case 1: {
                TutorialItemFragment fragment = new TutorialItemFragment();
                fragment.setSubTitle(context.getString(R.string.check_auto_subtitle));
                fragment.setTitle(context.getString(R.string.check_auto));
                if (context.getResources().getConfiguration().orientation
                        != Configuration.ORIENTATION_LANDSCAPE) {
                    fragment.setImageID(R.drawable.tut_vert_2);
                } else {
                    fragment.setImageID(R.drawable.tut_land_2);
                }
                return fragment;
            }
            case 2: {
                TutorialItemFragment fragment = new TutorialItemFragment();
                fragment.setSubTitle(context.getString(R.string.see_gauges_at_real_time));
                fragment.setTitle(context.getString(R.string.dashboard));
                if (context.getResources().getConfiguration().orientation
                        != Configuration.ORIENTATION_LANDSCAPE) {
                    fragment.setImageID(R.drawable.tut_vert_3);
                } else {
//                    fragment.setImageID(R.drawable.tut_land_3);
                }
                return fragment;
            }
        }

        return new TutorialItemFragment();
    }


    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}
