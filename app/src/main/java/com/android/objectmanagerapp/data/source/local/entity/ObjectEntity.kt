package com.android.objectmanagerapp.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "objects")
data class ObjectEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val type: String
)