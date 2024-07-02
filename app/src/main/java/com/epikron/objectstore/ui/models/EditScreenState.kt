package com.epikron.objectstore.ui.models

import com.epikron.data.models.ObjectModel

data class EditScreenState(
    val textEditors: ObjectTextEditorsStates,
    val objectRelations: List<ObjectModel>,
    val relationsPickerState: RelationsPickerState,
    val relationDeleteState: RelationDeleteState
) {
    companion object {
        val preview = EditScreenState(
            textEditors = ObjectTextEditorsStates(
                typeText = TextInputState.preview,
                nameText = TextInputState.preview.copy(isError = true),
                descriptionText = TextInputState.preview.copy(text = "Hello world!")
            ),
            objectRelations = listOf(ObjectModel.preview, ObjectModel.preview),
            relationsPickerState = RelationsPickerState(
                shouldShowRelationsDialog = false, availableRelations = listOf(), selectedObjectId = -1,
            ),
            relationDeleteState = RelationDeleteState(
                shouldShowDeleteRelationDialog = false,
                relatedObjectTypeAndName = ""
            )
        )
        val default = EditScreenState(
            textEditors = ObjectTextEditorsStates(
                typeText = TextInputState.default,
                nameText = TextInputState.default,
                descriptionText = TextInputState.default
            ),
            objectRelations = listOf(),
            relationsPickerState = RelationsPickerState(
                shouldShowRelationsDialog = false, availableRelations = listOf(), selectedObjectId = -1,
            ),
            relationDeleteState = RelationDeleteState(
                shouldShowDeleteRelationDialog = false,
                relatedObjectTypeAndName = ""
            )
        )
    }
}
