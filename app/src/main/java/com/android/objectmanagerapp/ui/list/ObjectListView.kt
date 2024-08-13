package com.android.objectmanagerapp.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.objectmanagerapp.ui.components.ObjectItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObjectListView(
    onAddObjectClick: () -> Unit,
    onEditObjectClick: (String) -> Unit,
) {
    val viewModel = hiltViewModel<ObjectListViewModel>()
    val viewState = viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddObjectClick,
                contentColor = Color.Black,
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Object")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text(text = "Objects") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TextField(
                value = viewState.value.searchQuery,
                onValueChange = {
                    viewState.value.updateSearchQuery(it)
                                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text(text = "Search...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(24.dp)
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize().padding(horizontal = 16.dp)
            ) {
                itemsIndexed(
                    items = viewState.value.objects,
                    key = { _, item -> item.hashCode() }
                ) { _, dataObject ->
                    ObjectItem(
                        dataObject = dataObject,
                        onEditClick = { onEditObjectClick(dataObject.id) },
                        onRemove = {viewState.value.deleteObject(dataObject)}
                    )
                }
            }
        }
    }
}
