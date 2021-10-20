package com.example.detailsdata

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigator.Navigator
import com.example.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val navigator: Navigator,
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle,
): ViewModel(), Navigator by navigator{


    private val id
        get() = savedStateHandle.get<String>("wallpaper")?.toInt()

    private val _state: MutableStateFlow<DetailsDataState> = MutableStateFlow(DetailsDataState())
    val state : StateFlow<DetailsDataState> = _state

    init {
        loadData()
    }



    private fun loadData(){
        id?.let {
            repository.getImageById(it)
                .onEach {dataState ->

                    _state.value = DetailsDataState(isLoading = dataState.isLoading)
                    dataState.data?.let {data ->
                        _state.value = DetailsDataState(commonPic = data)

                        dataState.stateMessage?.let {

                        }
                    }
                }.launchIn(viewModelScope)
        }
    }
}