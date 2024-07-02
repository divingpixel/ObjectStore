package com.epikron.objectstore.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.epikron.countriesandflags.ui.theme.Space
import com.epikron.objectstore.R
import com.epikron.objectstore.ui.logic.EditScreenLogic
import com.epikron.objectstore.ui.logic.EditScreenViewModel
import com.epikron.objectstore.ui.logic.PreviewEditScreenLogic
import com.epikron.objectstore.ui.models.EditScreenState
import com.epikron.objectstore.ui.theme.ObjectStoreTheme
import com.epikron.objectstore.ui.theme.mainTypography

@Composable
fun EditScreen(
    editObjectId: Int,
    onBackPressed: () -> Unit,
    onEditRelation: (Int) -> Unit,
    viewModel: EditScreenLogic = hiltViewModel<EditScreenViewModel>()
) {
    val titleResId = if (editObjectId >= 0) R.string.edit_screen_title else R.string.add_new_screen_title
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(editObjectId) {
        viewModel.setEditObjectModel(editObjectId)
    }

    if (uiState.relationDeleteState.shouldShowDeleteRelationDialog) {
        ConfirmationDialog(
            message = stringResource(
                id = R.string.delete_question,
                uiState.relationDeleteState.relatedObjectTypeAndName,
                stringResource(id = R.string.relation)
            ),
            onActionResult = { shouldDo ->
                if (shouldDo) {
                    viewModel.onConfirmDeleteRelation()
                } else {
                    viewModel.toggleShowDeleteRelationDialog(-1)
                }
            }
        )
    }

    if (uiState.relationsPickerState.shouldShowRelationsDialog) {
        RelationObjectDialog(
            pickerState = uiState.relationsPickerState,
            onSelect = viewModel::onRelationPickerSelect,
            onDismissRequest = { viewModel.onRelationPickerToggle(false) },
            onConfirmAction = viewModel::onRelationPickerConfirm
        )
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            MenuBar(
                title = stringResource(id = titleResId),
                menuAction = { viewModel.onSaveObjectPressed(onBackPressed) },
                backAction = onBackPressed
            )
        },
        floatingActionButton = {
            FloatingButton(
                onClick = {
                    if (editObjectId < 0) {
                        viewModel.onSaveObjectPressed(onBackPressed)
                    } else {
                        viewModel.onRelationPickerToggle(true)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TextInput(
                modifier = Modifier.padding(top = Space.large),
                uiState = uiState.textEditors.typeText,
                label = stringResource(id = R.string.type),
                errorText = stringResource(id = R.string.error),
                onValueChange = viewModel::onTypeTextChange,
                onDone = viewModel::validateFields
            )
            TextInput(
                uiState = uiState.textEditors.nameText,
                label = stringResource(id = R.string.name),
                errorText = stringResource(id = R.string.error),
                onValueChange = viewModel::onNameTextChange,
                onDone = viewModel::validateFields
            )
            TextInput(
                uiState = uiState.textEditors.descriptionText,
                label = stringResource(id = R.string.description),
                errorText = stringResource(id = R.string.error),
                onValueChange = viewModel::onDescriptionTextChange,
                onDone = viewModel::validateFields
            )

            if (uiState.objectRelations.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(Space.medium, Space.small),
                    text = stringResource(id = R.string.relations),
                    style = mainTypography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                LazyColumn(
                    modifier = Modifier.padding(horizontal = Space.medium),
                ) {
                    items(uiState.objectRelations) { objectModel ->
                        ObjectDisplayContainer(
                            objectModel = objectModel,
                            editAction = onEditRelation,
                            deleteAction = viewModel::toggleShowDeleteRelationDialog,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewEditScreen() {
    ObjectStoreTheme {
        EditScreen(
            editObjectId = 0,
            onBackPressed = {},
            onEditRelation = {},
            viewModel = PreviewEditScreenLogic(EditScreenState.preview)
        )
    }
}