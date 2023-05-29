package com.mfarhan08a.valorantagents.model

import com.mfarhan08a.valorantagents.R

data class Agent(
    val id: Long,
    val name: String,
    val role: Role,
    val biography: String,
    val icon: String,
    val art: Int,
)

enum class Role(val roleName: String, val roleIcon: Int) {
    DUELIST("Duelist", R.drawable.duelist),
    CONTROLLER("Controller", R.drawable.controller),
    SENTINEL("Sentinel", R.drawable.sentinel),
    INITIATOR("Intitiator", R.drawable.initiator),
}
