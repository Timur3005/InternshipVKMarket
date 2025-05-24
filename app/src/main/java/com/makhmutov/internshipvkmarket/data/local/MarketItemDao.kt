package com.makhmutov.internshipvkmarket.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.makhmutov.internshipvkmarket.domain.entities.MarketItemEntity

@Dao
interface MarketItemDao {

    @Query("SELECT * FROM market_items")
    suspend fun getAll(): List<MarketItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MarketItemEntity)

    @Delete
    suspend fun delete(item: MarketItemEntity)

    @Query("DELETE FROM market_items")
    suspend fun clearAll()
}