package com.carzis.main.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.carzis.R;
import com.carzis.main.MainActivity;
import com.carzis.main.adapter.TroubleCodesAdapter;
import com.carzis.main.listener.ActivityToTroublesCallbackListener;
import com.carzis.main.listener.TroublesToActivityCallbackListener;
import com.carzis.main.presenter.TroubleCodePresenter;
import com.carzis.main.view.TroubleCodesView;
import com.carzis.model.AppError;
import com.carzis.model.Trouble;
import com.carzis.repository.local.database.LocalRepository;
import com.carzis.repository.local.prefs.KeyValueStorage;
import com.carzis.util.custom.view.TroubleTypeBtn;

import java.util.List;
import java.util.Objects;

/**
 * Created by Alexandr.
 */
public class TroubleCodesFragment extends Fragment implements ActivityToTroublesCallbackListener,
        View.OnClickListener, TroubleCodesView {

    private static final String TAG = TroubleCodesFragment.class.getSimpleName();


    private LocalRepository localRepository;
    private TroubleCodePresenter troubleCodePresenter;
    private KeyValueStorage keyValueStorage;
    private TroublesToActivityCallbackListener troublesToActivityCallbackListener;

    private TroubleTypeBtn carEngineBtn;
    private TroubleTypeBtn carChasisBtn;
    private TroubleTypeBtn carBodyBtn;
    private TroubleTypeBtn carNetworkBtn;
    private RecyclerView troubleCodesList;
    private TextView troubleFullDescText;
    private ScrollView fullTroubleCodesDescContainer;

    private TextView titleTextView;
    private ImageButton backToTroublesBtn;

    private TroubleCodesAdapter troubleCodesAdapter;

    private final String POWER_TRAIN_CODE = "P";
    private final String CHASIS_CODE = "C";
    private final String BODY_CODE = "B";
    private final String NETWORK_CODE = "U";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trouble_codes, container, false);

        keyValueStorage = new KeyValueStorage(getContext());
        troublesToActivityCallbackListener.getTroubleCodes();

        troubleCodesList = rootView.findViewById(R.id.trouble_codes_list);
        troubleFullDescText = rootView.findViewById(R.id.trouble_code_full_desc);
        carEngineBtn = rootView.findViewById(R.id.car_engine);
        carChasisBtn = rootView.findViewById(R.id.car_chassis);
        carBodyBtn = rootView.findViewById(R.id.car_body);
        carNetworkBtn = rootView.findViewById(R.id.car_electronics);
        fullTroubleCodesDescContainer = rootView.findViewById(R.id.trouble_code_full_desc_container);

        if (isLayoutPortrait(Objects.requireNonNull(getContext()))) {
            titleTextView = rootView.findViewById(R.id.title_text);
            backToTroublesBtn = rootView.findViewById(R.id.back_to_troubles);

            backToTroublesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    troubleFullDescText.setVisibility(View.INVISIBLE);
                    fullTroubleCodesDescContainer.setVisibility(View.INVISIBLE);
                    backToTroublesBtn.setVisibility(View.GONE);
                    titleTextView.setText("КОД ОШИБКИ");
                    troubleCodesList.setVisibility(View.VISIBLE);

                }
            });

        }

        troubleCodesAdapter = new TroubleCodesAdapter();
        troubleCodesList.setAdapter(troubleCodesAdapter);

        localRepository = new LocalRepository(Objects.requireNonNull(getContext()));
        troubleCodePresenter = new TroubleCodePresenter();
        troubleCodePresenter.attachView(this);
        localRepository.attachView(this);

        carEngineBtn.setOnClickListener(this);
        carChasisBtn.setOnClickListener(this);
        carBodyBtn.setOnClickListener(this);
        carNetworkBtn.setOnClickListener(this);

        troubleCodesAdapter.setTroubleType(POWER_TRAIN_CODE);
        troubleCodesAdapter.setOnItemClickListener(
                position -> {
                    updateFullDescription(troubleCodesAdapter.getItem(position));
                    if (isLayoutPortrait(getContext())) {
                        titleTextView.setText("Описание");
                        backToTroublesBtn.setVisibility(View.VISIBLE);
                        fullTroubleCodesDescContainer.setVisibility(View.VISIBLE);
                        troubleFullDescText.setVisibility(View.VISIBLE);
                        troubleCodesList.setVisibility(View.INVISIBLE);

                    }
                });

        carEngineBtn.callOnClick();
        return rootView;
    }

    public void updateFullDescription(Trouble trouble) {
        String fullDescription = trouble.getFull_desc();

        if (fullDescription.isEmpty())
            fullDescription = "no description available";

        troubleFullDescText.setText(fullDescription);
        fullTroubleCodesDescContainer.smoothScrollTo(0, 0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        troublesToActivityCallbackListener = (TroublesToActivityCallbackListener) context;
        ((MainActivity) context).activityToTroublesCallbackListener = this;

    }

    @Override
    public void onPassTroubleCode(String troubleCode) {
        if (!troubleCodesAdapter.contains(troubleCode))
            troubleCodesAdapter.addItem(new Trouble(troubleCode));

        // TODO: check for brand
        troubleCodePresenter.getTroubleCodeDescription(keyValueStorage.getUserToken(), troubleCode, keyValueStorage.getLanguage(), "");
//        localRepository.getTrouble(troubleCode);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.car_engine:
                troubleCodesAdapter.setTroubleType(POWER_TRAIN_CODE);

                carEngineBtn.setSelected(true);
                carChasisBtn.setSelected(false);
                carBodyBtn.setSelected(false);
                carNetworkBtn.setSelected(false);
                break;
            case R.id.car_chassis:
                troubleCodesAdapter.setTroubleType(CHASIS_CODE);

                carEngineBtn.setSelected(false);
                carChasisBtn.setSelected(true);
                carBodyBtn.setSelected(false);
                carNetworkBtn.setSelected(false);
                break;
            case R.id.car_body:
                troubleCodesAdapter.setTroubleType(BODY_CODE);

                carEngineBtn.setSelected(false);
                carChasisBtn.setSelected(false);
                carBodyBtn.setSelected(true);
                carNetworkBtn.setSelected(false);
                break;
            case R.id.car_electronics:
                troubleCodesAdapter.setTroubleType(NETWORK_CODE);

                carEngineBtn.setSelected(false);
                carChasisBtn.setSelected(false);
                carBodyBtn.setSelected(false);
                carNetworkBtn.setSelected(true);
                break;
        }

        if (troubleCodesAdapter.getItemCount() > 0) {
            updateFullDescription(troubleCodesAdapter.getItem(troubleCodesAdapter.selectedItemPos));
//            troubleCodesList.smoothScrollToPosition(troubleCodesAdapter.selectedItemPos);
        } else {
            troubleFullDescText.setText("");
        }

    }


    //    loading troubles interface
    @Override
    public void onGetTroubleCode(Trouble trouble) {
        if (trouble != null) {
            if (!troubleCodesAdapter.contains(trouble.getCode()))
                troubleCodesAdapter.addItem(trouble);
            else
                troubleCodesAdapter.updateItem(trouble);

            // If this is first displayed trouble then automatically show full description
            if (troubleCodesAdapter.getItemCount() == 1)
                updateFullDescription(troubleCodesAdapter.getItem(0));
        }
    }

    private boolean isLayoutPortrait(Context context) {
        return context.getResources()
                .getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    public void onGetTroubleCodes(List<Trouble> troubles) {

    }

    @Override
    public void onRemoteRepoError(String code) {
        localRepository.getTrouble(code);
    }

    @Override
    public void showLoading(boolean load) {

    }

    @Override
    public void showError(AppError appError) {
        switch (appError) {
            case GET_TROUBLE_FROM_LOCAL_REPO_ERROR:

                break;
            case GET_TROUBLE_FROM_REMOTE_REPO_ERROR:

                break;
        }
//        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
    }
//    --------------------------------------------------------

}
