package com.epikron.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.epikron.data.models.ObjectModel
import com.epikron.data.models.RelationModel

@Database(entities = [RelationModel::class], version = 1)
internal abstract class RelationsDatabase : RoomDatabase() {
    internal abstract fun relationsDao(): RelationsDao
}
