package cz.utb.fai.kurzwatcher.ui.dashboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import cz.utb.fai.kurzwatcher.database.getDatabase
import cz.utb.fai.kurzwatcher.repository.KurzesRepo
import kotlinx.coroutines.launch
import java.io.IOException

class DashboardViewModel (application: Application) : AndroidViewModel(application) {

    private val kurzRepository = KurzesRepo(getDatabase(application))

    var kurzList = kurzRepository.kurzes;

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                kurzRepository.refreshKurzes()
                loadData()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                if(kurzList.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    private fun loadData() {
        kurzList = kurzRepository.kurzes
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    fun onChoiceChanged(specification : String) {
        kurzRepository.SORT_TYPE = specification
        loadData()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                Log.d("DashboardViewModel", "Factory create view-model")
                return DashboardViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct view-model")
        }
    }

}