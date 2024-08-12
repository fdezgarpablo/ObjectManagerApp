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
    fun insertObject(objectEntity: ObjectEntity)

    @Update
    fun updateObject(objectEntity: ObjectEntity)

    @Delete
    fun deleteObject(objectEntity: ObjectEntity)

    @Query("SELECT * FROM objects WHERE id = :id")
    fun getObjectById(id: Int): ObjectEntity?

    @Query("SELECT * FROM objects WHERE name LIKE :searchQuery OR type LIKE :searchQuery")
    fun getObjects(searchQuery: String): Flow<List<ObjectEntity>>
}