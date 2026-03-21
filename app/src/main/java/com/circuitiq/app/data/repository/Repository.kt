package com.circuitiq.app.data.repository

import com.circuitiq.app.CircuitIQApp
import com.circuitiq.app.data.model.*

object Repository {
    private val db get() = CircuitIQApp.instance.db
    fun getHistory() = db.history().getAll()
    suspend fun saveHistory(e: HistoryEntry) = db.history().insert(e)
    suspend fun clearHistory() = db.history().clearAll()
    suspend fun deleteHistory(id: Long) = db.history().delete(id)
    fun getFavorites() = db.favorites().getAll()
    suspend fun isFavorite(id: String) = db.favorites().isFavorite(id)
    suspend fun addFavorite(f: Favorite) = db.favorites().insert(f)
    suspend fun removeFavorite(id: String) = db.favorites().delete(id)
}
