package com.circuitiq.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val calculatorName: String,
    val category: String,
    val inputs: String,
    val result: String,
    val formula: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "favorites")
data class Favorite(
    @PrimaryKey val calculatorId: String,
    val calculatorName: String,
    val category: String,
    val icon: String,
    val addedAt: Long = System.currentTimeMillis()
)

data class Calculator(
    val id: String,
    val name: String,
    val category: String,
    val icon: String,
    val description: String,
    val isFavorite: Boolean = false
)

data class CalculatorCategory(
    val id: String,
    val name: String,
    val icon: String,
    val color: Int,
    val calculators: List<Calculator>
)
