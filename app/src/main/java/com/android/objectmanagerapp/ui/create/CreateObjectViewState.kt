package com.android.objectmanagerapp.ui.create

import com.android.objectmanagerapp.data.model.DataObject

data class CreateObjectViewState(
    var dataObject: DataObject,
    var saveObject: () -> Unit,
    var updateState: (DataObject) -> Unit,
){
    companion object{
        val empty = CreateObjectViewState(
            dataObject = DataObject(),
            saveObject = {},
            updateState = {}
        )
    }
}