package cz.utb.fai.kurzwatcher.ui.dashboard

import android.app.Application
import androidx.lifecycle.*
import cz.utb.fai.kurzwatcher.database.getDatabase
import cz.utb.fai.kurzwatcher.domain.KurzModel
import cz.utb.fai.kurzwatcher.repository.KurzesRepo
import kotlinx.coroutines.launch
import java.io.IOException

class DashboardViewModel (application: Application) : AndroidViewModel(application) {

    private val kurzRepository = KurzesRepo(getDatabase(application))

    private val kurzList = kurzRepository.kurzes;

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                kurzRepository.refreshKurzes()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                if(kurzList.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DashboardViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}