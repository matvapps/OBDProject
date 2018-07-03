package com.carzis.tutorial;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.carzis.R;
import com.carzis.main.MainActivity;
import com.carzis.repository.local.prefs.KeyValueStorage;

import me.relex.circleindicator.CircleIndicator;

public class TutorialActivity extends AppCompatActivity {

    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        KeyValueStorage keyValueStorage = new KeyValueStorage(this);
        if (!keyValueStorage.isFirstTimeLaunch()) {
            MainActivity.start(this);
            finish();
        }

        setContentView(R.layout.activity_tutorial);
        nextBtn = findViewById(R.id.next_btn);

        keyValueStorage.setFirstTimeLaunch(false);
        TutorialPageAdapter tutorialPageAdapter = new TutorialPageAdapter(getSupportFragmentManager());

        ViewPager viewpager = findViewById(R.id.viewpager);
        CircleIndicator indicator = findViewById(R.id.indicator);
        viewpager.setAdapter(tutorialPageAdapter);
        indicator.setViewPager(viewpager);


        nextBtn.setOnClickListener(view -> {
            MainActivity.start(TutorialActivity.this);
            finish();
        });


        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position + 1 == TutorialPageAdapter.PAGE_COUNT) {
                    nextBtn.setVisibility(View.VISIBLE);
                } else
                    nextBtn.setVisibility(View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
