package com.carzis.additionalscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.carzis.R;
import com.carzis.additionalscreen.fragment.AddCarFragment;
import com.carzis.additionalscreen.fragment.AddDeviceFragment;
import com.carzis.additionalscreen.fragment.ProfileSettingsFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AdditionalActivity extends AppCompatActivity {

    public static final int SETTINGS_PROFILE_FRAGMENT = 22;
    public static final int ADD_DEVICE_FRAGMENT = 23;
    public static final int ADD_CAR_FRAGMENT = 24;

    private static final String FRAGMENT_EXTRA = "fragment";


    private TextView timeText;
    private View background;
    private View backBtn;


    public static void start(Activity activity, int fragmentType) {
        Intent intent = new Intent(activity, AdditionalActivity.class);
        intent.putExtra(FRAGMENT_EXTRA, fragmentType);
        activity.startActivity(intent);
    }

    private void showFragment(Fragment fragment) {
        String TAG = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, TAG);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }


    private void startTimeThread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(() -> {
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                            String timeString = timeFormat.format(calendar.getTime());
                            timeText.setText(timeString);
                        });
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        thread.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional);

        Intent intent = getIntent();

        timeText = findViewById(R.id.time_text_view);
        background = findViewById(R.id.background);
        backBtn = findViewById(R.id.back_btn);
        startTimeThread();

        backBtn.setOnClickListener(view -> finish());

        final int fragmentType = intent.getIntExtra(FRAGMENT_EXTRA, 0);
        switch (fragmentType) {

            case SETTINGS_PROFILE_FRAGMENT: {
                showFragment(new ProfileSettingsFragment());
                background.setBackgroundResource(R.drawable.bg_main);
                break;
            }
            case ADD_DEVICE_FRAGMENT: {
                showFragment(new AddDeviceFragment());
                background.setBackgroundResource(R.drawable.bg_main);
                break;
            }
            case ADD_CAR_FRAGMENT: {
                showFragment(new AddCarFragment());
                background.setBackgroundResource(R.drawable.bg_main);
                break;
            }

        }

    }
}
