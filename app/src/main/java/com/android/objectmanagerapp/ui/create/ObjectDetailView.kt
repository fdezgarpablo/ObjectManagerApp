package com.android.objectmanagerapp.ui.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.objectmanagerapp.data.model.ModeType
import com.android.objectmanagerapp.data.model.ObjectType
import com.android.objectmanagerapp.ui.components.RelationCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObjectDetailView(
    objectId: String? = null,
    mode: String,
    onCancelClick: () -> Unit
) {
    val viewModel = hiltViewModel<ObjectDetailViewModel>()
    val viewState = viewModel.state.collectAsStateWithLifecycle()

    var expanded by remember { mutableStateOf(false) }
    var isDialogOpen by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = objectId, key2 = mode) {
        viewModel.initialize(objectId, mode)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {

                    Text(
                        text = when (viewState.value.mode) {
                            ModeType.CREATE.name -> "Create Object"
                            ModeType.EDIT.name -> "Edit Object"
                            ModeType.READ.name -> "Object"
                            else -> ""
                        },
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = onCancelClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = viewState.value.dataObject.name,
                onValueChange = { viewState.value.updateState(viewState.value.dataObject.copy(name = it)) },
                label = { Text("Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = viewState.value.mode != ModeType.READ.name) { },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                readOnly = viewState.value.mode == ModeType.READ.name
            )

            OutlinedTextField(
                value = viewState.value.dataObject.description,
                onValueChange = {
                    viewState.value.updateState(
                        viewState.value.dataObject.copy(
                            description = it
                        )
                    )
                },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = viewState.value.mode != ModeType.READ.name) { }
                    .height(150.dp),
                shape = RoundedCornerShape(12.dp),
                readOnly = viewState.value.mode == ModeType.READ.name
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = viewState.value.dataObject.type,
                    onValueChange = {
                        viewState.value.updateState(
                            viewState.value.dataObject.copy(
                                type = it
                            )
                        )
                    },

                    label = { Text("Type") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = viewState.value.mode != ModeType.READ.name) { },
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Icon",
                            modifier = Modifier.clickable {
                                if (viewState.value.mode != ModeType.READ.name) expanded = !expanded
                            }
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ObjectType.entries.forEach { selection ->
                        DropdownMenuItem(
                            onClick = {
                                viewState.value.updateState(viewState.value.dataObject.copy(type = selection.value))
                                expanded = false
                            },
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(text = selection.value)
                                }
                            }
                        )
                    }
                }
            }




            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Relations", style = MaterialTheme.typography.titleMedium)
                IconButton(
                    onClick = { isDialogOpen = true },
                    enabled = viewState.value.mode != ModeType.READ.name
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Add Relation")
                }
            }



            if (viewState.value.selectedRelations.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(viewState.value.selectedRelations) { relatedObjectId ->
                        val relatedObject =
                            viewState.value.allObjects.find { it.id == relatedObjectId }
                        relatedObject?.let {
                            RelationCard(dataObject = it)
                        }
                    }
                }

            } else {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "No relations, please click the edit button to add or delete one",
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
            }


            if (viewState.value.mode != ModeType.READ.name) {
                Button(
                    onClick = {
                        viewState.value.saveObject()
                        onCancelClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text(if (viewState.value.mode == ModeType.EDIT.name) "Save Changes" else "Create Object")
                }
            }

        }

        if (isDialogOpen) {
            AlertDialog(
                onDismissRequest = { isDialogOpen = false },
                title = { Text(text = "Select Relations") },
                text = {
                    Column {
                        viewState.value.allObjects.filter { it.id != viewState.value.objectId }
                            .forEach { relatedObject ->
                                val isSelected =
                                    viewState.value.selectedRelations.contains(relatedObject.id)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .clickable {
                                            viewModel.updateSelectedRelations(
                                                relatedObject.id,
                                                !isSelected
                                            )
                                        },
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Checkbox(
                                        checked = isSelected,
                                        onCheckedChange = null
                                    )

                                    Text(text = relatedObject.name)
                                }
                            }
                    }
                },
                confirmButton = {
                    Button(onClick = { isDialogOpen = false }) {
                        Text("Done")
                    }
                }
            )
        }
    }
}



