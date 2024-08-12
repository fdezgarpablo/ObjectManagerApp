package com.android.objectmanagerapp.data.repository

import com.android.objectmanagerapp.data.mappers.toDataObjectList
import com.android.objectmanagerapp.data.mappers.toEntity
import com.android.objectmanagerapp.data.model.DataObject
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

    override fun getObjects(query: String): Flow<List<DataObject>> {
        return objectDao.getObjects("%$query%").map(List<ObjectEntity>::toDataObjectList)
    }

    override suspend fun insertRelation(relationEntity: RelationEntity) {
        relationDao.insertRelation(relationEntity)
    }

    override suspend fun updateRelation(relationEntity: RelationEntity) {
        relationDao.updateRelation(relationEntity)
    }

    override suspend fun deleteRelation(relationEntity: RelationEntity) {
        relationDao.deleteRelation(relationEntity)
    }

    override fun getRelationsForParent(parentId: Int): Flow<List<RelationEntity>> {
        return relationDao.getRelationsForParent(parentId)
    }
}