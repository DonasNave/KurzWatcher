package cz.utb.fai.kurzwatcher.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import cz.utb.fai.kurzwatcher.api.*
import cz.utb.fai.kurzwatcher.database.DatabaseKurz
import cz.utb.fai.kurzwatcher.database.WatcherDatabase
import cz.utb.fai.kurzwatcher.database.asKurzModel
import cz.utb.fai.kurzwatcher.domain.KurzModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KurzesRepo(private val database: WatcherDatabase) {
    val kurzes: LiveData<List<KurzModel>> = Transformations.map(database.kurzDao.getKurzes()) {
            it.asKurzModel()
        }

    suspend fun getLastKurz() {
        withContext(Dispatchers.IO) {
            val kurzes = database.kurzDao.getLatestKurz()
        }
    }

    suspend fun refreshKurzes() {
        val currencyCodes = listOf("USD", "EUR", "GBP", "CZK", "JPY", "CHF", "AUD", "CAD") //TODO: select curses from multiple options panel
        withContext(Dispatchers.IO) {
            //val kurzModel: KurzApiModel = KurzesNetBridge.kurzService.getSpecifiedKurzes(mapKurzesToParams(currencyCodes))
            //database.kurzDao.insert(kurzModel.asDatabaseModel())
            Log.d("KurzesRepo", "refreshKurzes() called")
        }
    }
}