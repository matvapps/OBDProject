package com.carzis.repository.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.carzis.dao.CarDao;
import com.carzis.dao.HistoryItemDao;
import com.carzis.dao.PidDao;
import com.carzis.dao.TroubleDao;
import com.carzis.model.Car;
import com.carzis.model.HistoryItem;
import com.carzis.model.Trouble;
import com.carzis.obd.PidNew;

/**
 * Created by Alexandr.
 */

@Database(entities = {Trouble.class, Car.class, HistoryItem.class, PidNew.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    public abstract TroubleDao troubleDao();
    public abstract CarDao carDao();
    public abstract HistoryItemDao historyItemDao();
    public abstract PidDao pidItemDao();
}
