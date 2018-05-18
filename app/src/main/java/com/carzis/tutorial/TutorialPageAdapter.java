package com.carzis.tutorial;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.carzis.R;

/**
 * Created by Alexandr.
 */
public class TutorialPageAdapter extends FragmentStatePagerAdapter {

    public static int PAGE_COUNT = 2;

    public TutorialPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                TutorialItemFragment fragment = new TutorialItemFragment();
                fragment.setSubTitle("Смотрите все ошибки в вашем автомобиле");
                fragment.setTitle("Список ошибок");
                fragment.setImageID(R.drawable.bg_1);
                return fragment;
            }
            case 1: {
                TutorialItemFragment fragment = new TutorialItemFragment();
                fragment.setSubTitle("Смотрите в реальном времени за датчиками");
                fragment.setTitle("Панель приборов");
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
