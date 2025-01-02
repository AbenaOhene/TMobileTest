package com.example.tmobileproject.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmobileproject.data.remote.model.HomeFeedPage
import com.example.tmobileproject.data.repo.HomeFeedRepository
import com.example.tmobileproject.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFeedViewModel @Inject constructor(
    private val repository: HomeFeedRepository
): ViewModel() {
    // Define MutableStateFlow to hold the HomeFeedPage data
    private val _state = MutableStateFlow(HomeFeedState()) // Assuming Resource is a sealed class to manage different states like Loading, Success, Error
    val state: StateFlow<HomeFeedState> = _state.asStateFlow()

    init {
        // Fetch the home feed when ViewModel is initialized
        fetchHomeFeed()
    }

     fun fetchHomeFeed() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(showLoading = true)

            // Call the repository to get the data
            when (val result = repository.getHomeFeed()) {
                is Resource.Success -> {
                    Log.d("HomeFeedViewModel", "Data fetched successfully: ${result.data?.cards}")
                    val homeFeedPage = result.data ?: HomeFeedPage()
                    _state.value = _state.value.copy(
                        showLoading = false,
                        homeFeedPage = homeFeedPage,
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        showLoading = false,
                        homeFeedPage = HomeFeedPage(),
                        showError = false,
                    )
                }

                else -> {}
            }
        }
    }
}

data class HomeFeedState (
    val homeFeedPage: HomeFeedPage = HomeFeedPage(),
    val showLoading: Boolean = false,
    val showError: Boolean = false,
)
