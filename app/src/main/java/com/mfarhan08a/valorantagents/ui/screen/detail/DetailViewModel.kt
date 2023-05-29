package com.mfarhan08a.valorantagents.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mfarhan08a.valorantagents.data.Repository
import com.mfarhan08a.valorantagents.model.Agent
import com.mfarhan08a.valorantagents.model.ListAgent
import com.mfarhan08a.valorantagents.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<ListAgent>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ListAgent>>
        get() = _uiState

    fun getAgentById(agentId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getAgentById(agentId))
        }
    }

    fun addToFavorite(agent: Agent, isFavorited: Boolean) {
        viewModelScope.launch {
            repository.updateListAgent(agent.id, isFavorited)
        }
    }

}
