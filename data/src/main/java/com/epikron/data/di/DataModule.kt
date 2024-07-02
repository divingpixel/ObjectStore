package com.epikron.data.di

import android.content.Context
import androidx.room.Room
import com.epikron.data.local.ObjectsDao
import com.epikron.data.local.ObjectsDatabase
import com.epikron.data.local.RelationsDao
import com.epikron.data.local.RelationsDatabase
import com.epikron.data.repository.ObjectsRepository
import com.epikron.data.repository.ObjectsRepositoryImpl
import com.epikron.data.repository.RelationsRepository
import com.epikron.data.repository.RelationsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
public object RepositoryModule {
    @Singleton
    @Provides
    internal fun provideObjectsRepository(objectsDao: ObjectsDao): ObjectsRepository =
        ObjectsRepositoryImpl(objectsDao)

    @Singleton
    @Provides
    internal fun provideRelationsRepository(relationsDao: RelationsDao): RelationsRepository =
        RelationsRepositoryImpl(relationsDao)
}

@InstallIn(SingletonComponent::class)
@Module
internal object DatabaseModule {
    @Provides
    fun provideRelationsDao(database: RelationsDatabase): RelationsDao {
        return database.relationsDao()
    }

    @Provides
    @Singleton
    fun provideRelationsDatabase(@ApplicationContext appContext: Context): RelationsDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            RelationsDatabase::class.java,
            "RelationsDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideObjectsDao(database: ObjectsDatabase): ObjectsDao {
        return database.objectsDao()
    }

    @Provides
    @Singleton
    fun provideObjectsDatabase(@ApplicationContext appContext: Context): ObjectsDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            ObjectsDatabase::class.java,
            "ObjectsDatabase"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
