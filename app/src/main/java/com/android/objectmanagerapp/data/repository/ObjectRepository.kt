package com.android.objectmanagerapp.data.repository

import com.android.objectmanagerapp.data.model.DataObject
import com.android.objectmanagerapp.data.model.Relation
import kotlinx.coroutines.flow.Flow

interface ObjectRepository {

    suspend fun insertObject(dataObject: DataObject)

    suspend fun updateObject(dataObject: DataObject)

    suspend fun deleteObject(dataObject: DataObject)

    suspend fun getAllObjects(): Flow<List<DataObject>>

    suspend fun getObjects(query: String): Flow<List<DataObject>>

    suspend fun getObjectById(id: String): DataObject

    suspend fun insertRelation(relation: Relation)

    suspend fun updateRelation(relation: Relation)

    suspend fun deleteRelation(relation: Relation)

    fun getRelationsForParent(parentId: String): Flow<List<Relation>>
}