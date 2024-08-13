package com.android.objectmanagerapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "relations",
    primaryKeys = ["parentObjectId", "childObjectId"],
    foreignKeys = [
        ForeignKey(
            entity = ObjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentObjectId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ObjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["childObjectId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RelationEntity(
    val parentObjectId: String,
    val childObjectId: String,
)