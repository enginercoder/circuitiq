package com.circuitiq.app.ui.home

import androidx.lifecycle.ViewModel
import com.circuitiq.app.data.repository.Repository

class HomeViewModel : ViewModel() {
    val favorites = Repository.getFavorites()
}
