package com.android.objectmanagerapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.objectmanagerapp.data.repository.ObjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ObjectListViewModel @Inject constructor(
    private val repository: ObjectRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ObjectListViewState.empty)
    val state: StateFlow<ObjectListViewState> = _state


   init{
           repository.getObjects(_state.value.searchQuery).onEach{items ->

               _state.update {newState ->
                   newState.copy(
                       objects = items.toMutableList()
                   )
               }
           }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
   }

}