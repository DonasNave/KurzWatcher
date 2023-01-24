package cz.utb.fai.kurzwatcher.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import cz.utb.fai.kurzwatcher.api.KurzApiModel
import cz.utb.fai.kurzwatcher.api.KurzesNetBridge
import cz.utb.fai.kurzwatcher.api.mapKurzesToParams
import cz.utb.fai.kurzwatcher.database.getDatabase
import cz.utb.fai.kurzwatcher.domain.ConversionResultModel
import cz.utb.fai.kurzwatcher.domain.ConversionSettings
import cz.utb.fai.kurzwatcher.repository.KurzesRepo
import kotlinx.coroutines.launch
import java.io.IOException

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val text: LiveData<String> = _text
    private val kurzRepository = KurzesRepo(getDatabase(application))

    suspend fun convert(settings : ConversionSettings) : ConversionResultModel{
        val result = KurzesNetBridge().userApi.convertCurrency(settings.from, settings.to, settings.amount).body()!!
        Log.d("convert", result.toString())
        return result
    }
}