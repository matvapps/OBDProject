package com.carzis.main.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.carzis.R;
import com.carzis.additionalscreen.fragment.AddDeviceFragment;
import com.carzis.main.adapter.MyCarsAdapter;
import com.carzis.main.view.MyCarsView;
import com.carzis.model.Car;
import com.carzis.model.LoadingError;
import com.carzis.repository.local.database.LocalRepository;
import com.carzis.repository.local.prefs.KeyValueStorage;
import com.carzis.util.Utility;
import com.carzis.util.custom.view.GridSpacingItemDecoration;

import java.util.List;
import java.util.Objects;

/**
 * Created by Alexandr.
 */
public class MyCarsFragment extends Fragment implements MyCarsView{

    private final String TAG = AddDeviceFragment.class.getSimpleName();

    private RecyclerView carListView;
    private View searchViewBtn;
    private EditText searchStrEditText;


    private LocalRepository localRepository;
    private MyCarsAdapter myCarsAdapter;

    private KeyValueStorage keyValueStorage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_cars, container, false);

        carListView = rootView.findViewById(R.id.car_list);
        searchViewBtn  = rootView.findViewById(R.id.search_btn);
        searchStrEditText = rootView.findViewById(R.id.search_edtxt);

        keyValueStorage = new KeyValueStorage(Objects.requireNonNull(getContext()));
        localRepository = new LocalRepository(Objects.requireNonNull(getContext()));
        localRepository.attachView(this);

        localRepository.getAllCars();

        myCarsAdapter = new MyCarsAdapter(getContext());

        int spanCount = 4;
        if (isLayoutPortrait(getContext()))
            spanCount = 2;

        carListView.setNestedScrollingEnabled(false);
        carListView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        carListView.addItemDecoration(
                new GridSpacingItemDecoration(
                        spanCount, (int) Utility.convertDpToPx(getContext(), 10), false));
        carListView.setAdapter(myCarsAdapter);


        searchViewBtn.setOnClickListener(view -> myCarsAdapter.search(searchStrEditText.getText().toString()));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        localRepository.getAllCars();
    }

    @Override
    public void onGetCar(Car car) {

    }

    private boolean isLayoutPortrait(Context context) {
        return context.getResources()
                .getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    public void onGetCars(List<Car> cars) {
        myCarsAdapter.setItems(cars);
    }

    @Override
    public void showLoading(boolean load) {

    }

    @Override
    public void showError(LoadingError loadingError) {

    }
}
