package com.epikron.data.repository

import com.epikron.data.local.ObjectsDao
import com.epikron.data.models.ObjectModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

public interface ObjectsRepository {
    public suspend fun getAllObjects(): List<ObjectModel>
    public suspend fun getObjectById(objectId: Int): ObjectModel?
    public suspend fun deleteObject(objectModel: ObjectModel)
    public suspend fun updateObject(objectModel: ObjectModel)
    public suspend fun addObject(objectModel : ObjectModel)
}

internal class ObjectsRepositoryImpl @Inject constructor(
    private val objectsDao: ObjectsDao,
) : ObjectsRepository {
    private var objectsDaoJobInstance: Deferred<List<ObjectModel>> = CompletableDeferred()

    init {
        objectsDaoJobInstance.cancel()
    }

    override suspend fun getAllObjects(): List<ObjectModel> {
        coroutineScope {
            if (!objectsDaoJobInstance.isActive) {
                objectsDaoJobInstance = async { objectsDao.getAllObjects() }
            } else {
                objectsDaoJobInstance
            }
        }
        return objectsDaoJobInstance.await()
    }

    override suspend fun getObjectById(objectId: Int): ObjectModel? {
        return objectsDao.getById(objectId)
    }

    override suspend fun deleteObject(objectModel: ObjectModel) {
        objectsDao.deleteObject(objectModel)
    }

    override suspend fun updateObject(objectModel: ObjectModel) {
        objectsDao.updateObject(objectModel)
    }

    override suspend fun addObject(objectModel: ObjectModel) {
        objectsDao.insertObject(objectModel)
    }
}
