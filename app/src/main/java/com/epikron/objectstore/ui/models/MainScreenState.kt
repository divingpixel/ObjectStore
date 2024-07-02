package com.epikron.objectstore.ui.models

import com.epikron.data.models.ObjectModel

data class MainScreenState(
    val searchText: String,
    val isSearchActive: Boolean,
    val objectsList: List<ObjectModel>,
    val shouldShowDeleteDialog : Boolean,
    val selectedObject : ObjectModel
) {
    companion object {
        val preview = MainScreenState (
            searchText = "Test",
            isSearchActive = true,
            objectsList = listOf(ObjectModel.preview, ObjectModel.preview),
            shouldShowDeleteDialog = false,
            selectedObject = ObjectModel.empty
        )
        val default = MainScreenState("", false, listOf(), false, ObjectModel.empty)
    }
}