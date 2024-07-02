package com.epikron.data.repository

import com.epikron.data.local.RelationsDao
import com.epikron.data.models.RelationModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

public interface RelationsRepository {
    public suspend fun getAllRelations(): List<RelationModel>
    public suspend fun getRelationById(relationId: Int): RelationModel?
    public suspend fun getForObjectsWithId(objectId: Int): List<RelationModel>?
    public suspend fun addRelation(relationModel: RelationModel)
    public suspend fun deleteRelation(relationModel: RelationModel)
}

internal class RelationsRepositoryImpl @Inject constructor(
    private val relationsDao: RelationsDao,
) : RelationsRepository {
    private var relationsDaoJobInstance: Deferred<List<RelationModel>> = CompletableDeferred()

    init {
        relationsDaoJobInstance.cancel()
    }

    override suspend fun getAllRelations(): List<RelationModel> {
        coroutineScope {
            if (!relationsDaoJobInstance.isActive) {
                relationsDaoJobInstance = async { relationsDao.getAllRelations() }
            } else {
                relationsDaoJobInstance
            }
        }
        return relationsDaoJobInstance.await()
    }

    override suspend fun getRelationById(relationId: Int): RelationModel? {
        return relationsDao.getRelationById(relationId)
    }

    override suspend fun getForObjectsWithId(objectId: Int): List<RelationModel>? {
        return relationsDao.getForObjectsWithId(objectId)
    }

    override suspend fun addRelation(relationModel: RelationModel) {
        relationsDao.insertRelation(relationModel)
    }

    override suspend fun deleteRelation(relationModel: RelationModel) {
        relationsDao.deleteRelation(relationModel)
    }
}
