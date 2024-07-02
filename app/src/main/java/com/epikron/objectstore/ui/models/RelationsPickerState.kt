package com.epikron.objectstore.ui.models

import com.epikron.data.models.ObjectModel

data class RelationsPickerState(
    val shouldShowRelationsDialog : Boolean,
    val availableRelations : List<ObjectModel>,
    val selectedObjectId : Int
)
