package com.epikron.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.epikron.data.models.RelationModel

@Dao
internal interface RelationsDao {

    @Query("SELECT * FROM relationModel ORDER BY id")
    suspend fun getAllRelations(): List<RelationModel>

    @Query("SELECT * FROM relationModel WHERE id LIKE :relationId LIMIT 1")
    suspend fun getRelationById(relationId : Int): RelationModel?

    @Query("SELECT * FROM relationModel WHERE object_id LIKE :objectId OR object_id_related_to LIKE :objectId")
    suspend fun getForObjectsWithId(objectId : Int): List<RelationModel>?

    @Insert
    suspend fun insertRelation(relationModel: RelationModel)

    @Delete
    suspend fun deleteRelation(relationModel: RelationModel)

    @Update
    suspend fun updateRelation(relationModel: RelationModel)
}