package com.epikron.objectstore.ui.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epikron.data.models.ObjectModel
import com.epikron.data.repository.ObjectsRepository
import com.epikron.data.repository.RelationsRepository
import com.epikron.objectstore.ui.models.MainScreenState
import com.epikron.objectstore.ui.utils.containsAllChars
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MainScreenLogic {
    val uiState: StateFlow<MainScreenState>
    fun onSearchTextChange(text: String)
    fun onSearchFocusChanged(toggle: Boolean)
    fun onDeleteConfirmed()
    fun toggleDeleteDialog(objectId: Int)
}

class PreviewMainScreenLogic(private val desiredUiState: MainScreenState) : MainScreenLogic {
    override val uiState: StateFlow<MainScreenState> get() = MutableStateFlow(desiredUiState).asStateFlow()

    override fun onSearchTextChange(text: String) {/* do nothing */
    }

    override fun onSearchFocusChanged(toggle: Boolean) {/* do nothing */
    }

    override fun onDeleteConfirmed() {/* do nothing */
    }

    override fun toggleDeleteDialog(objectId: Int) {/* do nothing */
    }
}

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val objectsRepository: ObjectsRepository,
    private val relationsRepository: RelationsRepository
) : ViewModel(), MainScreenLogic {
    private var allObjectsList: List<ObjectModel> = mutableListOf()
        set(value) {
            field = value
            displayObjectsList.value = filterObjectsList()
        }
    private val isDeleteDialogOpen = MutableStateFlow(false)
    private val searchText = MutableStateFlow("")
    private val isSearchActive = MutableStateFlow(false)
    private val displayObjectsList = MutableStateFlow(listOf<ObjectModel>())
    private val selectedObject = MutableStateFlow(ObjectModel.empty)

    override val uiState: StateFlow<MainScreenState> =
        combine(
            searchText,
            isSearchActive,
            displayObjectsList,
            isDeleteDialogOpen,
            selectedObject
        ) { searchText, isSearchActive, objectsList, showDialog, selectedObject ->
            MainScreenState(
                searchText = searchText,
                isSearchActive = isSearchActive,
                objectsList = objectsList,
                shouldShowDeleteDialog = showDialog,
                selectedObject = selectedObject,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = MainScreenState.default
        )

    init {
        viewModelScope.launch {
            allObjectsList = objectsRepository.getAllObjects()
        }
    }

    private fun filterObjectsList(): List<ObjectModel> {
        if (searchText.value.isBlank()) return allObjectsList
        val result: MutableList<ObjectModel> = mutableListOf()
        result.addAll(
            allObjectsList.filter { objectModel ->
                objectModel.name.containsAllChars(searchText.value) ||
                    objectModel.type.containsAllChars(searchText.value) ||
                    objectModel.description.containsAllChars(searchText.value)
            }
        )
        return result
    }

    override fun onSearchTextChange(text: String) {
        searchText.value = text
        displayObjectsList.value = filterObjectsList()
    }

    override fun onSearchFocusChanged(toggle: Boolean) {
        isSearchActive.value = toggle
    }

    override fun onDeleteConfirmed() {
        isDeleteDialogOpen.value = false
        viewModelScope.launch {
            relationsRepository.getForObjectsWithId(selectedObject.value.id)?.forEach { relationModel ->
                relationsRepository.deleteRelation(relationModel)
            }
            objectsRepository.deleteObject(selectedObject.value)
            selectedObject.value = ObjectModel.empty
            allObjectsList = objectsRepository.getAllObjects()
        }
    }

    override fun toggleDeleteDialog(objectId: Int) {
        isDeleteDialogOpen.value = objectId >= 0
        viewModelScope.launch {
            selectedObject.value = objectsRepository.getObjectById(objectId) ?: ObjectModel.empty
        }
    }
}