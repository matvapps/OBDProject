package com.carzis.connect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;


import com.carzis.R;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class ConnectActivity extends AppCompatActivity {

    ImageView iconBth;
    PulsatorLayout pulsatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        iconBth = findViewById(R.id.bth_icon);
        pulsatorLayout = findViewById(R.id.pulsator);

        iconBth.setOnClickListener(view -> pulsatorLayout.start());


    }

}
