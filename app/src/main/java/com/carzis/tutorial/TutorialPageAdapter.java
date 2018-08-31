package com.carzis.tutorial;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.carzis.R;

import java.util.Locale;

/**
 * Created by Alexandr.
 */
public class TutorialPageAdapter extends FragmentPagerAdapter {

    public static int PAGE_COUNT = 2;

    private Context context;
    private String currentLang;
    private int tutId1vert = R.drawable.tut_vert_1_en;
    private int tutId2vert = R.drawable.tut_vert_2_en;
    private int tutId3vert = R.drawable.tut_vert_3;

    private int tutId1land = R.drawable.tut_land_1_en;
    private int tutId2land = R.drawable.tut_land_2_en;
    private int tutId3land = R.drawable.tut_land_3;

    public TutorialPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        currentLang = Locale.getDefault().getLanguage();

        if (currentLang.equals("ru")) {
            PAGE_COUNT = 3;

            tutId1vert = R.drawable.tut_vert_1;
            tutId2vert = R.drawable.tut_vert_2;
            tutId3vert = R.drawable.tut_vert_3;

            tutId1land = R.drawable.tut_land_1;
            tutId2land = R.drawable.tut_land_2;
            tutId3land = R.drawable.tut_land_3;
        } else {
            tutId1vert = R.drawable.tut_vert_1_en;
            tutId2vert = R.drawable.tut_vert_2_en;

            tutId1land = R.drawable.tut_land_1_en;
            tutId2land = R.drawable.tut_land_2_en;
        }
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
                    fragment.setImageID(tutId1vert);
                } else {
                    fragment.setImageID(tutId1land);
                }
                return fragment;
            }
            case 1: {
                TutorialItemFragment fragment = new TutorialItemFragment();
                if (currentLang.equals("ru")) {
                    fragment.setSubTitle(context.getString(R.string.check_auto_subtitle));
                    fragment.setTitle(context.getString(R.string.check_auto));
                } else {
                    fragment.setSubTitle(context.getString(R.string.see_gauges_at_real_time));
                    fragment.setTitle(context.getString(R.string.dashboard));
                }
                if (context.getResources().getConfiguration().orientation
                        != Configuration.ORIENTATION_LANDSCAPE) {
                    fragment.setImageID(tutId2vert);
                } else {
                    fragment.setImageID(tutId2land);
                }
                return fragment;
            }
            case 2: {
                TutorialItemFragment fragment = new TutorialItemFragment();
                fragment.setSubTitle(context.getString(R.string.see_gauges_at_real_time));
                fragment.setTitle(context.getString(R.string.dashboard));
                if (context.getResources().getConfiguration().orientation
                        != Configuration.ORIENTATION_LANDSCAPE) {
                    fragment.setImageID(tutId3vert);
                } else {
                    fragment.setImageID(tutId3land);
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
