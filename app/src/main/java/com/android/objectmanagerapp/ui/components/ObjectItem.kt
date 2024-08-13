package com.android.objectmanagerapp.ui.components

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import com.android.objectmanagerapp.data.model.DataObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObjectItem(
    dataObject: DataObject,
    onEditClick: () -> Unit,
    onRemove: (DataObject) -> Unit,
    onCardClick: () -> Unit
) {
    val context = LocalContext.current
    val currentItem by rememberUpdatedState(dataObject)
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when(it) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    onRemove(currentItem)
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    return@rememberSwipeToDismissBoxState false
                }
                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        positionalThreshold = { it * .25F }
    )
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { DismissBackground(dismissState)},
        content = {
            ObjectCard(dataObject = dataObject, onEditClick = {onEditClick()}, onCardClick = {onCardClick()})
        })
}