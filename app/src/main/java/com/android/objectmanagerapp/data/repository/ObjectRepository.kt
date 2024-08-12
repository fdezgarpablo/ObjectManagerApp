package com.android.objectmanagerapp.data.repository

import com.android.objectmanagerapp.data.model.DataObject
import com.android.objectmanagerapp.data.source.local.entity.RelationEntity
import kotlinx.coroutines.flow.Flow

interface ObjectRepository {

    suspend fun insertObject(dataObject: DataObject)

    suspend fun updateObject(dataObject: DataObject)

    suspend fun deleteObject(dataObject: DataObject)

    fun getObjects(query: String): Flow<List<DataObject>>

    suspend fun insertRelation(relationEntity: RelationEntity)

    suspend fun updateRelation(relationEntity: RelationEntity)

    suspend fun deleteRelation(relationEntity: RelationEntity)

    fun getRelationsForParent(parentId: Int): Flow<List<RelationEntity>>
}