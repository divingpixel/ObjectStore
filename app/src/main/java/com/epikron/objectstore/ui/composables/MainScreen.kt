package com.epikron.objectstore.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epikron.countriesandflags.ui.theme.Space
import com.epikron.objectstore.R
import com.epikron.objectstore.ui.logic.MainScreenLogic
import com.epikron.objectstore.ui.logic.MainScreenViewModel
import com.epikron.objectstore.ui.logic.PreviewMainScreenLogic
import com.epikron.objectstore.ui.models.MainScreenState
import com.epikron.objectstore.ui.theme.ObjectStoreTheme

@Composable
fun MainScreen(
    onAddButtonClick: () -> Unit,
    onEditObjectClick: (Int) -> Unit,
    viewModel: MainScreenLogic = hiltViewModel<MainScreenViewModel>()
) {
    val screenState = viewModel.uiState.collectAsState().value

    if (screenState.shouldShowDeleteDialog) {
        ConfirmationDialog(
            message = stringResource(
                id = R.string.delete_question, screenState.selectedObject.type, screenState.selectedObject.name
            ),
            onActionResult = { shouldDo ->
                if (shouldDo) {
                    viewModel.onDeleteConfirmed()
                } else {
                    viewModel.toggleDeleteDialog(-1)
                }
            }
        )
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            MenuBar(
                title = stringResource(id = R.string.objects_screen_title),
            )
        },
        floatingActionButton = {
            FloatingButton { onAddButtonClick.invoke() }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SearchField(
                uiState = screenState,
                onValueChange = viewModel::onSearchTextChange,
                onSearchFocusChanged = viewModel::onSearchFocusChanged
            )
            LazyColumn(
                modifier = Modifier.padding(horizontal = Space.medium),
            ) {
                items(screenState.objectsList) { objectModel ->
                    ObjectDisplayContainer(
                        objectModel = objectModel,
                        editAction = onEditObjectClick,
                        deleteAction = viewModel::toggleDeleteDialog,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    ObjectStoreTheme {
        MainScreen({}, {}, viewModel = PreviewMainScreenLogic(MainScreenState.preview))
    }
}