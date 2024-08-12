package com.android.objectmanagerapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.android.objectmanagerapp.data.source.local.entity.RelationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RelationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRelation(relationEntity: RelationEntity)

    @Update
    fun updateRelation(relationEntity: RelationEntity)

    @Delete
    fun deleteRelation(relationEntity: RelationEntity)

    @Query("SELECT * FROM relations WHERE parentObjectId = :parentId")
    fun getRelationsForParent(parentId: Int): Flow<List<RelationEntity>>
}