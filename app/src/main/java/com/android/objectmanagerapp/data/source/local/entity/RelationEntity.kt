package com.android.objectmanagerapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "relations",
    primaryKeys = ["parentObjectId", "childObjectId"],
    foreignKeys = [
        ForeignKey(entity = ObjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentObjectId"]),
        ForeignKey(entity = ObjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["childObjectId"])
    ]
)
data class RelationEntity(
    val parentObjectId: Long,
    val childObjectId: Long,
    val relationshipType: String
)