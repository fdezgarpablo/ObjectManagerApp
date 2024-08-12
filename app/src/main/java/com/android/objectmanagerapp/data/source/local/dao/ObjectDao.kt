package com.android.objectmanagerapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.android.objectmanagerapp.data.source.local.entity.ObjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ObjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertObject(objectEntity: ObjectEntity)

    @Update
    suspend fun updateObject(objectEntity: ObjectEntity)

    @Delete
    suspend fun deleteObject(objectEntity: ObjectEntity)

    @Query("SELECT * FROM objects WHERE id = :id")
    suspend fun getObjectById(id: Long): ObjectEntity?

    @Query("SELECT * FROM objects WHERE name LIKE :searchQuery OR type LIKE :searchQuery")
    fun searchObjects(searchQuery: String): Flow<List<ObjectEntity>>
}