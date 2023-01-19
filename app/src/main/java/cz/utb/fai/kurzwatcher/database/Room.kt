package cz.utb.fai.kurzwatcher.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import cz.utb.fai.kurzwatcher.domain.TargetValueModel

@Dao
interface KurzDao {
    @Query("select * from KurzDatabase")
    fun getKurzes(): LiveData<List<DatabaseKurz>>

    @Query("select * from KurzDatabase where code IN (:kurzCodes)")
    fun getSomeKurzes(kurzCodes: List<String>): LiveData<List<DatabaseKurz>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( videos: List<DatabaseKurz>)
}

@Dao
interface TargetValueDao {
    @Query("select * from TargetValueDatabase")
    fun getTargetValues(): LiveData<List<DatabaseTargetValue>>

    @Query("select * from TargetValueDatabase order by createdTime desc limit 1")
    fun getLastTargetValue(): LiveData<DatabaseTargetValue>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( videos: List<DatabaseTargetValue>)
}

@Database(entities = [DatabaseKurz::class, DatabaseTargetValue::class], version = 1)
abstract class WatcherDatabase: RoomDatabase() {
    abstract val kurzDao: KurzDao;
    abstract val targetValueDao: TargetValueDao;
}

private lateinit var INSTANCE: WatcherDatabase

fun getDatabase(context: Context): WatcherDatabase {
    synchronized(WatcherDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                WatcherDatabase::class.java,
                "videos").build()
        }
    }
    return INSTANCE
}