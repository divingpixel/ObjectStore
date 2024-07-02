package com.epikron.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.epikron.data.models.ObjectModel

@Dao
internal interface ObjectsDao {

    @Query("SELECT * FROM objectModel ORDER BY id")
    suspend fun getAllObjects(): List<ObjectModel>

    @Query("SELECT * FROM objectModel WHERE object_name LIKE :objectName")
    suspend fun getByName(objectName : String): ObjectModel?

    @Query("SELECT * FROM objectModel WHERE id LIKE :objectId LIMIT 1")
    suspend fun getById(objectId : Int): ObjectModel?

    @Query("SELECT * FROM objectModel WHERE object_type LIKE :objectType")
    suspend fun getByType(objectType : String): List<ObjectModel>?

    @Query("SELECT * FROM objectModel WHERE object_description LIKE :objectDescription")
    suspend fun getByDescription(objectDescription : String): ObjectModel?

    @Insert
    suspend fun insertObject(objectModel: ObjectModel)

    @Delete
    suspend fun deleteObject(objectModel: ObjectModel)

    @Update
    suspend fun updateObject(objectModel: ObjectModel)
}