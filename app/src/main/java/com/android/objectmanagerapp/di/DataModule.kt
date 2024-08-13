package com.android.objectmanagerapp.di

import android.content.Context
import androidx.room.Room
import com.android.objectmanagerapp.data.repository.ObjectRepository
import com.android.objectmanagerapp.data.repository.ObjectRepositoryImpl
import com.android.objectmanagerapp.data.source.local.ObjectManagerDatabase
import com.android.objectmanagerapp.data.source.local.dao.ObjectDao
import com.android.objectmanagerapp.data.source.local.dao.RelationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideObjectRepository(objectDao: ObjectDao, relationDao: RelationDao): ObjectRepository = ObjectRepositoryImpl(objectDao, relationDao)
}


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ObjectManagerDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ObjectManagerDatabase::class.java,
            "ObjectManagerDatabase.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideObjectDao(database: ObjectManagerDatabase): ObjectDao = database.objectDao()
    @Provides
    fun providerRelationDao(database: ObjectManagerDatabase): RelationDao = database.relationDao()
}