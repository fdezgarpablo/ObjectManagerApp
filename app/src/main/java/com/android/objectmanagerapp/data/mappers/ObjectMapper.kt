package com.android.objectmanagerapp.data.mappers

import com.android.objectmanagerapp.data.model.DataObject
import com.android.objectmanagerapp.data.source.local.entity.ObjectEntity

fun DataObject.toEntity() = ObjectEntity(
    id = id,
    name = name,
    description = description,
    type = type
)

fun ObjectEntity.toDataObject() = DataObject(
    id = id,
    name = name,
    description = description,
    type = type
)

fun List<ObjectEntity>.toDataObjectList() = map(ObjectEntity::toDataObject)