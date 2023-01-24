package cz.utb.fai.kurzwatcher.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import cz.utb.fai.kurzwatcher.api.KurzApiModel
import cz.utb.fai.kurzwatcher.api.KurzesNetBridge
import cz.utb.fai.kurzwatcher.api.asDatabaseModel
import cz.utb.fai.kurzwatcher.api.mapKurzesToParams
import cz.utb.fai.kurzwatcher.database.WatcherDatabase
import cz.utb.fai.kurzwatcher.database.asKurzEntryModel
import cz.utb.fai.kurzwatcher.domain.KurzEntryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Date
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class KurzesRepo(private val database: WatcherDatabase) {
    val kurzes: LiveData<List<KurzEntryModel>> = Transformations.map(database.kurzDao.getKurzes()) {
            it.asKurzEntryModel()
        }

    suspend fun getLastKurzes() {
        withContext(Dispatchers.IO) {
            val kurzes = database.kurzDao.getLatestKurzes()
        }
    }

    suspend fun refreshKurzes() {
        val currencyCodes = listOf("USD", "EUR", "GBP", "CZK", "JPY", "CHF", "AUD", "CAD") //TODO: select curses from multiple options panel
        withContext(Dispatchers.IO) {
            //val kurzModel: KurzApiModel = KurzesNetBridge.kurzService.getSpecifiedKurzes(mapKurzesToParams(currencyCodes))
            val kurzModel = KurzApiModel( //TEMP DUMMY
                "CZK",
                LocalDate.parse("2020-05-01", DateTimeFormatter.ISO_DATE),
                mapOf(
                    "USD" to 0.042,
                    "EUR" to 0.038,
                    "GBP" to 0.034,
                    "CZK" to 1.0,
                    "JPY" to 4.5,
                    "CHF" to 0.04,
                    "AUD" to 0.06,
                    "CAD" to 0.05
                ),
                true,
                1588300800
            )
            //database.kurzDao.insert(kurzModel.asDatabaseModel())
            Log.d("KurzesRepo", "refreshKurzes() called")
        }
    }
}