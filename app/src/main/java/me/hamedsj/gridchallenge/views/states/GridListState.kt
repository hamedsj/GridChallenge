package me.hamedsj.gridchallenge.views.states

import me.hamedsj.gridchallenge.models.entities.PhotoResponseModel

data class GridListState(
    val list: List<PhotoResponseModel> = listOf(),
    val isLoading: Boolean = false
)