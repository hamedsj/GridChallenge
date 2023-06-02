package me.hamedsj.gridchallenge.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.hamedsj.gridchallenge.models.repositories.FakeUnsplashRepositoryImpl
import me.hamedsj.gridchallenge.utils.ifNotSuccessful
import me.hamedsj.gridchallenge.utils.ifSuccessful
import me.hamedsj.gridchallenge.views.states.GridListState

class GridChallengeViewModel : ViewModel() {

    private val unsplashRepository = FakeUnsplashRepositoryImpl()

    private val pStateStream = MutableStateFlow(GridListState())
    val stateStream: StateFlow<GridListState> = pStateStream

    fun loadPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            pStateStream.emit(
                stateStream.value.copy(
                    isLoading = true
                )
            )
            val response = unsplashRepository.fetchPhotos()
            response.ifSuccessful { responseList ->
                pStateStream.emit(
                    stateStream.value.copy(
                        list = responseList,
                        isLoading = false
                    )
                )
            }
            response.ifNotSuccessful {
                pStateStream.emit(
                    stateStream.value.copy(
                        list = listOf(),
                        isLoading = false
                    )
                )
            }
        }
    }

}