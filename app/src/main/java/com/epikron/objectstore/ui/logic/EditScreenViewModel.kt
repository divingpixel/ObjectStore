package com.epikron.objectstore.ui.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epikron.data.models.ObjectModel
import com.epikron.data.models.RelationModel
import com.epikron.data.repository.ObjectsRepository
import com.epikron.data.repository.RelationsRepository
import com.epikron.objectstore.ui.models.EditScreenState
import com.epikron.objectstore.ui.models.ObjectTextEditorsStates
import com.epikron.objectstore.ui.models.RelationDeleteState
import com.epikron.objectstore.ui.models.RelationsPickerState
import com.epikron.objectstore.ui.models.TextInputState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

interface EditScreenLogic {
    val uiState: StateFlow<EditScreenState>
    fun setEditObjectModel(objectId: Int)
    fun onTypeTextChange(text: String)
    fun onNameTextChange(text: String)
    fun onDescriptionTextChange(text: String)
    fun validateFields()
    fun onSaveObjectPressed(successAction: (() -> Unit)?)
    fun onRelationPickerToggle(toggle: Boolean)
    fun onRelationPickerSelect(id: Int)
    fun onRelationPickerConfirm(id: Int)
    fun toggleShowDeleteRelationDialog(relationId: Int)
    fun onConfirmDeleteRelation()
}

class PreviewEditScreenLogic(private val desiredUiState: EditScreenState) : EditScreenLogic {
    override val uiState: StateFlow<EditScreenState> get() = MutableStateFlow(desiredUiState).asStateFlow()

    override fun setEditObjectModel(objectId: Int) {/* do nothing */
    }

    override fun onTypeTextChange(text: String) {/* do nothing */
    }

    override fun onNameTextChange(text: String) {/* do nothing */
    }

    override fun onDescriptionTextChange(text: String) {/* do nothing */
    }

    override fun onSaveObjectPressed(successAction: (() -> Unit)?) {/* do nothing */
    }

    override fun validateFields() {/* do nothing */
    }

    override fun onRelationPickerToggle(toggle: Boolean) {/* do nothing */
    }

    override fun onRelationPickerSelect(id: Int) {/* do nothing */
    }

    override fun onRelationPickerConfirm(id: Int) {/* do nothing */
    }

    override fun toggleShowDeleteRelationDialog(relationId: Int) {/* do nothing */
    }

    override fun onConfirmDeleteRelation() {/* do nothing */
    }
}

