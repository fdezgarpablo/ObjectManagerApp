package com.android.objectmanagerapp.data.mappers

import com.android.objectmanagerapp.data.model.Relation
import com.android.objectmanagerapp.data.source.local.entity.RelationEntity

fun Relation.toEntity() = RelationEntity(
    parentObjectId = parentObjectId,
    childObjectId = childObjectId
)

fun RelationEntity.toDataObject() = Relation(
    parentObjectId = parentObjectId,
    childObjectId = childObjectId
)

fun List<RelationEntity>.toDataObjectList() = map(RelationEntity::toDataObject)