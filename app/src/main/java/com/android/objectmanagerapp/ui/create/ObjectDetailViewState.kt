package com.android.objectmanagerapp.ui.create

import com.android.objectmanagerapp.data.model.DataObject

data class ObjectDetailViewState(
    val dataObject: DataObject,
    var mode: String,
    var objectId: String?,
    val selectedRelations: List<String>,
    var initialRelations: List<String>,
    var saveObject: () -> Unit,
    var allObjects: List<DataObject>,
    var updateState: (DataObject) -> Unit
) {
    companion object {
        val empty = ObjectDetailViewState(
            dataObject = DataObject(),
            selectedRelations = emptyList(),
            saveObject = {},
            updateState = {},
            mode = "",
            objectId = null,
            allObjects = mutableListOf(),
            initialRelations = mutableListOf()
        )
    }
}
