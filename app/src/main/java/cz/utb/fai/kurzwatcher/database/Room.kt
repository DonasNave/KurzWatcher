package cz.utb.fai.kurzwatcher.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.LocalDate

@Dao
interface KurzDao {
    @Query("select * from DatabaseKurzEntry")
    fun getKurzes(): LiveData<List<DatabaseKurzEntry>>

    @Query("Select t.id, t.code, t.createdTime, t.rate FROM DatabaseKurzEntry t inner join " +
            "(SELECT id, MAX(createdTime) as max_date FROM DatabaseKurzEntry) a " +
            "ON a.id = t.id and a.max_date = t.createdTime")
    fun getLatestKurzes(): LiveData<DatabaseKurzEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(kurzes: List<DatabaseKurzEntry>)

    @Query("DELETE FROM DatabaseKurzEntry")
    fun deleteAll()

    @Query("DELETE FROM DatabaseKurzEntry WHERE createdTime < :date")
    fun deleteOlderThan(date: LocalDate)
}

@Database(entities = [DatabaseKurzEntry::class], version = 1)
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