@HiltViewModel
class EditScreenViewModel @Inject constructor(
    private val objectsRepository: ObjectsRepository,
    private val relationsRepository: RelationsRepository
) : ViewModel(), EditScreenLogic {
    private var editObjectModel = ObjectModel.empty
    private var canSave = false

    private val typeText = MutableStateFlow(TextInputState.default)
    private val nameText = MutableStateFlow(TextInputState.default)
    private val descriptionText = MutableStateFlow(TextInputState.default)

    private val textEditorsState = combine(typeText, nameText, descriptionText) { type, name, description ->
        validateFields()
        ObjectTextEditorsStates(
            typeText = type,
            nameText = name,
            descriptionText = description
        )
    }

    private val relations = MutableStateFlow(listOf<ObjectModel>())

    private val shouldShowRelationsDialog = MutableStateFlow(false)
    private val availableRelationObjects = MutableStateFlow(listOf<ObjectModel>())
    private val selectedRelationObjectId = MutableStateFlow(-1)
    private val relationDeleteState = MutableStateFlow(RelationDeleteState.empty)
    private var relationToDelete: RelationModel = RelationModel.empty

    private val relationsDialogPickerState = combine(shouldShowRelationsDialog, availableRelationObjects, selectedRelationObjectId)
    { showDialog, availableObjects, selectedId ->
        RelationsPickerState(
            shouldShowRelationsDialog = showDialog,
            availableRelations = availableObjects,
            selectedObjectId = selectedId
        )
    }

    override val uiState: StateFlow<EditScreenState> = combine(textEditorsState, relations, relationsDialogPickerState, relationDeleteState)
    { textEditors, relations, relationsDialogState, deleteRelationState ->
        EditScreenState(
            textEditors = textEditors,
            objectRelations = relations,
            relationsPickerState = relationsDialogState,
            relationDeleteState = deleteRelationState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = EditScreenState.default
    )

    private suspend fun getRelatedObjects(relations: List<RelationModel>?): List<ObjectModel> {
        return viewModelScope.async {
            relations?.let { relationsList ->
                val result: MutableList<ObjectModel> = mutableListOf()
                relationsList.forEach { relation ->
                    objectsRepository.getObjectById(
                        if (relation.objectId != editObjectModel.id) {
                            relation.objectId
                        } else {
                            relation.relatesWidthId
                        }
                    )?.let {
                        result.add(it)
                    }
                }
                result
            } ?: listOf()
        }.await()
    }

    override fun setEditObjectModel(objectId: Int) {
        if (objectId >= 0) {
            viewModelScope.launch {
                editObjectModel = objectsRepository.getObjectById(objectId) ?: ObjectModel.empty
                typeText.value = typeText.value.copy(text = editObjectModel.type)
                nameText.value = nameText.value.copy(text = editObjectModel.name)
                descriptionText.value = descriptionText.value.copy(text = editObjectModel.description)
                relations.value = getRelatedObjects(relationsRepository.getForObjectsWithId(editObjectModel.id))
            }
        }
    }

    override fun onTypeTextChange(text: String) {
        typeText.value = typeText.value.copy(text = text, isError = text.isBlank())
    }

    override fun onNameTextChange(text: String) {
        nameText.value = nameText.value.copy(text = text, isError = text.isBlank())
    }

    override fun onDescriptionTextChange(text: String) {
        descriptionText.value = descriptionText.value.copy(text = text, isError = text.isBlank())
    }

    override fun onSaveObjectPressed(successAction: (() -> Unit)?) {
        validateFields()
        if (canSave) {
            if (editObjectModel == ObjectModel.empty) {
                viewModelScope.launch {
                    objectsRepository.addObject(
                        ObjectModel(
                            name = nameText.value.text,
                            type = typeText.value.text,
                            description = descriptionText.value.text
                        )
                    )
                }
            } else {
                viewModelScope.launch {
                    objectsRepository.updateObject(
                        editObjectModel.copy(
                            name = nameText.value.text,
                            type = typeText.value.text,
                            description = descriptionText.value.text
                        )
                    )
                }
            }
            successAction?.invoke()
        }
    }

    override fun validateFields() {
        val typeIsValid = typeText.value.text.isNotBlank()
        typeText.value = typeText.value.copy(isError = typeIsValid.not())
        val nameIsValid = nameText.value.text.isNotBlank()
        nameText.value = nameText.value.copy(isError = nameIsValid.not())
        val descriptionIsValid = descriptionText.value.text.isNotBlank()
        descriptionText.value = descriptionText.value.copy(isError = descriptionIsValid.not())
        canSave = typeIsValid && nameIsValid && descriptionIsValid
    }

    override fun onRelationPickerToggle(toggle: Boolean) {
        if (toggle) {
            viewModelScope.launch {
                val alreadyRelated = getRelatedObjects(relationsRepository.getForObjectsWithId(editObjectModel.id)).toSet()
                availableRelationObjects.value = objectsRepository.getAllObjects() - alreadyRelated - editObjectModel
                shouldShowRelationsDialog.value = true
                selectedRelationObjectId.value = -1
            }
        } else {
            shouldShowRelationsDialog.value = false
        }
    }

    override fun onRelationPickerSelect(id: Int) {
        selectedRelationObjectId.value = id
    }

    override fun onRelationPickerConfirm(id: Int) {
        if (id >= 0) viewModelScope.launch {
            shouldShowRelationsDialog.value = false
            relationsRepository.addRelation(
                RelationModel(objectId = editObjectModel.id, relatesWidthId = id)
            )
            relations.value = getRelatedObjects(relationsRepository.getForObjectsWithId(editObjectModel.id))
        } else {
            shouldShowRelationsDialog.value = false
        }
    }

    override fun toggleShowDeleteRelationDialog(relationId: Int) {
        if (relationId >= 0) viewModelScope.launch {
            relationsRepository.getForObjectsWithId(relationId)?.let { relationsList ->
                relationsList.firstOrNull { it.objectId == editObjectModel.id || it.relatesWidthId == editObjectModel.id }
            }?.let {
                relationToDelete = it
            }
            val relation = relations.value.first { it.id == relationId }
            relationDeleteState.value = RelationDeleteState(
                shouldShowDeleteRelationDialog = true,
                relatedObjectTypeAndName = "${relation.type}: ${relation.name}"
            )
        } else {
            relationDeleteState.value = RelationDeleteState.empty
        }
    }

    override fun onConfirmDeleteRelation() {
        viewModelScope.launch {
            relationsRepository.deleteRelation(relationToDelete)
            relations.value = getRelatedObjects(relationsRepository.getForObjectsWithId(editObjectModel.id))
            relationDeleteState.value = RelationDeleteState.empty
        }
    }
}