package com.android.objectmanagerapp.data.repository

import com.android.objectmanagerapp.data.mappers.toDataObject
import com.android.objectmanagerapp.data.mappers.toDataObjectList
import com.android.objectmanagerapp.data.mappers.toEntity
import com.android.objectmanagerapp.data.model.DataObject
import com.android.objectmanagerapp.data.model.Relation
import com.android.objectmanagerapp.data.source.local.dao.ObjectDao
import com.android.objectmanagerapp.data.source.local.dao.RelationDao
import com.android.objectmanagerapp.data.source.local.entity.ObjectEntity
import com.android.objectmanagerapp.data.source.local.entity.RelationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObjectRepositoryImpl @Inject constructor(
    private val objectDao: ObjectDao,
    private val relationDao: RelationDao
):ObjectRepository {
    override suspend fun insertObject(dataObject: DataObject) {
        objectDao.insertObject(dataObject.toEntity())
    }

    override suspend fun updateObject(dataObject: DataObject) {
        objectDao.updateObject(dataObject.toEntity())
    }

    override suspend fun deleteObject(dataObject: DataObject) {
        objectDao.deleteObject(dataObject.toEntity())
    }

    override suspend fun getObjects(query: String): Flow<List<DataObject>> {
        return objectDao.getObjects(query).map(List<ObjectEntity>::toDataObjectList)
    }

    override suspend fun getAllObjects(): Flow<List<DataObject>> {
        return objectDao.getAllObjects().map(List<ObjectEntity>::toDataObjectList)
    }

    override suspend fun getObjectById(id: String): DataObject {
        return objectDao.getObjectById(id)?.toDataObject() ?: DataObject()
    }

    override suspend fun insertRelation(relation: Relation) {
        relationDao.insertRelation(relation.toEntity())
    }

    override suspend fun updateRelation(relation: Relation) {
        relationDao.updateRelation(relation.toEntity())
    }

    override suspend fun deleteRelation(relation: Relation) {
        relationDao.deleteRelation(relation.toEntity())
    }

    override fun getRelationsForParent(parentId: String): Flow<List<Relation>> {
        return relationDao.getRelationsForParent(parentId).map(List<RelationEntity>::toDataObjectList)
    }
}