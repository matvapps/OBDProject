package com.carzis.connect;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.carzis.R;
import com.carzis.specialistmode.SpecialistActivity;

import static com.carzis.connect.ConnectActivity.CONNECTION_TYPE_EXTRA;

public class SelectModeActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = SelectModeActivity.class.getSimpleName();

    private Button fastModeBtn;
    private Button specialistBtn;
    private ImageButton backButton;

    private String connectionType;

    public static void start(Activity activity, String connectionType ) {
        Intent intent = new Intent(activity, SelectModeActivity.class);
        intent.putExtra(CONNECTION_TYPE_EXTRA, connectionType);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);

        connectionType = getIntent().getStringExtra(CONNECTION_TYPE_EXTRA);

        fastModeBtn = findViewById(R.id.btn_fast_mode);
        specialistBtn = findViewById(R.id.btn_specialist_mode);
        backButton = findViewById(R.id.back_btn);

        fastModeBtn.setOnClickListener(this);
        specialistBtn.setOnClickListener(this);
        backButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_fast_mode:
                ConnectActivity.start(this, connectionType);
                finish();
                break;
            case R.id.btn_specialist_mode:
                SpecialistActivity.start(this, connectionType);
                finish();
                break;
            case R.id.back_btn:
                ConnectionTypeActivity.start(this);
                finish();
                break;
        }
    }
}
