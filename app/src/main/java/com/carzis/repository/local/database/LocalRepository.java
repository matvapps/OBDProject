package com.carzis.repository.local.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.carzis.history.HistoryView;
import com.carzis.main.view.MyCarsView;
import com.carzis.main.view.TroubleCodesView;
import com.carzis.model.Car;
import com.carzis.model.HistoryItem;
import com.carzis.model.Trouble;
import com.huma.room_for_asset.RoomAsset;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.carzis.model.AppError.GET_CAR_FROM_LOCAL_REPO_ERROR;
import static com.carzis.model.AppError.GET_TROUBLE_FROM_LOCAL_REPO_ERROR;

/**
 * Created by Alexandr.
 */
public class LocalRepository {

    private final String TAG = LocalRepository.class.getSimpleName();

    private Context context;
    private AppDatabase appDatabase;
    private TroubleCodesView troubleCodesView;
    private MyCarsView myCarsView;
    private HistoryView historyView;


    public void attachView(TroubleCodesView troubleCodesView) {
        this.troubleCodesView = troubleCodesView;
    }

    public void attachView(MyCarsView myCarsView) {
        this.myCarsView = myCarsView;
    }

    public void attachView(HistoryView historyView) {
        this.historyView = historyView;
    }

    public LocalRepository(Context context) {
        this.context = context;
        appDatabase = RoomAsset
                .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "carzis.db")
                .build();
    }

    public void getTrouble(String code) {
        troubleCodesView.showLoading(true);

        appDatabase.troubleDao().getTrouble(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Trouble>() {
                    @Override
                    public void onSuccess(Trouble trouble) {
                        Log.d(TAG, "onSuccess: " + trouble.getCode());
                        troubleCodesView.onGetTroubleCode(trouble);
                        troubleCodesView.showLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        troubleCodesView.onGetTroubleCode(new Trouble(code, "", "", "нет описания"));
                        troubleCodesView.showLoading(false);
                        troubleCodesView.showError(GET_TROUBLE_FROM_LOCAL_REPO_ERROR);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void getAllTroubles() {
        troubleCodesView.showLoading(true);

        appDatabase.troubleDao().getAllTroubles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(troubles -> {
                    troubleCodesView.showLoading(false);
                    troubleCodesView.onGetTroubleCodes(troubles);
                });
    }


    public void getCar(String name) {
        myCarsView.showLoading(true);

        appDatabase.carDao().getCar(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Car>() {
                    @Override
                    public void onSuccess(Car car) {
                        myCarsView.onGetCar(car);
                        myCarsView.showLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        myCarsView.showLoading(false);
                        myCarsView.showError(GET_CAR_FROM_LOCAL_REPO_ERROR);
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void getAllCars() {
        myCarsView.showLoading(true);

        appDatabase.carDao().getAllCars()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cars -> {
                    myCarsView.showLoading(false);
                    myCarsView.onGetCars(cars);
                });

    }

    @SuppressLint("CheckResult")
    public void addCar(Car car) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                appDatabase.carDao().insertAll(car);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }


    public void addHistoryItem(HistoryItem historyItem) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                appDatabase.historyItemDao().insert(historyItem);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @SuppressLint("CheckResult")
    public void getAllHistoryItemsByCar(String carName) {
        historyView.showLoading(true);

        appDatabase.historyItemDao().getHistoryItemByCar(carName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(historyItems -> {
                    historyView.showLoading(false);
                    historyView.onGetHistoryItems(historyItems, carName);
                });
    }

}
