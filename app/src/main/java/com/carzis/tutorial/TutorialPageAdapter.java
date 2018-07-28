package com.carzis.tutorial;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.carzis.R;

/**
 * Created by Alexandr.
 */
public class TutorialPageAdapter extends FragmentPagerAdapter {

    public static int PAGE_COUNT = 2;

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
                Log.d("TAG", "getItem: 1");
                fragment.setImageID(R.drawable.bg_1);
                return fragment;
            }
            case 1: {
                TutorialItemFragment fragment = new TutorialItemFragment();
                fragment.setSubTitle(context.getString(R.string.see_gauges_at_real_time));
                fragment.setTitle(context.getString(R.string.dashboard));
                Log.d("TAG", "getItem: 2");
                fragment.setImageID(R.drawable.bg_2);
                return fragment;
            }
//            case 3: {
//                TutorialItemFragment fragment = new TutorialItemFragment();
//
//                return fragment;
//            }
        }

        return new TutorialItemFragment();
    }


    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}
