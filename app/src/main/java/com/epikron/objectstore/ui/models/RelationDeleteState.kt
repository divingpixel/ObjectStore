package com.epikron.objectstore.ui.models

data class RelationDeleteState(
    val shouldShowDeleteRelationDialog: Boolean,
    val relatedObjectTypeAndName: String
) {
    companion object {
        val empty = RelationDeleteState(false, "")
    }
}