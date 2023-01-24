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
    val kurzes: LiveData<List<KurzEntryModel>> = Transformations.map(database.kurzDao.getLatestKurzes()) {
            it.asKurzEntryModel()
        }

    suspend fun refreshKurzes() {
        val currencyCodes = listOf("USD", "EUR", "GBP", "BTC")
        withContext(Dispatchers.IO) {
            val apiClient = KurzesNetBridge().userApi
            val apiResult = apiClient.getSpecifiedKurzes(mapKurzesToParams(currencyCodes))
            val kurzModel: KurzApiModel = apiResult.body()!!
            database.kurzDao.insert(kurzModel.asDatabaseModel())
        }
    }
}