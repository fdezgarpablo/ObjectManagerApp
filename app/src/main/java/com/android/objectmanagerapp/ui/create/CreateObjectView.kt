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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.objectmanagerapp.data.model.ObjectType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateObjectView(
    onCancelClick: () -> Unit,
) {
    val viewModel = hiltViewModel<CreateObjectViewModel>()
    val viewState = viewModel.state.collectAsStateWithLifecycle()

    val types = listOf("Desk", "Computer", "Keyboard", "Server", "Human Being")
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Object") },
                navigationIcon = {
                    IconButton(onClick = onCancelClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = viewState.value.dataObject.description,
                onValueChange = { viewState.value.updateState(viewState.value.dataObject.copy(description = it))  },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                shape = RoundedCornerShape(12.dp),

            )

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = viewState.value.dataObject.type,
                    onValueChange = {viewState.value.updateState(viewState.value.dataObject.copy(type = it))  },
                    label = { Text("Type") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown Icon",
                            modifier = Modifier.clickable { expanded = !expanded }
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
                                viewState.value.updateState(viewState.value.dataObject.copy(type = selection.value ))
                                expanded = false
                            },
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
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

            Spacer(modifier = Modifier.weight(1f))

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
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Save Object")
            }
        }
        }

}
