package com.epikron.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.epikron.data.models.ObjectModel

@Database(entities = [ObjectModel::class], version = 1)
internal abstract class ObjectsDatabase : RoomDatabase() {
    internal abstract fun objectsDao(): ObjectsDao
}
