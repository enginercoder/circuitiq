package com.circuitiq.app.ui.favorites

import androidx.lifecycle.*
import com.circuitiq.app.data.repository.Repository
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {
    val favorites = Repository.getFavorites()
    fun remove(id: String) = viewModelScope.launch { Repository.removeFavorite(id) }
}
