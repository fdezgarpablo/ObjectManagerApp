package com.android.objectmanagerapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.objectmanagerapp.data.model.DataObject
import com.android.objectmanagerapp.data.repository.ObjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ObjectListViewModel @Inject constructor(
    private val repository: ObjectRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ObjectListViewState.empty)
    val state: StateFlow<ObjectListViewState> = _state


   init{
       _state.value.updateSearchQuery = {query -> updateSearchQuery(query)}
       _state.value.deleteObject = {dataObject -> deleteObject(dataObject)}

      _state
           .flatMapLatest { currentState ->
               repository.getObjects(currentState.searchQuery)
           }
           .onEach { items ->
               _state.update { currentState ->
                   currentState.copy(
                       objects = items.toMutableList()
                   )
               }
           }
           .launchIn(viewModelScope)
   }

    private fun updateSearchQuery(query: String) {
        _state.update { currentState ->
            currentState.copy(
                searchQuery = query,
            )
        }
    }

    private fun deleteObject(dataObject: DataObject) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteObject(dataObject)
            _state.update { currentState ->
                currentState.copy(
                    searchQuery = _state.value.searchQuery,
                )
            }
        }
    }

}