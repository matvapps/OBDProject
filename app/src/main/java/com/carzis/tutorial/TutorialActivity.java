package com.carzis.tutorial;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.carzis.R;
import com.carzis.base.BaseActivity;
import com.carzis.entry.LogRegActivity;
import com.carzis.repository.local.prefs.KeyValueStorage;

import me.relex.circleindicator.CircleIndicator;

public class TutorialActivity extends BaseActivity {

    private final String CURRENT_PAGE = "current_page";

    private Button nextBtn;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        KeyValueStorage keyValueStorage = new KeyValueStorage(this);
        keyValueStorage.resetDashboardDevices();
        if (!keyValueStorage.isFirstTimeLaunch()) {
            LogRegActivity.start(this);
            finish();
        }
        keyValueStorage.setFirstTimeLaunch(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        nextBtn = findViewById(R.id.next_btn);

        TutorialPageAdapter tutorialPageAdapter = new TutorialPageAdapter(getSupportFragmentManager(), this);

        ViewPager viewpager = findViewById(R.id.viewpager);
        CircleIndicator indicator = findViewById(R.id.indicator);
        viewpager.setAdapter(tutorialPageAdapter);
        viewpager.setOffscreenPageLimit(viewpager.getChildCount());

        indicator.setViewPager(viewpager);

        nextBtn.setOnClickListener(view -> {
            LogRegActivity.start(TutorialActivity.this);
            finish();
        });
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                if (position + 1 == TutorialPageAdapter.PAGE_COUNT) {
                    nextBtn.setVisibility(View.VISIBLE);
                } else
                    nextBtn.setVisibility(View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (savedInstanceState != null) {
            viewpager.setCurrentItem(savedInstanceState.getInt(CURRENT_PAGE));
            tutorialPageAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_PAGE, currentPage);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
