package com.android.objectmanagerapp.ui.list

import com.android.objectmanagerapp.data.model.DataObject

data class ObjectListViewState(
    var objects: MutableList<DataObject>,
    var searchQuery: String
){
    companion object{
        val empty = ObjectListViewState(
            objects = mutableListOf(),
            searchQuery = ""
        )
    }
}