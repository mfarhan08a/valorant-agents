package com.mfarhan08a.valorantagents.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfarhan08a.valorantagents.data.Repository
import com.mfarhan08a.valorantagents.model.ListAgent
import com.mfarhan08a.valorantagents.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<ListAgent>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<ListAgent>>>
        get() = _uiState

    fun getAllAgents() {
        viewModelScope.launch {
            repository.getAllAgents()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { agents ->
                    _uiState.value = UiState.Success(agents)
                }
        }
    }

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            repository.search(newQuery)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { agents ->
                    _uiState.value = UiState.Success(agents)
                }
        }
    }
}