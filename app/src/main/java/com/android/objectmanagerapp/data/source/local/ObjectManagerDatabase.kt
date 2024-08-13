package com.android.objectmanagerapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.objectmanagerapp.data.source.local.dao.ObjectDao
import com.android.objectmanagerapp.data.source.local.dao.RelationDao
import com.android.objectmanagerapp.data.source.local.entity.ObjectEntity
import com.android.objectmanagerapp.data.source.local.entity.RelationEntity

@Database(entities = [ObjectEntity::class, RelationEntity::class], version = 2)
abstract class ObjectManagerDatabase : RoomDatabase() {
    abstract fun objectDao(): ObjectDao
    abstract fun relationDao(): RelationDao
}