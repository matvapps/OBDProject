package com.carzis.dialoglist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.carzis.R;

import java.util.ArrayList;

public class DialogListActivity extends Activity {

    public static final String TITLE_EXTRA = "title";
    public static final String ITEMS_EXTRA = "items";

    public static final String STRING_EXTRA = "string";

    public static final int DIALOG_LIST_ACTIVITY_CODE = 134;

    private TextView title;
    private RecyclerView list;

    private DialogListAdapter dialogListAdapter;

    private String titleText;
    private ArrayList<String> listItems;

    public static void startForResult(Activity activity, String title, ArrayList<String> items) {
        Intent intent = new Intent(activity, DialogListActivity.class);
        intent.putExtra(TITLE_EXTRA, title);
        intent.putExtra(ITEMS_EXTRA, items);
        activity.startActivityForResult(intent, DIALOG_LIST_ACTIVITY_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_list);

        setResult(RESULT_CANCELED);

        titleText = getIntent().getStringExtra(TITLE_EXTRA);
        listItems = getIntent().getStringArrayListExtra(ITEMS_EXTRA);

//        Toast.makeText(this, listItems.toString(), Toast.LENGTH_SHORT).show();

        title = findViewById(R.id.title);
        list = findViewById(R.id.list);

        list.setLayoutManager(new LinearLayoutManager(this));

        dialogListAdapter = new DialogListAdapter();
        dialogListAdapter.setItems(listItems);
        dialogListAdapter.setItemClickListener(item -> {
            Intent intent = new Intent();
            intent.putExtra(STRING_EXTRA, item);

            setResult(RESULT_OK, intent);
            finish();
        });

        list.setAdapter(dialogListAdapter);
        title.setText(titleText);



    }
}
