package com.mfarhan08a.valorantagents.data

import com.mfarhan08a.valorantagents.model.FakeDataSource
import com.mfarhan08a.valorantagents.model.ListAgent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class Repository {

    private val agentsData = mutableListOf<ListAgent>()

    init {
        if (agentsData.isEmpty()) {
            FakeDataSource.agents.forEach {
                agentsData.add(ListAgent(it, false))
            }
        }
    }

    fun getAllAgents(): Flow<List<ListAgent>> {
        return flowOf(agentsData)
    }

    fun search(query: String): Flow<List<ListAgent>> {
        return flowOf(
            agentsData.filter {
                it.agent.name.contains(query, ignoreCase = true)
            }
        )
    }

    fun getAgentById(agentId: Long): ListAgent {
        return agentsData.first {
            it.agent.id == agentId
        }
    }

    fun updateListAgent(agentId: Long, newFav: Boolean): Flow<Boolean> {
        val index = agentsData.indexOfFirst { it.agent.id == agentId }
        val agent = agentsData[index]
        agentsData[index] = agent.copy(agent = agent.agent, isFavorite = newFav)
        return flowOf(newFav)
    }

    fun getFavoriteAgents(): Flow<List<ListAgent>> {
        return getAllAgents()
            .map { agentlists ->
                agentlists.filter { agentlist ->
                    agentlist.isFavorite
                }
            }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository()
            }.also { instance = it }
    }
}