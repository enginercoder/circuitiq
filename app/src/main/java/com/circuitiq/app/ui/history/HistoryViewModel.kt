package com.circuitiq.app.ui.history

import androidx.lifecycle.*
import com.circuitiq.app.data.repository.Repository
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {
    val history = Repository.getHistory()
    fun clearAll() = viewModelScope.launch { Repository.clearHistory() }
    fun delete(id: Long) = viewModelScope.launch { Repository.deleteHistory(id) }
}
