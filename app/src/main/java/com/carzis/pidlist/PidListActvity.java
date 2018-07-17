package com.carzis.pidlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.carzis.R;

public class PidListActvity extends AppCompatActivity {

    private final String TAG = PidListActvity.class.getSimpleName();
    private static final String CAR_NAME = "car_name";

    private String carName;

    public static void start(Context context, String carName) {
        Intent intent = new Intent(context, PidListActvity.class);
        intent.putExtra(CAR_NAME, carName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        carName = getIntent().getStringExtra(CAR_NAME);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pid_list_actvity);


    }
}
