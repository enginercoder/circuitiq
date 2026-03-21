package com.circuitiq.app

import android.app.Application
import androidx.room.Room
import com.circuitiq.app.data.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class CircuitIQApp : Application() {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    val db by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "circuitiq.db")
            .fallbackToDestructiveMigration().build()
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object { lateinit var instance: CircuitIQApp }
}
// v1.0.1
