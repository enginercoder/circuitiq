package com.circuitiq.app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.circuitiq.app.data.model.*

@Dao interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY timestamp DESC LIMIT 100")
    fun getAll(): LiveData<List<HistoryEntry>>
    @Insert suspend fun insert(e: HistoryEntry)
    @Query("DELETE FROM history") suspend fun clearAll()
    @Query("DELETE FROM history WHERE id=:id") suspend fun delete(id: Long)
}

@Dao interface FavoriteDao {
    @Query("SELECT * FROM favorites ORDER BY addedAt DESC")
    fun getAll(): LiveData<List<Favorite>>
    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE calculatorId=:id)")
    suspend fun isFavorite(id: String): Boolean
    @Insert(onConflict=OnConflictStrategy.REPLACE) suspend fun insert(f: Favorite)
    @Query("DELETE FROM favorites WHERE calculatorId=:id") suspend fun delete(id: String)
}

@Database(entities=[HistoryEntry::class, Favorite::class], version=1, exportSchema=false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun history(): HistoryDao
    abstract fun favorites(): FavoriteDao
}
