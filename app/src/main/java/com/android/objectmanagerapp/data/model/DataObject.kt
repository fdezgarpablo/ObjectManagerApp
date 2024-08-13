package com.android.objectmanagerapp.data.model

import java.util.UUID

data class DataObject(
    var id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var description: String = "",
    var type: String = ObjectType.HUMAN.value
)