package com.android.objectmanagerapp.utils

import com.android.objectmanagerapp.data.model.DataObject

fun List<DataObject>.queryFilter(query: String): List<DataObject> {
    return if (query.isEmpty()) this else this.filter { it.name.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)}
}