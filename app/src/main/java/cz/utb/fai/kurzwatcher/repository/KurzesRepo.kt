package cz.utb.fai.kurzwatcher.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import cz.utb.fai.kurzwatcher.api.KurzesNetBridge
import cz.utb.fai.kurzwatcher.api.asDatabaseModel
import cz.utb.fai.kurzwatcher.api.mapKurzesToParams
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

    suspend fun refreshKurzes() {
        val currencyCodes = listOf("USD", "EUR", "GBP", "CZK", "JPY", "CHF", "AUD", "CAD") //TODO: select curses from multiple options panel
        withContext(Dispatchers.IO) {
            val playlist = KurzesNetBridge.kurz.getSpecifiedKurzes(mapKurzesToParams(currencyCodes))
            database.kurzDao.insertAll(playlist.asDatabaseModel())
        }
    }
}