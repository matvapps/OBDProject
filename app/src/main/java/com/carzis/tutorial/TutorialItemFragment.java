package com.carzis.tutorial;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carzis.R;

/**
 * Created by Alexandr.
 */
public class TutorialItemFragment extends Fragment {

    private String title;
    private String subTitle;
    private int imageID;

    private final String CURRENT_TITLE = "current_title";
    private final String CURRENT_SUBTITLE = "current_subtitle";
    private final String CURRENT_IMAGEID = "current_image_id";

    private TextView titleView;
    private TextView subTitleView;
    private ImageView backgroundImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_tutorial, container, false);

        titleView = rootView.findViewById(R.id.title_text);
        subTitleView = rootView.findViewById(R.id.sub_title_text);
        backgroundImage = rootView.findViewById(R.id.background);

        titleView.setText(title);
        subTitleView.setText(subTitle);


        if (savedInstanceState != null) {
            imageID = savedInstanceState.getInt(CURRENT_IMAGEID);
            title = savedInstanceState.getString(CURRENT_TITLE);
            subTitle = savedInstanceState.getString(CURRENT_SUBTITLE);

            switch (imageID) {
                case R.drawable.tut_land_1:
                    if (getResources().getConfiguration().orientation
                            != Configuration.ORIENTATION_LANDSCAPE)
                        imageID = R.drawable.tut_vert_1;
                    break;
                case R.drawable.tut_land_2:
                    if (getResources().getConfiguration().orientation
                            != Configuration.ORIENTATION_LANDSCAPE)
                        imageID = R.drawable.tut_vert_2;
                    break;
                case R.drawable.tut_land_3:
                    if (getResources().getConfiguration().orientation
                            != Configuration.ORIENTATION_LANDSCAPE)
                        imageID = R.drawable.tut_vert_3;
                    break;
                case R.drawable.tut_vert_1:
                    if (getResources().getConfiguration().orientation
                            == Configuration.ORIENTATION_LANDSCAPE)
                        imageID = R.drawable.tut_land_1;
                    break;
                case R.drawable.tut_vert_2:
                    if (getResources().getConfiguration().orientation
                            == Configuration.ORIENTATION_LANDSCAPE)
                        imageID = R.drawable.tut_land_2;
                    break;
                case R.drawable.tut_vert_3:
                    if (getResources().getConfiguration().orientation
                            == Configuration.ORIENTATION_LANDSCAPE)
                        imageID = R.drawable.tut_land_3;
                    break;
                case R.drawable.tut_vert_1_en: {
                    if (getResources().getConfiguration().orientation
                            == Configuration.ORIENTATION_LANDSCAPE)
                        imageID = R.drawable.tut_land_1_en;
                    break;
                }
                case R.drawable.tut_vert_2_en: {
                    if (getResources().getConfiguration().orientation
                            == Configuration.ORIENTATION_LANDSCAPE)
                        imageID = R.drawable.tut_land_2_en;
                    break;
                }
                case R.drawable.tut_land_1_en:
                    if (getResources().getConfiguration().orientation
                            != Configuration.ORIENTATION_LANDSCAPE)
                        imageID = R.drawable.tut_vert_1_en;
                    break;
                case R.drawable.tut_land_2_en:
                    if (getResources().getConfiguration().orientation
                            != Configuration.ORIENTATION_LANDSCAPE)
                        imageID = R.drawable.tut_vert_2_en;
                    break;
            }

            backgroundImage.setImageResource(imageID);
            titleView.setText(title);
            subTitleView.setText(subTitle);
        } else {
            backgroundImage.setImageResource(imageID);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_IMAGEID, imageID);
        outState.putString(CURRENT_SUBTITLE, subTitle);
        outState.putString(CURRENT_TITLE, title);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}
