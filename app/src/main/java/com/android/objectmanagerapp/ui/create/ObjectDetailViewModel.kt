package com.android.objectmanagerapp.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.objectmanagerapp.data.model.DataObject
import com.android.objectmanagerapp.data.model.ModeType
import com.android.objectmanagerapp.data.model.Relation
import com.android.objectmanagerapp.data.repository.ObjectRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ObjectDetailViewModel @Inject constructor(
    private val repository: ObjectRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ObjectDetailViewState.empty)
    val state: StateFlow<ObjectDetailViewState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllObjects().collect { objects ->
                withContext(Dispatchers.Main) {
                    _state.update {
                        it.copy(allObjects = objects)
                    }
                }
            }
        }
    }

    fun initialize(objectId: String?, mode: String) {

        _state.value.mode = mode
        _state.value.objectId = objectId

        if (mode != ModeType.CREATE.name && objectId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val dataObject = repository.getObjectById(objectId)
                withContext(Dispatchers.Main) {
                    _state.update { state ->
                        state.copy(
                            dataObject = dataObject,
                            selectedRelations = getExistingRelations(objectId),
                        )
                    }
                }
            }
        }

        _state.update { state ->
            state.copy(
                saveObject = { saveObject() },
                updateState = { newState -> updateState(newState) }
            )
        }
    }

    private suspend fun getExistingRelations(objectId: String): List<String> {
        val relations = repository.getRelationsForParent(objectId)
            .first()
            .map { it.childObjectId }

        _state.value.initialRelations = relations

        return relations
    }

    private fun saveObject() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_state.value.mode == ModeType.EDIT.name) {

                repository.updateObject(_state.value.dataObject)
                saveRelations(_state.value.objectId!!)

            } else if (_state.value.mode == ModeType.CREATE.name) {

                val objectId = UUID.randomUUID().toString()
                repository.insertObject(_state.value.dataObject.apply { id = objectId })
                saveRelations(objectId)
            }
        }
    }

    private fun saveRelations(parentId: String) {
        val relationsToDelete =
            _state.value.initialRelations.filter { it !in _state.value.selectedRelations }

        viewModelScope.launch(Dispatchers.IO) {

            _state.value.selectedRelations.forEach { relatedObjectId ->
                repository.insertRelation(
                    Relation(
                        parentObjectId = parentId,
                        childObjectId = relatedObjectId,
                    )
                )
            }

            if (_state.value.mode == ModeType.EDIT.name) {
                relationsToDelete.forEach { relationToDelete ->
                    repository.deleteRelation(
                        Relation(
                            parentObjectId = parentId,
                            childObjectId = relationToDelete,
                        )
                    )
                }
            }
        }
    }


    private fun updateState(dataObject: DataObject) {
        _state.update { newState ->
            newState.copy(dataObject = dataObject)
        }
    }

    fun updateSelectedRelations(relatedObjectId: String, isSelected: Boolean) {
        _state.update { currentState ->
            val newRelations = if (isSelected) {
                currentState.selectedRelations + relatedObjectId
            } else {
                currentState.selectedRelations - relatedObjectId
            }
            currentState.copy(selectedRelations = newRelations)
        }
    }
}

