package cz.utb.fai.kurzwatcher.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface KurzDao {
    @Query("select * from DatabaseKurz")
    fun getKurzes(): LiveData<List<DatabaseKurz>>

    @Query("select * from DatabaseKurz order by createdTime desc limit 1")
    fun getLatestKurz(): LiveData<DatabaseKurz>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(kurzes: DatabaseKurz)
}

@Database(entities = [DatabaseKurz::class], version = 1)
@TypeConverters(DateConverter::class, StringMapConverter::class)
abstract class WatcherDatabase: RoomDatabase() {
    abstract val kurzDao: KurzDao;
}

private lateinit var INSTANCE: WatcherDatabase

fun getDatabase(context: Context): WatcherDatabase {
    synchronized(WatcherDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                WatcherDatabase::class.java,
                "kurzes").build()
        }
    }
    return INSTANCE
}