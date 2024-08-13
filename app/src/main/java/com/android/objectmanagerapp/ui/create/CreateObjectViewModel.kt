package com.android.objectmanagerapp.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.objectmanagerapp.data.model.DataObject
import com.android.objectmanagerapp.data.repository.ObjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateObjectViewModel @Inject constructor(
    private val repository: ObjectRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreateObjectViewState.empty)
    val state: StateFlow<CreateObjectViewState> = _state


    init {
        _state.value.saveObject = {saveObject()}
        _state.value.updateState = { newState -> updateState(newState)}
    }

    private fun saveObject(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertObject(_state.value.dataObject.apply { id = UUID.randomUUID().toString() })
        }
    }

    private fun updateState(dataObject: DataObject){
        _state.update { newState ->
            newState.copy(
                dataObject = dataObject
            )
        }
    }

}